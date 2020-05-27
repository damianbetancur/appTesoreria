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
import model.Empleado;
import model.Empresa;

/**
 *
 * @author Ariel
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa unaEmpresaE = empleado.getUnaEmpresaE();
            if (unaEmpresaE != null) {
                unaEmpresaE = em.getReference(unaEmpresaE.getClass(), unaEmpresaE.getId());
                empleado.setUnaEmpresaE(unaEmpresaE);
            }
            em.persist(empleado);
            if (unaEmpresaE != null) {
                unaEmpresaE.getEmpleados().add(empleado);
                unaEmpresaE = em.merge(unaEmpresaE);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Empresa unaEmpresaEOld = persistentEmpleado.getUnaEmpresaE();
            Empresa unaEmpresaENew = empleado.getUnaEmpresaE();
            if (unaEmpresaENew != null) {
                unaEmpresaENew = em.getReference(unaEmpresaENew.getClass(), unaEmpresaENew.getId());
                empleado.setUnaEmpresaE(unaEmpresaENew);
            }
            empleado = em.merge(empleado);
            if (unaEmpresaEOld != null && !unaEmpresaEOld.equals(unaEmpresaENew)) {
                unaEmpresaEOld.getEmpleados().remove(empleado);
                unaEmpresaEOld = em.merge(unaEmpresaEOld);
            }
            if (unaEmpresaENew != null && !unaEmpresaENew.equals(unaEmpresaEOld)) {
                unaEmpresaENew.getEmpleados().add(empleado);
                unaEmpresaENew = em.merge(unaEmpresaENew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Empresa unaEmpresaE = empleado.getUnaEmpresaE();
            if (unaEmpresaE != null) {
                unaEmpresaE.getEmpleados().remove(empleado);
                unaEmpresaE = em.merge(unaEmpresaE);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
