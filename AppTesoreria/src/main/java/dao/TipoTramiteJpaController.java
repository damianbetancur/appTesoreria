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
import model.Area;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Empleado;
import model.TipoTramite;

/**
 *
 * @author Ariel
 */
public class TipoTramiteJpaController implements Serializable {

    public TipoTramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTramite tipoTramite) {
        if (tipoTramite.getAreas() == null) {
            tipoTramite.setAreas(new ArrayList<Area>());
        }
        if (tipoTramite.getEmpleados() == null) {
            tipoTramite.setEmpleados(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Area> attachedAreas = new ArrayList<Area>();
            for (Area areasAreaToAttach : tipoTramite.getAreas()) {
                areasAreaToAttach = em.getReference(areasAreaToAttach.getClass(), areasAreaToAttach.getId());
                attachedAreas.add(areasAreaToAttach);
            }
            tipoTramite.setAreas(attachedAreas);
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : tipoTramite.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            tipoTramite.setEmpleados(attachedEmpleados);
            em.persist(tipoTramite);
            for (Area areasArea : tipoTramite.getAreas()) {
                areasArea.getTipoTramite().add(tipoTramite);
                areasArea = em.merge(areasArea);
            }
            for (Empleado empleadosEmpleado : tipoTramite.getEmpleados()) {
                empleadosEmpleado.getTipoTramite().add(tipoTramite);
                empleadosEmpleado = em.merge(empleadosEmpleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTramite tipoTramite) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTramite persistentTipoTramite = em.find(TipoTramite.class, tipoTramite.getId());
            List<Area> areasOld = persistentTipoTramite.getAreas();
            List<Area> areasNew = tipoTramite.getAreas();
            List<Empleado> empleadosOld = persistentTipoTramite.getEmpleados();
            List<Empleado> empleadosNew = tipoTramite.getEmpleados();
            List<Area> attachedAreasNew = new ArrayList<Area>();
            for (Area areasNewAreaToAttach : areasNew) {
                areasNewAreaToAttach = em.getReference(areasNewAreaToAttach.getClass(), areasNewAreaToAttach.getId());
                attachedAreasNew.add(areasNewAreaToAttach);
            }
            areasNew = attachedAreasNew;
            tipoTramite.setAreas(areasNew);
            List<Empleado> attachedEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado empleadosNewEmpleadoToAttach : empleadosNew) {
                empleadosNewEmpleadoToAttach = em.getReference(empleadosNewEmpleadoToAttach.getClass(), empleadosNewEmpleadoToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewEmpleadoToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            tipoTramite.setEmpleados(empleadosNew);
            tipoTramite = em.merge(tipoTramite);
            for (Area areasOldArea : areasOld) {
                if (!areasNew.contains(areasOldArea)) {
                    areasOldArea.getTipoTramite().remove(tipoTramite);
                    areasOldArea = em.merge(areasOldArea);
                }
            }
            for (Area areasNewArea : areasNew) {
                if (!areasOld.contains(areasNewArea)) {
                    areasNewArea.getTipoTramite().add(tipoTramite);
                    areasNewArea = em.merge(areasNewArea);
                }
            }
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    empleadosOldEmpleado.getTipoTramite().remove(tipoTramite);
                    empleadosOldEmpleado = em.merge(empleadosOldEmpleado);
                }
            }
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    empleadosNewEmpleado.getTipoTramite().add(tipoTramite);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoTramite.getId();
                if (findTipoTramite(id) == null) {
                    throw new NonexistentEntityException("The tipoTramite with id " + id + " no longer exists.");
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
            TipoTramite tipoTramite;
            try {
                tipoTramite = em.getReference(TipoTramite.class, id);
                tipoTramite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTramite with id " + id + " no longer exists.", enfe);
            }
            List<Area> areas = tipoTramite.getAreas();
            for (Area areasArea : areas) {
                areasArea.getTipoTramite().remove(tipoTramite);
                areasArea = em.merge(areasArea);
            }
            List<Empleado> empleados = tipoTramite.getEmpleados();
            for (Empleado empleadosEmpleado : empleados) {
                empleadosEmpleado.getTipoTramite().remove(tipoTramite);
                empleadosEmpleado = em.merge(empleadosEmpleado);
            }
            em.remove(tipoTramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTramite> findTipoTramiteEntities() {
        return findTipoTramiteEntities(true, -1, -1);
    }

    public List<TipoTramite> findTipoTramiteEntities(int maxResults, int firstResult) {
        return findTipoTramiteEntities(false, maxResults, firstResult);
    }

    private List<TipoTramite> findTipoTramiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTramite.class));
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

    public TipoTramite findTipoTramite(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTramite.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTramiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTramite> rt = cq.from(TipoTramite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
