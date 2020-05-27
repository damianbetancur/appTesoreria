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
import model.Empresa;
import model.TipoMovimiento;

/**
 *
 * @author Ariel
 */
public class TipoMovimientoJpaController implements Serializable {

    public TipoMovimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoMovimiento tipoMovimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa unaEmpresaTM = tipoMovimiento.getUnaEmpresaTM();
            if (unaEmpresaTM != null) {
                unaEmpresaTM = em.getReference(unaEmpresaTM.getClass(), unaEmpresaTM.getId());
                tipoMovimiento.setUnaEmpresaTM(unaEmpresaTM);
            }
            em.persist(tipoMovimiento);
            if (unaEmpresaTM != null) {
                unaEmpresaTM.getTiposDeMovimientos().add(tipoMovimiento);
                unaEmpresaTM = em.merge(unaEmpresaTM);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoMovimiento tipoMovimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMovimiento persistentTipoMovimiento = em.find(TipoMovimiento.class, tipoMovimiento.getId());
            Empresa unaEmpresaTMOld = persistentTipoMovimiento.getUnaEmpresaTM();
            Empresa unaEmpresaTMNew = tipoMovimiento.getUnaEmpresaTM();
            if (unaEmpresaTMNew != null) {
                unaEmpresaTMNew = em.getReference(unaEmpresaTMNew.getClass(), unaEmpresaTMNew.getId());
                tipoMovimiento.setUnaEmpresaTM(unaEmpresaTMNew);
            }
            tipoMovimiento = em.merge(tipoMovimiento);
            if (unaEmpresaTMOld != null && !unaEmpresaTMOld.equals(unaEmpresaTMNew)) {
                unaEmpresaTMOld.getTiposDeMovimientos().remove(tipoMovimiento);
                unaEmpresaTMOld = em.merge(unaEmpresaTMOld);
            }
            if (unaEmpresaTMNew != null && !unaEmpresaTMNew.equals(unaEmpresaTMOld)) {
                unaEmpresaTMNew.getTiposDeMovimientos().add(tipoMovimiento);
                unaEmpresaTMNew = em.merge(unaEmpresaTMNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoMovimiento.getId();
                if (findTipoMovimiento(id) == null) {
                    throw new NonexistentEntityException("The tipoMovimiento with id " + id + " no longer exists.");
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
            TipoMovimiento tipoMovimiento;
            try {
                tipoMovimiento = em.getReference(TipoMovimiento.class, id);
                tipoMovimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMovimiento with id " + id + " no longer exists.", enfe);
            }
            Empresa unaEmpresaTM = tipoMovimiento.getUnaEmpresaTM();
            if (unaEmpresaTM != null) {
                unaEmpresaTM.getTiposDeMovimientos().remove(tipoMovimiento);
                unaEmpresaTM = em.merge(unaEmpresaTM);
            }
            em.remove(tipoMovimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoMovimiento> findTipoMovimientoEntities() {
        return findTipoMovimientoEntities(true, -1, -1);
    }

    public List<TipoMovimiento> findTipoMovimientoEntities(int maxResults, int firstResult) {
        return findTipoMovimientoEntities(false, maxResults, firstResult);
    }

    private List<TipoMovimiento> findTipoMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoMovimiento.class));
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

    public TipoMovimiento findTipoMovimiento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMovimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoMovimiento> rt = cq.from(TipoMovimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
