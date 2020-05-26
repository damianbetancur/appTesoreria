/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.TipoUsuario;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario tipoUsuario = usuario.getTipoUsuario();
            if (tipoUsuario != null) {
                tipoUsuario = em.getReference(tipoUsuario.getClass(), tipoUsuario.getId());
                usuario.setTipoUsuario(tipoUsuario);
            }
            em.persist(usuario);
            if (tipoUsuario != null) {
                tipoUsuario.getUsuarios().add(usuario);
                tipoUsuario = em.merge(tipoUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            TipoUsuario tipoUsuarioOld = persistentUsuario.getTipoUsuario();
            TipoUsuario tipoUsuarioNew = usuario.getTipoUsuario();
            if (tipoUsuarioNew != null) {
                tipoUsuarioNew = em.getReference(tipoUsuarioNew.getClass(), tipoUsuarioNew.getId());
                usuario.setTipoUsuario(tipoUsuarioNew);
            }
            usuario = em.merge(usuario);
            if (tipoUsuarioOld != null && !tipoUsuarioOld.equals(tipoUsuarioNew)) {
                tipoUsuarioOld.getUsuarios().remove(usuario);
                tipoUsuarioOld = em.merge(tipoUsuarioOld);
            }
            if (tipoUsuarioNew != null && !tipoUsuarioNew.equals(tipoUsuarioOld)) {
                tipoUsuarioNew.getUsuarios().add(usuario);
                tipoUsuarioNew = em.merge(tipoUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            TipoUsuario tipoUsuario = usuario.getTipoUsuario();
            if (tipoUsuario != null) {
                tipoUsuario.getUsuarios().remove(usuario);
                tipoUsuario = em.merge(tipoUsuario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Usuario iniciarSesion(Usuario us){
        EntityManager em = getEntityManager();
        Usuario usuario = null;
        String consulta;
        try {
            consulta ="FROM Usuario u WHERE u.nombre = ?1 and u.clave = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, us.getNombre());
            query.setParameter(2, us.getClave());
            
            List <Usuario> lista = query.getResultList();
            if (!lista.isEmpty()) {
                usuario = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        } finally{
            em.close();
        }
        return usuario;
    }
    
    
    public Usuario verificarUsuario(Usuario us){
        EntityManager em = getEntityManager();
        Usuario usuario = null;
        String consulta;
        try {
            consulta ="FROM Usuario u WHERE u.nombre = ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, us.getNombre());
            
            List <Usuario> lista = query.getResultList();
            if (!lista.isEmpty()) {
                usuario = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        } finally{
            em.close();
        }
        return usuario;
    }
}
