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
import model.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cuenta;
import model.TipoMovimiento;
import model.Concepto;
import model.Empresa;

/**
 *
 * @author Ariel
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) {
        if (empresa.getEmpleados() == null) {
            empresa.setEmpleados(new ArrayList<Empleado>());
        }
        if (empresa.getCuentas() == null) {
            empresa.setCuentas(new ArrayList<Cuenta>());
        }
        if (empresa.getTiposDeMovimientos() == null) {
            empresa.setTiposDeMovimientos(new ArrayList<TipoMovimiento>());
        }
        if (empresa.getConceptos() == null) {
            empresa.setConceptos(new ArrayList<Concepto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : empresa.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            empresa.setEmpleados(attachedEmpleados);
            List<Cuenta> attachedCuentas = new ArrayList<Cuenta>();
            for (Cuenta cuentasCuentaToAttach : empresa.getCuentas()) {
                cuentasCuentaToAttach = em.getReference(cuentasCuentaToAttach.getClass(), cuentasCuentaToAttach.getId());
                attachedCuentas.add(cuentasCuentaToAttach);
            }
            empresa.setCuentas(attachedCuentas);
            List<TipoMovimiento> attachedTiposDeMovimientos = new ArrayList<TipoMovimiento>();
            for (TipoMovimiento tiposDeMovimientosTipoMovimientoToAttach : empresa.getTiposDeMovimientos()) {
                tiposDeMovimientosTipoMovimientoToAttach = em.getReference(tiposDeMovimientosTipoMovimientoToAttach.getClass(), tiposDeMovimientosTipoMovimientoToAttach.getId());
                attachedTiposDeMovimientos.add(tiposDeMovimientosTipoMovimientoToAttach);
            }
            empresa.setTiposDeMovimientos(attachedTiposDeMovimientos);
            List<Concepto> attachedConceptos = new ArrayList<Concepto>();
            for (Concepto conceptosConceptoToAttach : empresa.getConceptos()) {
                conceptosConceptoToAttach = em.getReference(conceptosConceptoToAttach.getClass(), conceptosConceptoToAttach.getId());
                attachedConceptos.add(conceptosConceptoToAttach);
            }
            empresa.setConceptos(attachedConceptos);
            em.persist(empresa);
            for (Empleado empleadosEmpleado : empresa.getEmpleados()) {
                Empresa oldUnaEmpresaEOfEmpleadosEmpleado = empleadosEmpleado.getUnaEmpresaE();
                empleadosEmpleado.setUnaEmpresaE(empresa);
                empleadosEmpleado = em.merge(empleadosEmpleado);
                if (oldUnaEmpresaEOfEmpleadosEmpleado != null) {
                    oldUnaEmpresaEOfEmpleadosEmpleado.getEmpleados().remove(empleadosEmpleado);
                    oldUnaEmpresaEOfEmpleadosEmpleado = em.merge(oldUnaEmpresaEOfEmpleadosEmpleado);
                }
            }
            for (Cuenta cuentasCuenta : empresa.getCuentas()) {
                Empresa oldUnaEmpresaCOfCuentasCuenta = cuentasCuenta.getUnaEmpresaC();
                cuentasCuenta.setUnaEmpresaC(empresa);
                cuentasCuenta = em.merge(cuentasCuenta);
                if (oldUnaEmpresaCOfCuentasCuenta != null) {
                    oldUnaEmpresaCOfCuentasCuenta.getCuentas().remove(cuentasCuenta);
                    oldUnaEmpresaCOfCuentasCuenta = em.merge(oldUnaEmpresaCOfCuentasCuenta);
                }
            }
            for (TipoMovimiento tiposDeMovimientosTipoMovimiento : empresa.getTiposDeMovimientos()) {
                Empresa oldUnaEmpresaTMOfTiposDeMovimientosTipoMovimiento = tiposDeMovimientosTipoMovimiento.getUnaEmpresaTM();
                tiposDeMovimientosTipoMovimiento.setUnaEmpresaTM(empresa);
                tiposDeMovimientosTipoMovimiento = em.merge(tiposDeMovimientosTipoMovimiento);
                if (oldUnaEmpresaTMOfTiposDeMovimientosTipoMovimiento != null) {
                    oldUnaEmpresaTMOfTiposDeMovimientosTipoMovimiento.getTiposDeMovimientos().remove(tiposDeMovimientosTipoMovimiento);
                    oldUnaEmpresaTMOfTiposDeMovimientosTipoMovimiento = em.merge(oldUnaEmpresaTMOfTiposDeMovimientosTipoMovimiento);
                }
            }
            for (Concepto conceptosConcepto : empresa.getConceptos()) {
                Empresa oldUnaEmpresaCCOfConceptosConcepto = conceptosConcepto.getUnaEmpresaCC();
                conceptosConcepto.setUnaEmpresaCC(empresa);
                conceptosConcepto = em.merge(conceptosConcepto);
                if (oldUnaEmpresaCCOfConceptosConcepto != null) {
                    oldUnaEmpresaCCOfConceptosConcepto.getConceptos().remove(conceptosConcepto);
                    oldUnaEmpresaCCOfConceptosConcepto = em.merge(oldUnaEmpresaCCOfConceptosConcepto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            List<Empleado> empleadosOld = persistentEmpresa.getEmpleados();
            List<Empleado> empleadosNew = empresa.getEmpleados();
            List<Cuenta> cuentasOld = persistentEmpresa.getCuentas();
            List<Cuenta> cuentasNew = empresa.getCuentas();
            List<TipoMovimiento> tiposDeMovimientosOld = persistentEmpresa.getTiposDeMovimientos();
            List<TipoMovimiento> tiposDeMovimientosNew = empresa.getTiposDeMovimientos();
            List<Concepto> conceptosOld = persistentEmpresa.getConceptos();
            List<Concepto> conceptosNew = empresa.getConceptos();
            List<Empleado> attachedEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado empleadosNewEmpleadoToAttach : empleadosNew) {
                empleadosNewEmpleadoToAttach = em.getReference(empleadosNewEmpleadoToAttach.getClass(), empleadosNewEmpleadoToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewEmpleadoToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            empresa.setEmpleados(empleadosNew);
            List<Cuenta> attachedCuentasNew = new ArrayList<Cuenta>();
            for (Cuenta cuentasNewCuentaToAttach : cuentasNew) {
                cuentasNewCuentaToAttach = em.getReference(cuentasNewCuentaToAttach.getClass(), cuentasNewCuentaToAttach.getId());
                attachedCuentasNew.add(cuentasNewCuentaToAttach);
            }
            cuentasNew = attachedCuentasNew;
            empresa.setCuentas(cuentasNew);
            List<TipoMovimiento> attachedTiposDeMovimientosNew = new ArrayList<TipoMovimiento>();
            for (TipoMovimiento tiposDeMovimientosNewTipoMovimientoToAttach : tiposDeMovimientosNew) {
                tiposDeMovimientosNewTipoMovimientoToAttach = em.getReference(tiposDeMovimientosNewTipoMovimientoToAttach.getClass(), tiposDeMovimientosNewTipoMovimientoToAttach.getId());
                attachedTiposDeMovimientosNew.add(tiposDeMovimientosNewTipoMovimientoToAttach);
            }
            tiposDeMovimientosNew = attachedTiposDeMovimientosNew;
            empresa.setTiposDeMovimientos(tiposDeMovimientosNew);
            List<Concepto> attachedConceptosNew = new ArrayList<Concepto>();
            for (Concepto conceptosNewConceptoToAttach : conceptosNew) {
                conceptosNewConceptoToAttach = em.getReference(conceptosNewConceptoToAttach.getClass(), conceptosNewConceptoToAttach.getId());
                attachedConceptosNew.add(conceptosNewConceptoToAttach);
            }
            conceptosNew = attachedConceptosNew;
            empresa.setConceptos(conceptosNew);
            empresa = em.merge(empresa);
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    empleadosOldEmpleado.setUnaEmpresaE(null);
                    empleadosOldEmpleado = em.merge(empleadosOldEmpleado);
                }
            }
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    Empresa oldUnaEmpresaEOfEmpleadosNewEmpleado = empleadosNewEmpleado.getUnaEmpresaE();
                    empleadosNewEmpleado.setUnaEmpresaE(empresa);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                    if (oldUnaEmpresaEOfEmpleadosNewEmpleado != null && !oldUnaEmpresaEOfEmpleadosNewEmpleado.equals(empresa)) {
                        oldUnaEmpresaEOfEmpleadosNewEmpleado.getEmpleados().remove(empleadosNewEmpleado);
                        oldUnaEmpresaEOfEmpleadosNewEmpleado = em.merge(oldUnaEmpresaEOfEmpleadosNewEmpleado);
                    }
                }
            }
            for (Cuenta cuentasOldCuenta : cuentasOld) {
                if (!cuentasNew.contains(cuentasOldCuenta)) {
                    cuentasOldCuenta.setUnaEmpresaC(null);
                    cuentasOldCuenta = em.merge(cuentasOldCuenta);
                }
            }
            for (Cuenta cuentasNewCuenta : cuentasNew) {
                if (!cuentasOld.contains(cuentasNewCuenta)) {
                    Empresa oldUnaEmpresaCOfCuentasNewCuenta = cuentasNewCuenta.getUnaEmpresaC();
                    cuentasNewCuenta.setUnaEmpresaC(empresa);
                    cuentasNewCuenta = em.merge(cuentasNewCuenta);
                    if (oldUnaEmpresaCOfCuentasNewCuenta != null && !oldUnaEmpresaCOfCuentasNewCuenta.equals(empresa)) {
                        oldUnaEmpresaCOfCuentasNewCuenta.getCuentas().remove(cuentasNewCuenta);
                        oldUnaEmpresaCOfCuentasNewCuenta = em.merge(oldUnaEmpresaCOfCuentasNewCuenta);
                    }
                }
            }
            for (TipoMovimiento tiposDeMovimientosOldTipoMovimiento : tiposDeMovimientosOld) {
                if (!tiposDeMovimientosNew.contains(tiposDeMovimientosOldTipoMovimiento)) {
                    tiposDeMovimientosOldTipoMovimiento.setUnaEmpresaTM(null);
                    tiposDeMovimientosOldTipoMovimiento = em.merge(tiposDeMovimientosOldTipoMovimiento);
                }
            }
            for (TipoMovimiento tiposDeMovimientosNewTipoMovimiento : tiposDeMovimientosNew) {
                if (!tiposDeMovimientosOld.contains(tiposDeMovimientosNewTipoMovimiento)) {
                    Empresa oldUnaEmpresaTMOfTiposDeMovimientosNewTipoMovimiento = tiposDeMovimientosNewTipoMovimiento.getUnaEmpresaTM();
                    tiposDeMovimientosNewTipoMovimiento.setUnaEmpresaTM(empresa);
                    tiposDeMovimientosNewTipoMovimiento = em.merge(tiposDeMovimientosNewTipoMovimiento);
                    if (oldUnaEmpresaTMOfTiposDeMovimientosNewTipoMovimiento != null && !oldUnaEmpresaTMOfTiposDeMovimientosNewTipoMovimiento.equals(empresa)) {
                        oldUnaEmpresaTMOfTiposDeMovimientosNewTipoMovimiento.getTiposDeMovimientos().remove(tiposDeMovimientosNewTipoMovimiento);
                        oldUnaEmpresaTMOfTiposDeMovimientosNewTipoMovimiento = em.merge(oldUnaEmpresaTMOfTiposDeMovimientosNewTipoMovimiento);
                    }
                }
            }
            for (Concepto conceptosOldConcepto : conceptosOld) {
                if (!conceptosNew.contains(conceptosOldConcepto)) {
                    conceptosOldConcepto.setUnaEmpresaCC(null);
                    conceptosOldConcepto = em.merge(conceptosOldConcepto);
                }
            }
            for (Concepto conceptosNewConcepto : conceptosNew) {
                if (!conceptosOld.contains(conceptosNewConcepto)) {
                    Empresa oldUnaEmpresaCCOfConceptosNewConcepto = conceptosNewConcepto.getUnaEmpresaCC();
                    conceptosNewConcepto.setUnaEmpresaCC(empresa);
                    conceptosNewConcepto = em.merge(conceptosNewConcepto);
                    if (oldUnaEmpresaCCOfConceptosNewConcepto != null && !oldUnaEmpresaCCOfConceptosNewConcepto.equals(empresa)) {
                        oldUnaEmpresaCCOfConceptosNewConcepto.getConceptos().remove(conceptosNewConcepto);
                        oldUnaEmpresaCCOfConceptosNewConcepto = em.merge(oldUnaEmpresaCCOfConceptosNewConcepto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<Empleado> empleados = empresa.getEmpleados();
            for (Empleado empleadosEmpleado : empleados) {
                empleadosEmpleado.setUnaEmpresaE(null);
                empleadosEmpleado = em.merge(empleadosEmpleado);
            }
            List<Cuenta> cuentas = empresa.getCuentas();
            for (Cuenta cuentasCuenta : cuentas) {
                cuentasCuenta.setUnaEmpresaC(null);
                cuentasCuenta = em.merge(cuentasCuenta);
            }
            List<TipoMovimiento> tiposDeMovimientos = empresa.getTiposDeMovimientos();
            for (TipoMovimiento tiposDeMovimientosTipoMovimiento : tiposDeMovimientos) {
                tiposDeMovimientosTipoMovimiento.setUnaEmpresaTM(null);
                tiposDeMovimientosTipoMovimiento = em.merge(tiposDeMovimientosTipoMovimiento);
            }
            List<Concepto> conceptos = empresa.getConceptos();
            for (Concepto conceptosConcepto : conceptos) {
                conceptosConcepto.setUnaEmpresaCC(null);
                conceptosConcepto = em.merge(conceptosConcepto);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
