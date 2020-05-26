/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TipoUsuario;

/**
 *
 * @author Ariel
 */
public class TipoUsuarioJpaController implements Serializable {

    public TipoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUsuario tipoUsuario) {
        if (tipoUsuario.getUsuarios() == null) {
            tipoUsuario.setUsuarios(new HashSet<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Usuario> attachedUsuarios = new HashSet<Usuario>();
            for (Usuario usuariosUsuarioToAttach : tipoUsuario.getUsuarios()) {
                usuariosUsuarioToAttach = em.getReference(usuariosUsuarioToAttach.getClass(), usuariosUsuarioToAttach.getId());
                attachedUsuarios.add(usuariosUsuarioToAttach);
            }
            tipoUsuario.setUsuarios(attachedUsuarios);
            em.persist(tipoUsuario);
            for (Usuario usuariosUsuario : tipoUsuario.getUsuarios()) {
                TipoUsuario oldTipoUsuarioOfUsuariosUsuario = usuariosUsuario.getTipoUsuario();
                usuariosUsuario.setTipoUsuario(tipoUsuario);
                usuariosUsuario = em.merge(usuariosUsuario);
                if (oldTipoUsuarioOfUsuariosUsuario != null) {
                    oldTipoUsuarioOfUsuariosUsuario.getUsuarios().remove(usuariosUsuario);
                    oldTipoUsuarioOfUsuariosUsuario = em.merge(oldTipoUsuarioOfUsuariosUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUsuario tipoUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario persistentTipoUsuario = em.find(TipoUsuario.class, tipoUsuario.getId());
            Set<Usuario> usuariosOld = persistentTipoUsuario.getUsuarios();
            Set<Usuario> usuariosNew = tipoUsuario.getUsuarios();
            Set<Usuario> attachedUsuariosNew = new HashSet<Usuario>();
            for (Usuario usuariosNewUsuarioToAttach : usuariosNew) {
                usuariosNewUsuarioToAttach = em.getReference(usuariosNewUsuarioToAttach.getClass(), usuariosNewUsuarioToAttach.getId());
                attachedUsuariosNew.add(usuariosNewUsuarioToAttach);
            }
            usuariosNew = attachedUsuariosNew;
            tipoUsuario.setUsuarios(usuariosNew);
            tipoUsuario = em.merge(tipoUsuario);
            for (Usuario usuariosOldUsuario : usuariosOld) {
                if (!usuariosNew.contains(usuariosOldUsuario)) {
                    usuariosOldUsuario.setTipoUsuario(null);
                    usuariosOldUsuario = em.merge(usuariosOldUsuario);
                }
            }
            for (Usuario usuariosNewUsuario : usuariosNew) {
                if (!usuariosOld.contains(usuariosNewUsuario)) {
                    TipoUsuario oldTipoUsuarioOfUsuariosNewUsuario = usuariosNewUsuario.getTipoUsuario();
                    usuariosNewUsuario.setTipoUsuario(tipoUsuario);
                    usuariosNewUsuario = em.merge(usuariosNewUsuario);
                    if (oldTipoUsuarioOfUsuariosNewUsuario != null && !oldTipoUsuarioOfUsuariosNewUsuario.equals(tipoUsuario)) {
                        oldTipoUsuarioOfUsuariosNewUsuario.getUsuarios().remove(usuariosNewUsuario);
                        oldTipoUsuarioOfUsuariosNewUsuario = em.merge(oldTipoUsuarioOfUsuariosNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoUsuario.getId();
                if (findTipoUsuario(id) == null) {
                    throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.");
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
            TipoUsuario tipoUsuario;
            try {
                tipoUsuario = em.getReference(TipoUsuario.class, id);
                tipoUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.", enfe);
            }
            Set<Usuario> usuarios = tipoUsuario.getUsuarios();
            for (Usuario usuariosUsuario : usuarios) {
                usuariosUsuario.setTipoUsuario(null);
                usuariosUsuario = em.merge(usuariosUsuario);
            }
            em.remove(tipoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUsuario> findTipoUsuarioEntities() {
        return findTipoUsuarioEntities(true, -1, -1);
    }

    public List<TipoUsuario> findTipoUsuarioEntities(int maxResults, int firstResult) {
        return findTipoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TipoUsuario> findTipoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUsuario.class));
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

    public TipoUsuario findTipoUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUsuario> rt = cq.from(TipoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
