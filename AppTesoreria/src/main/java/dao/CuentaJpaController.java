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
import model.Empresa;
import model.RegistroDeMovimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cuenta;

/**
 *
 * @author Ariel
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) {
        if (cuenta.getRegistros() == null) {
            cuenta.setRegistros(new ArrayList<RegistroDeMovimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa unaEmpresaC = cuenta.getUnaEmpresaC();
            if (unaEmpresaC != null) {
                unaEmpresaC = em.getReference(unaEmpresaC.getClass(), unaEmpresaC.getId());
                cuenta.setUnaEmpresaC(unaEmpresaC);
            }
            List<RegistroDeMovimiento> attachedRegistros = new ArrayList<RegistroDeMovimiento>();
            for (RegistroDeMovimiento registrosRegistroDeMovimientoToAttach : cuenta.getRegistros()) {
                registrosRegistroDeMovimientoToAttach = em.getReference(registrosRegistroDeMovimientoToAttach.getClass(), registrosRegistroDeMovimientoToAttach.getId());
                attachedRegistros.add(registrosRegistroDeMovimientoToAttach);
            }
            cuenta.setRegistros(attachedRegistros);
            em.persist(cuenta);
            if (unaEmpresaC != null) {
                unaEmpresaC.getCuentas().add(cuenta);
                unaEmpresaC = em.merge(unaEmpresaC);
            }
            for (RegistroDeMovimiento registrosRegistroDeMovimiento : cuenta.getRegistros()) {
                Cuenta oldUnaCuentaOfRegistrosRegistroDeMovimiento = registrosRegistroDeMovimiento.getUnaCuenta();
                registrosRegistroDeMovimiento.setUnaCuenta(cuenta);
                registrosRegistroDeMovimiento = em.merge(registrosRegistroDeMovimiento);
                if (oldUnaCuentaOfRegistrosRegistroDeMovimiento != null) {
                    oldUnaCuentaOfRegistrosRegistroDeMovimiento.getRegistros().remove(registrosRegistroDeMovimiento);
                    oldUnaCuentaOfRegistrosRegistroDeMovimiento = em.merge(oldUnaCuentaOfRegistrosRegistroDeMovimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getId());
            Empresa unaEmpresaCOld = persistentCuenta.getUnaEmpresaC();
            Empresa unaEmpresaCNew = cuenta.getUnaEmpresaC();
            List<RegistroDeMovimiento> registrosOld = persistentCuenta.getRegistros();
            List<RegistroDeMovimiento> registrosNew = cuenta.getRegistros();
            if (unaEmpresaCNew != null) {
                unaEmpresaCNew = em.getReference(unaEmpresaCNew.getClass(), unaEmpresaCNew.getId());
                cuenta.setUnaEmpresaC(unaEmpresaCNew);
            }
            List<RegistroDeMovimiento> attachedRegistrosNew = new ArrayList<RegistroDeMovimiento>();
            for (RegistroDeMovimiento registrosNewRegistroDeMovimientoToAttach : registrosNew) {
                registrosNewRegistroDeMovimientoToAttach = em.getReference(registrosNewRegistroDeMovimientoToAttach.getClass(), registrosNewRegistroDeMovimientoToAttach.getId());
                attachedRegistrosNew.add(registrosNewRegistroDeMovimientoToAttach);
            }
            registrosNew = attachedRegistrosNew;
            cuenta.setRegistros(registrosNew);
            cuenta = em.merge(cuenta);
            if (unaEmpresaCOld != null && !unaEmpresaCOld.equals(unaEmpresaCNew)) {
                unaEmpresaCOld.getCuentas().remove(cuenta);
                unaEmpresaCOld = em.merge(unaEmpresaCOld);
            }
            if (unaEmpresaCNew != null && !unaEmpresaCNew.equals(unaEmpresaCOld)) {
                unaEmpresaCNew.getCuentas().add(cuenta);
                unaEmpresaCNew = em.merge(unaEmpresaCNew);
            }
            for (RegistroDeMovimiento registrosOldRegistroDeMovimiento : registrosOld) {
                if (!registrosNew.contains(registrosOldRegistroDeMovimiento)) {
                    registrosOldRegistroDeMovimiento.setUnaCuenta(null);
                    registrosOldRegistroDeMovimiento = em.merge(registrosOldRegistroDeMovimiento);
                }
            }
            for (RegistroDeMovimiento registrosNewRegistroDeMovimiento : registrosNew) {
                if (!registrosOld.contains(registrosNewRegistroDeMovimiento)) {
                    Cuenta oldUnaCuentaOfRegistrosNewRegistroDeMovimiento = registrosNewRegistroDeMovimiento.getUnaCuenta();
                    registrosNewRegistroDeMovimiento.setUnaCuenta(cuenta);
                    registrosNewRegistroDeMovimiento = em.merge(registrosNewRegistroDeMovimiento);
                    if (oldUnaCuentaOfRegistrosNewRegistroDeMovimiento != null && !oldUnaCuentaOfRegistrosNewRegistroDeMovimiento.equals(cuenta)) {
                        oldUnaCuentaOfRegistrosNewRegistroDeMovimiento.getRegistros().remove(registrosNewRegistroDeMovimiento);
                        oldUnaCuentaOfRegistrosNewRegistroDeMovimiento = em.merge(oldUnaCuentaOfRegistrosNewRegistroDeMovimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cuenta.getId();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            Empresa unaEmpresaC = cuenta.getUnaEmpresaC();
            if (unaEmpresaC != null) {
                unaEmpresaC.getCuentas().remove(cuenta);
                unaEmpresaC = em.merge(unaEmpresaC);
            }
            List<RegistroDeMovimiento> registros = cuenta.getRegistros();
            for (RegistroDeMovimiento registrosRegistroDeMovimiento : registros) {
                registrosRegistroDeMovimiento.setUnaCuenta(null);
                registrosRegistroDeMovimiento = em.merge(registrosRegistroDeMovimiento);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
