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
import model.Area;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Persona;
import model.Empleado;
import model.Organismo;

/**
 *
 * @author Ariel
 */
public class OrganismoJpaController implements Serializable {

    public OrganismoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organismo organismo) {
        if (organismo.getAreas() == null) {
            organismo.setAreas(new ArrayList<Area>());
        }
        if (organismo.getPersonas() == null) {
            organismo.setPersonas(new ArrayList<Persona>());
        }
        if (organismo.getEmpleados() == null) {
            organismo.setEmpleados(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Area> attachedAreas = new ArrayList<Area>();
            for (Area areasAreaToAttach : organismo.getAreas()) {
                areasAreaToAttach = em.getReference(areasAreaToAttach.getClass(), areasAreaToAttach.getId());
                attachedAreas.add(areasAreaToAttach);
            }
            organismo.setAreas(attachedAreas);
            List<Persona> attachedPersonas = new ArrayList<Persona>();
            for (Persona personasPersonaToAttach : organismo.getPersonas()) {
                personasPersonaToAttach = em.getReference(personasPersonaToAttach.getClass(), personasPersonaToAttach.getId());
                attachedPersonas.add(personasPersonaToAttach);
            }
            organismo.setPersonas(attachedPersonas);
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : organismo.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            organismo.setEmpleados(attachedEmpleados);
            em.persist(organismo);
            for (Area areasArea : organismo.getAreas()) {
                Organismo oldUnOrganismoAOfAreasArea = areasArea.getUnOrganismoA();
                areasArea.setUnOrganismoA(organismo);
                areasArea = em.merge(areasArea);
                if (oldUnOrganismoAOfAreasArea != null) {
                    oldUnOrganismoAOfAreasArea.getAreas().remove(areasArea);
                    oldUnOrganismoAOfAreasArea = em.merge(oldUnOrganismoAOfAreasArea);
                }
            }
            for (Persona personasPersona : organismo.getPersonas()) {
                Organismo oldUnOrganismoBOfPersonasPersona = personasPersona.getUnOrganismoB();
                personasPersona.setUnOrganismoB(organismo);
                personasPersona = em.merge(personasPersona);
                if (oldUnOrganismoBOfPersonasPersona != null) {
                    oldUnOrganismoBOfPersonasPersona.getPersonas().remove(personasPersona);
                    oldUnOrganismoBOfPersonasPersona = em.merge(oldUnOrganismoBOfPersonasPersona);
                }
            }
            for (Empleado empleadosEmpleado : organismo.getEmpleados()) {
                Organismo oldUnOrganismoCOfEmpleadosEmpleado = empleadosEmpleado.getUnOrganismoC();
                empleadosEmpleado.setUnOrganismoC(organismo);
                empleadosEmpleado = em.merge(empleadosEmpleado);
                if (oldUnOrganismoCOfEmpleadosEmpleado != null) {
                    oldUnOrganismoCOfEmpleadosEmpleado.getEmpleados().remove(empleadosEmpleado);
                    oldUnOrganismoCOfEmpleadosEmpleado = em.merge(oldUnOrganismoCOfEmpleadosEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Organismo organismo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Organismo persistentOrganismo = em.find(Organismo.class, organismo.getId());
            List<Area> areasOld = persistentOrganismo.getAreas();
            List<Area> areasNew = organismo.getAreas();
            List<Persona> personasOld = persistentOrganismo.getPersonas();
            List<Persona> personasNew = organismo.getPersonas();
            List<Empleado> empleadosOld = persistentOrganismo.getEmpleados();
            List<Empleado> empleadosNew = organismo.getEmpleados();
            List<String> illegalOrphanMessages = null;
            for (Area areasOldArea : areasOld) {
                if (!areasNew.contains(areasOldArea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Area " + areasOldArea + " since its unOrganismoA field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Area> attachedAreasNew = new ArrayList<Area>();
            for (Area areasNewAreaToAttach : areasNew) {
                areasNewAreaToAttach = em.getReference(areasNewAreaToAttach.getClass(), areasNewAreaToAttach.getId());
                attachedAreasNew.add(areasNewAreaToAttach);
            }
            areasNew = attachedAreasNew;
            organismo.setAreas(areasNew);
            List<Persona> attachedPersonasNew = new ArrayList<Persona>();
            for (Persona personasNewPersonaToAttach : personasNew) {
                personasNewPersonaToAttach = em.getReference(personasNewPersonaToAttach.getClass(), personasNewPersonaToAttach.getId());
                attachedPersonasNew.add(personasNewPersonaToAttach);
            }
            personasNew = attachedPersonasNew;
            organismo.setPersonas(personasNew);
            List<Empleado> attachedEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado empleadosNewEmpleadoToAttach : empleadosNew) {
                empleadosNewEmpleadoToAttach = em.getReference(empleadosNewEmpleadoToAttach.getClass(), empleadosNewEmpleadoToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewEmpleadoToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            organismo.setEmpleados(empleadosNew);
            organismo = em.merge(organismo);
            for (Area areasNewArea : areasNew) {
                if (!areasOld.contains(areasNewArea)) {
                    Organismo oldUnOrganismoAOfAreasNewArea = areasNewArea.getUnOrganismoA();
                    areasNewArea.setUnOrganismoA(organismo);
                    areasNewArea = em.merge(areasNewArea);
                    if (oldUnOrganismoAOfAreasNewArea != null && !oldUnOrganismoAOfAreasNewArea.equals(organismo)) {
                        oldUnOrganismoAOfAreasNewArea.getAreas().remove(areasNewArea);
                        oldUnOrganismoAOfAreasNewArea = em.merge(oldUnOrganismoAOfAreasNewArea);
                    }
                }
            }
            for (Persona personasOldPersona : personasOld) {
                if (!personasNew.contains(personasOldPersona)) {
                    personasOldPersona.setUnOrganismoB(null);
                    personasOldPersona = em.merge(personasOldPersona);
                }
            }
            for (Persona personasNewPersona : personasNew) {
                if (!personasOld.contains(personasNewPersona)) {
                    Organismo oldUnOrganismoBOfPersonasNewPersona = personasNewPersona.getUnOrganismoB();
                    personasNewPersona.setUnOrganismoB(organismo);
                    personasNewPersona = em.merge(personasNewPersona);
                    if (oldUnOrganismoBOfPersonasNewPersona != null && !oldUnOrganismoBOfPersonasNewPersona.equals(organismo)) {
                        oldUnOrganismoBOfPersonasNewPersona.getPersonas().remove(personasNewPersona);
                        oldUnOrganismoBOfPersonasNewPersona = em.merge(oldUnOrganismoBOfPersonasNewPersona);
                    }
                }
            }
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    empleadosOldEmpleado.setUnOrganismoC(null);
                    empleadosOldEmpleado = em.merge(empleadosOldEmpleado);
                }
            }
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    Organismo oldUnOrganismoCOfEmpleadosNewEmpleado = empleadosNewEmpleado.getUnOrganismoC();
                    empleadosNewEmpleado.setUnOrganismoC(organismo);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                    if (oldUnOrganismoCOfEmpleadosNewEmpleado != null && !oldUnOrganismoCOfEmpleadosNewEmpleado.equals(organismo)) {
                        oldUnOrganismoCOfEmpleadosNewEmpleado.getEmpleados().remove(empleadosNewEmpleado);
                        oldUnOrganismoCOfEmpleadosNewEmpleado = em.merge(oldUnOrganismoCOfEmpleadosNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = organismo.getId();
                if (findOrganismo(id) == null) {
                    throw new NonexistentEntityException("The organismo with id " + id + " no longer exists.");
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
            Organismo organismo;
            try {
                organismo = em.getReference(Organismo.class, id);
                organismo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organismo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Area> areasOrphanCheck = organismo.getAreas();
            for (Area areasOrphanCheckArea : areasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organismo (" + organismo + ") cannot be destroyed since the Area " + areasOrphanCheckArea + " in its areas field has a non-nullable unOrganismoA field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> personas = organismo.getPersonas();
            for (Persona personasPersona : personas) {
                personasPersona.setUnOrganismoB(null);
                personasPersona = em.merge(personasPersona);
            }
            List<Empleado> empleados = organismo.getEmpleados();
            for (Empleado empleadosEmpleado : empleados) {
                empleadosEmpleado.setUnOrganismoC(null);
                empleadosEmpleado = em.merge(empleadosEmpleado);
            }
            em.remove(organismo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Organismo> findOrganismoEntities() {
        return findOrganismoEntities(true, -1, -1);
    }

    public List<Organismo> findOrganismoEntities(int maxResults, int firstResult) {
        return findOrganismoEntities(false, maxResults, firstResult);
    }

    private List<Organismo> findOrganismoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Organismo.class));
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

    public Organismo findOrganismo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organismo.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganismoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Organismo> rt = cq.from(Organismo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
