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
import model.Concepto;
import model.Empresa;

/**
 *
 * @author Ariel
 */
public class ConceptoJpaController implements Serializable {

    public ConceptoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Concepto concepto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa unaEmpresaCC = concepto.getUnaEmpresaCC();
            if (unaEmpresaCC != null) {
                unaEmpresaCC = em.getReference(unaEmpresaCC.getClass(), unaEmpresaCC.getId());
                concepto.setUnaEmpresaCC(unaEmpresaCC);
            }
            em.persist(concepto);
            if (unaEmpresaCC != null) {
                unaEmpresaCC.getConceptos().add(concepto);
                unaEmpresaCC = em.merge(unaEmpresaCC);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Concepto concepto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Concepto persistentConcepto = em.find(Concepto.class, concepto.getId());
            Empresa unaEmpresaCCOld = persistentConcepto.getUnaEmpresaCC();
            Empresa unaEmpresaCCNew = concepto.getUnaEmpresaCC();
            if (unaEmpresaCCNew != null) {
                unaEmpresaCCNew = em.getReference(unaEmpresaCCNew.getClass(), unaEmpresaCCNew.getId());
                concepto.setUnaEmpresaCC(unaEmpresaCCNew);
            }
            concepto = em.merge(concepto);
            if (unaEmpresaCCOld != null && !unaEmpresaCCOld.equals(unaEmpresaCCNew)) {
                unaEmpresaCCOld.getConceptos().remove(concepto);
                unaEmpresaCCOld = em.merge(unaEmpresaCCOld);
            }
            if (unaEmpresaCCNew != null && !unaEmpresaCCNew.equals(unaEmpresaCCOld)) {
                unaEmpresaCCNew.getConceptos().add(concepto);
                unaEmpresaCCNew = em.merge(unaEmpresaCCNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = concepto.getId();
                if (findConcepto(id) == null) {
                    throw new NonexistentEntityException("The concepto with id " + id + " no longer exists.");
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
            Concepto concepto;
            try {
                concepto = em.getReference(Concepto.class, id);
                concepto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concepto with id " + id + " no longer exists.", enfe);
            }
            Empresa unaEmpresaCC = concepto.getUnaEmpresaCC();
            if (unaEmpresaCC != null) {
                unaEmpresaCC.getConceptos().remove(concepto);
                unaEmpresaCC = em.merge(unaEmpresaCC);
            }
            em.remove(concepto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Concepto> findConceptoEntities() {
        return findConceptoEntities(true, -1, -1);
    }

    public List<Concepto> findConceptoEntities(int maxResults, int firstResult) {
        return findConceptoEntities(false, maxResults, firstResult);
    }

    private List<Concepto> findConceptoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concepto.class));
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

    public Concepto findConcepto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concepto.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concepto> rt = cq.from(Concepto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
