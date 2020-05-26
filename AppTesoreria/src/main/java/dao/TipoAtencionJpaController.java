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
import model.TipoAtencion;

/**
 *
 * @author Ariel
 */
public class TipoAtencionJpaController implements Serializable {

    public TipoAtencionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAtencion tipoAtencion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAtencion tipoAtencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoAtencion = em.merge(tipoAtencion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoAtencion.getId();
                if (findTipoAtencion(id) == null) {
                    throw new NonexistentEntityException("The tipoAtencion with id " + id + " no longer exists.");
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
            TipoAtencion tipoAtencion;
            try {
                tipoAtencion = em.getReference(TipoAtencion.class, id);
                tipoAtencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAtencion with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAtencion> findTipoAtencionEntities() {
        return findTipoAtencionEntities(true, -1, -1);
    }

    public List<TipoAtencion> findTipoAtencionEntities(int maxResults, int firstResult) {
        return findTipoAtencionEntities(false, maxResults, firstResult);
    }

    private List<TipoAtencion> findTipoAtencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAtencion.class));
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

    public TipoAtencion findTipoAtencion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAtencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAtencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAtencion> rt = cq.from(TipoAtencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
