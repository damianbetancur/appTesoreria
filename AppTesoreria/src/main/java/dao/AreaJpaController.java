/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Organismo;
import model.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Area;
import model.Turno;
import model.Tramite;
import model.TipoTramite;

/**
 *
 * @author Ariel
 */
public class AreaJpaController implements Serializable {

    public AreaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Area area) {
        if (area.getEmpleados() == null) {
            area.setEmpleados(new ArrayList<Empleado>());
        }
        if (area.getTurnos() == null) {
            area.setTurnos(new ArrayList<Turno>());
        }
        if (area.getTramites() == null) {
            area.setTramites(new ArrayList<Tramite>());
        }
        if (area.getTipoTramite() == null) {
            area.setTipoTramite(new ArrayList<TipoTramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Organismo unOrganismoA = area.getUnOrganismoA();
            if (unOrganismoA != null) {
                unOrganismoA = em.getReference(unOrganismoA.getClass(), unOrganismoA.getId());
                area.setUnOrganismoA(unOrganismoA);
            }
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : area.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            area.setEmpleados(attachedEmpleados);
            List<Turno> attachedTurnos = new ArrayList<Turno>();
            for (Turno turnosTurnoToAttach : area.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getId());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            area.setTurnos(attachedTurnos);
            List<Tramite> attachedTramites = new ArrayList<Tramite>();
            for (Tramite tramitesTramiteToAttach : area.getTramites()) {
                tramitesTramiteToAttach = em.getReference(tramitesTramiteToAttach.getClass(), tramitesTramiteToAttach.getId());
                attachedTramites.add(tramitesTramiteToAttach);
            }
            area.setTramites(attachedTramites);
            List<TipoTramite> attachedTipoTramite = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteTipoTramiteToAttach : area.getTipoTramite()) {
                tipoTramiteTipoTramiteToAttach = em.getReference(tipoTramiteTipoTramiteToAttach.getClass(), tipoTramiteTipoTramiteToAttach.getId());
                attachedTipoTramite.add(tipoTramiteTipoTramiteToAttach);
            }
            area.setTipoTramite(attachedTipoTramite);
            em.persist(area);
            if (unOrganismoA != null) {
                unOrganismoA.getAreas().add(area);
                unOrganismoA = em.merge(unOrganismoA);
            }
            for (Empleado empleadosEmpleado : area.getEmpleados()) {
                Area oldUnAreaAOfEmpleadosEmpleado = empleadosEmpleado.getUnAreaA();
                empleadosEmpleado.setUnAreaA(area);
                empleadosEmpleado = em.merge(empleadosEmpleado);
                if (oldUnAreaAOfEmpleadosEmpleado != null) {
                    oldUnAreaAOfEmpleadosEmpleado.getEmpleados().remove(empleadosEmpleado);
                    oldUnAreaAOfEmpleadosEmpleado = em.merge(oldUnAreaAOfEmpleadosEmpleado);
                }
            }
            for (Turno turnosTurno : area.getTurnos()) {
                Area oldUnAreaBOfTurnosTurno = turnosTurno.getUnAreaB();
                turnosTurno.setUnAreaB(area);
                turnosTurno = em.merge(turnosTurno);
                if (oldUnAreaBOfTurnosTurno != null) {
                    oldUnAreaBOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldUnAreaBOfTurnosTurno = em.merge(oldUnAreaBOfTurnosTurno);
                }
            }
            for (Tramite tramitesTramite : area.getTramites()) {
                Area oldUnAreaCOfTramitesTramite = tramitesTramite.getUnAreaC();
                tramitesTramite.setUnAreaC(area);
                tramitesTramite = em.merge(tramitesTramite);
                if (oldUnAreaCOfTramitesTramite != null) {
                    oldUnAreaCOfTramitesTramite.getTramites().remove(tramitesTramite);
                    oldUnAreaCOfTramitesTramite = em.merge(oldUnAreaCOfTramitesTramite);
                }
            }
            for (TipoTramite tipoTramiteTipoTramite : area.getTipoTramite()) {
                tipoTramiteTipoTramite.getAreas().add(area);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area persistentArea = em.find(Area.class, area.getId());
            Organismo unOrganismoAOld = persistentArea.getUnOrganismoA();
            Organismo unOrganismoANew = area.getUnOrganismoA();
            List<Empleado> empleadosOld = persistentArea.getEmpleados();
            List<Empleado> empleadosNew = area.getEmpleados();
            List<Turno> turnosOld = persistentArea.getTurnos();
            List<Turno> turnosNew = area.getTurnos();
            List<Tramite> tramitesOld = persistentArea.getTramites();
            List<Tramite> tramitesNew = area.getTramites();
            List<TipoTramite> tipoTramiteOld = persistentArea.getTipoTramite();
            List<TipoTramite> tipoTramiteNew = area.getTipoTramite();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadosOldEmpleado + " since its unAreaA field is not nullable.");
                }
            }
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnosOldTurno + " since its unAreaB field is not nullable.");
                }
            }
            for (Tramite tramitesOldTramite : tramitesOld) {
                if (!tramitesNew.contains(tramitesOldTramite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tramite " + tramitesOldTramite + " since its unAreaC field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unOrganismoANew != null) {
                unOrganismoANew = em.getReference(unOrganismoANew.getClass(), unOrganismoANew.getId());
                area.setUnOrganismoA(unOrganismoANew);
            }
            List<Empleado> attachedEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado empleadosNewEmpleadoToAttach : empleadosNew) {
                empleadosNewEmpleadoToAttach = em.getReference(empleadosNewEmpleadoToAttach.getClass(), empleadosNewEmpleadoToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewEmpleadoToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            area.setEmpleados(empleadosNew);
            List<Turno> attachedTurnosNew = new ArrayList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getId());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            area.setTurnos(turnosNew);
            List<Tramite> attachedTramitesNew = new ArrayList<Tramite>();
            for (Tramite tramitesNewTramiteToAttach : tramitesNew) {
                tramitesNewTramiteToAttach = em.getReference(tramitesNewTramiteToAttach.getClass(), tramitesNewTramiteToAttach.getId());
                attachedTramitesNew.add(tramitesNewTramiteToAttach);
            }
            tramitesNew = attachedTramitesNew;
            area.setTramites(tramitesNew);
            List<TipoTramite> attachedTipoTramiteNew = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteNewTipoTramiteToAttach : tipoTramiteNew) {
                tipoTramiteNewTipoTramiteToAttach = em.getReference(tipoTramiteNewTipoTramiteToAttach.getClass(), tipoTramiteNewTipoTramiteToAttach.getId());
                attachedTipoTramiteNew.add(tipoTramiteNewTipoTramiteToAttach);
            }
            tipoTramiteNew = attachedTipoTramiteNew;
            area.setTipoTramite(tipoTramiteNew);
            area = em.merge(area);
            if (unOrganismoAOld != null && !unOrganismoAOld.equals(unOrganismoANew)) {
                unOrganismoAOld.getAreas().remove(area);
                unOrganismoAOld = em.merge(unOrganismoAOld);
            }
            if (unOrganismoANew != null && !unOrganismoANew.equals(unOrganismoAOld)) {
                unOrganismoANew.getAreas().add(area);
                unOrganismoANew = em.merge(unOrganismoANew);
            }
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    Area oldUnAreaAOfEmpleadosNewEmpleado = empleadosNewEmpleado.getUnAreaA();
                    empleadosNewEmpleado.setUnAreaA(area);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                    if (oldUnAreaAOfEmpleadosNewEmpleado != null && !oldUnAreaAOfEmpleadosNewEmpleado.equals(area)) {
                        oldUnAreaAOfEmpleadosNewEmpleado.getEmpleados().remove(empleadosNewEmpleado);
                        oldUnAreaAOfEmpleadosNewEmpleado = em.merge(oldUnAreaAOfEmpleadosNewEmpleado);
                    }
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Area oldUnAreaBOfTurnosNewTurno = turnosNewTurno.getUnAreaB();
                    turnosNewTurno.setUnAreaB(area);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldUnAreaBOfTurnosNewTurno != null && !oldUnAreaBOfTurnosNewTurno.equals(area)) {
                        oldUnAreaBOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldUnAreaBOfTurnosNewTurno = em.merge(oldUnAreaBOfTurnosNewTurno);
                    }
                }
            }
            for (Tramite tramitesNewTramite : tramitesNew) {
                if (!tramitesOld.contains(tramitesNewTramite)) {
                    Area oldUnAreaCOfTramitesNewTramite = tramitesNewTramite.getUnAreaC();
                    tramitesNewTramite.setUnAreaC(area);
                    tramitesNewTramite = em.merge(tramitesNewTramite);
                    if (oldUnAreaCOfTramitesNewTramite != null && !oldUnAreaCOfTramitesNewTramite.equals(area)) {
                        oldUnAreaCOfTramitesNewTramite.getTramites().remove(tramitesNewTramite);
                        oldUnAreaCOfTramitesNewTramite = em.merge(oldUnAreaCOfTramitesNewTramite);
                    }
                }
            }
            for (TipoTramite tipoTramiteOldTipoTramite : tipoTramiteOld) {
                if (!tipoTramiteNew.contains(tipoTramiteOldTipoTramite)) {
                    tipoTramiteOldTipoTramite.getAreas().remove(area);
                    tipoTramiteOldTipoTramite = em.merge(tipoTramiteOldTipoTramite);
                }
            }
            for (TipoTramite tipoTramiteNewTipoTramite : tipoTramiteNew) {
                if (!tipoTramiteOld.contains(tipoTramiteNewTipoTramite)) {
                    tipoTramiteNewTipoTramite.getAreas().add(area);
                    tipoTramiteNewTipoTramite = em.merge(tipoTramiteNewTipoTramite);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = area.getId();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadosOrphanCheck = area.getEmpleados();
            for (Empleado empleadosOrphanCheckEmpleado : empleadosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Empleado " + empleadosOrphanCheckEmpleado + " in its empleados field has a non-nullable unAreaA field.");
            }
            List<Turno> turnosOrphanCheck = area.getTurnos();
            for (Turno turnosOrphanCheckTurno : turnosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Turno " + turnosOrphanCheckTurno + " in its turnos field has a non-nullable unAreaB field.");
            }
            List<Tramite> tramitesOrphanCheck = area.getTramites();
            for (Tramite tramitesOrphanCheckTramite : tramitesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Tramite " + tramitesOrphanCheckTramite + " in its tramites field has a non-nullable unAreaC field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Organismo unOrganismoA = area.getUnOrganismoA();
            if (unOrganismoA != null) {
                unOrganismoA.getAreas().remove(area);
                unOrganismoA = em.merge(unOrganismoA);
            }
            List<TipoTramite> tipoTramite = area.getTipoTramite();
            for (TipoTramite tipoTramiteTipoTramite : tipoTramite) {
                tipoTramiteTipoTramite.getAreas().remove(area);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
            }
            em.remove(area);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
