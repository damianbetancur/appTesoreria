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
import model.RequisitoObligatorio;

/**
 *
 * @author Ariel
 */
public class RequisitoObligatorioJpaController implements Serializable {

    public RequisitoObligatorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RequisitoObligatorio requisitoObligatorio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(requisitoObligatorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RequisitoObligatorio requisitoObligatorio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            requisitoObligatorio = em.merge(requisitoObligatorio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = requisitoObligatorio.getId();
                if (findRequisitoObligatorio(id) == null) {
                    throw new NonexistentEntityException("The requisitoObligatorio with id " + id + " no longer exists.");
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
            RequisitoObligatorio requisitoObligatorio;
            try {
                requisitoObligatorio = em.getReference(RequisitoObligatorio.class, id);
                requisitoObligatorio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requisitoObligatorio with id " + id + " no longer exists.", enfe);
            }
            em.remove(requisitoObligatorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RequisitoObligatorio> findRequisitoObligatorioEntities() {
        return findRequisitoObligatorioEntities(true, -1, -1);
    }

    public List<RequisitoObligatorio> findRequisitoObligatorioEntities(int maxResults, int firstResult) {
        return findRequisitoObligatorioEntities(false, maxResults, firstResult);
    }

    private List<RequisitoObligatorio> findRequisitoObligatorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequisitoObligatorio.class));
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

    public RequisitoObligatorio findRequisitoObligatorio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequisitoObligatorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequisitoObligatorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequisitoObligatorio> rt = cq.from(RequisitoObligatorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
