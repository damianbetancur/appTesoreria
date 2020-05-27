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
import model.Cuenta;
import model.LineaDeMovimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.RegistroDeMovimiento;

/**
 *
 * @author Ariel
 */
public class RegistroDeMovimientoJpaController implements Serializable {

    public RegistroDeMovimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroDeMovimiento registroDeMovimiento) {
        if (registroDeMovimiento.getLineasDeRegistroDeMovimiento() == null) {
            registroDeMovimiento.setLineasDeRegistroDeMovimiento(new ArrayList<LineaDeMovimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta unaCuenta = registroDeMovimiento.getUnaCuenta();
            if (unaCuenta != null) {
                unaCuenta = em.getReference(unaCuenta.getClass(), unaCuenta.getId());
                registroDeMovimiento.setUnaCuenta(unaCuenta);
            }
            List<LineaDeMovimiento> attachedLineasDeRegistroDeMovimiento = new ArrayList<LineaDeMovimiento>();
            for (LineaDeMovimiento lineasDeRegistroDeMovimientoLineaDeMovimientoToAttach : registroDeMovimiento.getLineasDeRegistroDeMovimiento()) {
                lineasDeRegistroDeMovimientoLineaDeMovimientoToAttach = em.getReference(lineasDeRegistroDeMovimientoLineaDeMovimientoToAttach.getClass(), lineasDeRegistroDeMovimientoLineaDeMovimientoToAttach.getId());
                attachedLineasDeRegistroDeMovimiento.add(lineasDeRegistroDeMovimientoLineaDeMovimientoToAttach);
            }
            registroDeMovimiento.setLineasDeRegistroDeMovimiento(attachedLineasDeRegistroDeMovimiento);
            em.persist(registroDeMovimiento);
            if (unaCuenta != null) {
                unaCuenta.getRegistros().add(registroDeMovimiento);
                unaCuenta = em.merge(unaCuenta);
            }
            for (LineaDeMovimiento lineasDeRegistroDeMovimientoLineaDeMovimiento : registroDeMovimiento.getLineasDeRegistroDeMovimiento()) {
                RegistroDeMovimiento oldUnRegistroOfLineasDeRegistroDeMovimientoLineaDeMovimiento = lineasDeRegistroDeMovimientoLineaDeMovimiento.getUnRegistro();
                lineasDeRegistroDeMovimientoLineaDeMovimiento.setUnRegistro(registroDeMovimiento);
                lineasDeRegistroDeMovimientoLineaDeMovimiento = em.merge(lineasDeRegistroDeMovimientoLineaDeMovimiento);
                if (oldUnRegistroOfLineasDeRegistroDeMovimientoLineaDeMovimiento != null) {
                    oldUnRegistroOfLineasDeRegistroDeMovimientoLineaDeMovimiento.getLineasDeRegistroDeMovimiento().remove(lineasDeRegistroDeMovimientoLineaDeMovimiento);
                    oldUnRegistroOfLineasDeRegistroDeMovimientoLineaDeMovimiento = em.merge(oldUnRegistroOfLineasDeRegistroDeMovimientoLineaDeMovimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroDeMovimiento registroDeMovimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroDeMovimiento persistentRegistroDeMovimiento = em.find(RegistroDeMovimiento.class, registroDeMovimiento.getId());
            Cuenta unaCuentaOld = persistentRegistroDeMovimiento.getUnaCuenta();
            Cuenta unaCuentaNew = registroDeMovimiento.getUnaCuenta();
            List<LineaDeMovimiento> lineasDeRegistroDeMovimientoOld = persistentRegistroDeMovimiento.getLineasDeRegistroDeMovimiento();
            List<LineaDeMovimiento> lineasDeRegistroDeMovimientoNew = registroDeMovimiento.getLineasDeRegistroDeMovimiento();
            if (unaCuentaNew != null) {
                unaCuentaNew = em.getReference(unaCuentaNew.getClass(), unaCuentaNew.getId());
                registroDeMovimiento.setUnaCuenta(unaCuentaNew);
            }
            List<LineaDeMovimiento> attachedLineasDeRegistroDeMovimientoNew = new ArrayList<LineaDeMovimiento>();
            for (LineaDeMovimiento lineasDeRegistroDeMovimientoNewLineaDeMovimientoToAttach : lineasDeRegistroDeMovimientoNew) {
                lineasDeRegistroDeMovimientoNewLineaDeMovimientoToAttach = em.getReference(lineasDeRegistroDeMovimientoNewLineaDeMovimientoToAttach.getClass(), lineasDeRegistroDeMovimientoNewLineaDeMovimientoToAttach.getId());
                attachedLineasDeRegistroDeMovimientoNew.add(lineasDeRegistroDeMovimientoNewLineaDeMovimientoToAttach);
            }
            lineasDeRegistroDeMovimientoNew = attachedLineasDeRegistroDeMovimientoNew;
            registroDeMovimiento.setLineasDeRegistroDeMovimiento(lineasDeRegistroDeMovimientoNew);
            registroDeMovimiento = em.merge(registroDeMovimiento);
            if (unaCuentaOld != null && !unaCuentaOld.equals(unaCuentaNew)) {
                unaCuentaOld.getRegistros().remove(registroDeMovimiento);
                unaCuentaOld = em.merge(unaCuentaOld);
            }
            if (unaCuentaNew != null && !unaCuentaNew.equals(unaCuentaOld)) {
                unaCuentaNew.getRegistros().add(registroDeMovimiento);
                unaCuentaNew = em.merge(unaCuentaNew);
            }
            for (LineaDeMovimiento lineasDeRegistroDeMovimientoOldLineaDeMovimiento : lineasDeRegistroDeMovimientoOld) {
                if (!lineasDeRegistroDeMovimientoNew.contains(lineasDeRegistroDeMovimientoOldLineaDeMovimiento)) {
                    lineasDeRegistroDeMovimientoOldLineaDeMovimiento.setUnRegistro(null);
                    lineasDeRegistroDeMovimientoOldLineaDeMovimiento = em.merge(lineasDeRegistroDeMovimientoOldLineaDeMovimiento);
                }
            }
            for (LineaDeMovimiento lineasDeRegistroDeMovimientoNewLineaDeMovimiento : lineasDeRegistroDeMovimientoNew) {
                if (!lineasDeRegistroDeMovimientoOld.contains(lineasDeRegistroDeMovimientoNewLineaDeMovimiento)) {
                    RegistroDeMovimiento oldUnRegistroOfLineasDeRegistroDeMovimientoNewLineaDeMovimiento = lineasDeRegistroDeMovimientoNewLineaDeMovimiento.getUnRegistro();
                    lineasDeRegistroDeMovimientoNewLineaDeMovimiento.setUnRegistro(registroDeMovimiento);
                    lineasDeRegistroDeMovimientoNewLineaDeMovimiento = em.merge(lineasDeRegistroDeMovimientoNewLineaDeMovimiento);
                    if (oldUnRegistroOfLineasDeRegistroDeMovimientoNewLineaDeMovimiento != null && !oldUnRegistroOfLineasDeRegistroDeMovimientoNewLineaDeMovimiento.equals(registroDeMovimiento)) {
                        oldUnRegistroOfLineasDeRegistroDeMovimientoNewLineaDeMovimiento.getLineasDeRegistroDeMovimiento().remove(lineasDeRegistroDeMovimientoNewLineaDeMovimiento);
                        oldUnRegistroOfLineasDeRegistroDeMovimientoNewLineaDeMovimiento = em.merge(oldUnRegistroOfLineasDeRegistroDeMovimientoNewLineaDeMovimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = registroDeMovimiento.getId();
                if (findRegistroDeMovimiento(id) == null) {
                    throw new NonexistentEntityException("The registroDeMovimiento with id " + id + " no longer exists.");
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
            RegistroDeMovimiento registroDeMovimiento;
            try {
                registroDeMovimiento = em.getReference(RegistroDeMovimiento.class, id);
                registroDeMovimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroDeMovimiento with id " + id + " no longer exists.", enfe);
            }
            Cuenta unaCuenta = registroDeMovimiento.getUnaCuenta();
            if (unaCuenta != null) {
                unaCuenta.getRegistros().remove(registroDeMovimiento);
                unaCuenta = em.merge(unaCuenta);
            }
            List<LineaDeMovimiento> lineasDeRegistroDeMovimiento = registroDeMovimiento.getLineasDeRegistroDeMovimiento();
            for (LineaDeMovimiento lineasDeRegistroDeMovimientoLineaDeMovimiento : lineasDeRegistroDeMovimiento) {
                lineasDeRegistroDeMovimientoLineaDeMovimiento.setUnRegistro(null);
                lineasDeRegistroDeMovimientoLineaDeMovimiento = em.merge(lineasDeRegistroDeMovimientoLineaDeMovimiento);
            }
            em.remove(registroDeMovimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroDeMovimiento> findRegistroDeMovimientoEntities() {
        return findRegistroDeMovimientoEntities(true, -1, -1);
    }

    public List<RegistroDeMovimiento> findRegistroDeMovimientoEntities(int maxResults, int firstResult) {
        return findRegistroDeMovimientoEntities(false, maxResults, firstResult);
    }

    private List<RegistroDeMovimiento> findRegistroDeMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroDeMovimiento.class));
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

    public RegistroDeMovimiento findRegistroDeMovimiento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroDeMovimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroDeMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroDeMovimiento> rt = cq.from(RegistroDeMovimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
