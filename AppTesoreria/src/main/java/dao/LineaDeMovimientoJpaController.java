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
import model.LineaDeMovimiento;
import model.RegistroDeMovimiento;

/**
 *
 * @author Ariel
 */
public class LineaDeMovimientoJpaController implements Serializable {

    public LineaDeMovimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LineaDeMovimiento lineaDeMovimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroDeMovimiento unRegistro = lineaDeMovimiento.getUnRegistro();
            if (unRegistro != null) {
                unRegistro = em.getReference(unRegistro.getClass(), unRegistro.getId());
                lineaDeMovimiento.setUnRegistro(unRegistro);
            }
            em.persist(lineaDeMovimiento);
            if (unRegistro != null) {
                unRegistro.getLineasDeRegistroDeMovimiento().add(lineaDeMovimiento);
                unRegistro = em.merge(unRegistro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LineaDeMovimiento lineaDeMovimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LineaDeMovimiento persistentLineaDeMovimiento = em.find(LineaDeMovimiento.class, lineaDeMovimiento.getId());
            RegistroDeMovimiento unRegistroOld = persistentLineaDeMovimiento.getUnRegistro();
            RegistroDeMovimiento unRegistroNew = lineaDeMovimiento.getUnRegistro();
            if (unRegistroNew != null) {
                unRegistroNew = em.getReference(unRegistroNew.getClass(), unRegistroNew.getId());
                lineaDeMovimiento.setUnRegistro(unRegistroNew);
            }
            lineaDeMovimiento = em.merge(lineaDeMovimiento);
            if (unRegistroOld != null && !unRegistroOld.equals(unRegistroNew)) {
                unRegistroOld.getLineasDeRegistroDeMovimiento().remove(lineaDeMovimiento);
                unRegistroOld = em.merge(unRegistroOld);
            }
            if (unRegistroNew != null && !unRegistroNew.equals(unRegistroOld)) {
                unRegistroNew.getLineasDeRegistroDeMovimiento().add(lineaDeMovimiento);
                unRegistroNew = em.merge(unRegistroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = lineaDeMovimiento.getId();
                if (findLineaDeMovimiento(id) == null) {
                    throw new NonexistentEntityException("The lineaDeMovimiento with id " + id + " no longer exists.");
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
            LineaDeMovimiento lineaDeMovimiento;
            try {
                lineaDeMovimiento = em.getReference(LineaDeMovimiento.class, id);
                lineaDeMovimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineaDeMovimiento with id " + id + " no longer exists.", enfe);
            }
            RegistroDeMovimiento unRegistro = lineaDeMovimiento.getUnRegistro();
            if (unRegistro != null) {
                unRegistro.getLineasDeRegistroDeMovimiento().remove(lineaDeMovimiento);
                unRegistro = em.merge(unRegistro);
            }
            em.remove(lineaDeMovimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LineaDeMovimiento> findLineaDeMovimientoEntities() {
        return findLineaDeMovimientoEntities(true, -1, -1);
    }

    public List<LineaDeMovimiento> findLineaDeMovimientoEntities(int maxResults, int firstResult) {
        return findLineaDeMovimientoEntities(false, maxResults, firstResult);
    }

    private List<LineaDeMovimiento> findLineaDeMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LineaDeMovimiento.class));
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

    public LineaDeMovimiento findLineaDeMovimiento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LineaDeMovimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineaDeMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LineaDeMovimiento> rt = cq.from(LineaDeMovimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
