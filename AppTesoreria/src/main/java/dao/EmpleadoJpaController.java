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
import model.Organismo;
import model.Area;
import model.TipoTramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Empleado;

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
        if (empleado.getTipoTramite() == null) {
            empleado.setTipoTramite(new ArrayList<TipoTramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Organismo unOrganismoC = empleado.getUnOrganismoC();
            if (unOrganismoC != null) {
                unOrganismoC = em.getReference(unOrganismoC.getClass(), unOrganismoC.getId());
                empleado.setUnOrganismoC(unOrganismoC);
            }
            Area unAreaA = empleado.getUnAreaA();
            if (unAreaA != null) {
                unAreaA = em.getReference(unAreaA.getClass(), unAreaA.getId());
                empleado.setUnAreaA(unAreaA);
            }
            List<TipoTramite> attachedTipoTramite = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteTipoTramiteToAttach : empleado.getTipoTramite()) {
                tipoTramiteTipoTramiteToAttach = em.getReference(tipoTramiteTipoTramiteToAttach.getClass(), tipoTramiteTipoTramiteToAttach.getId());
                attachedTipoTramite.add(tipoTramiteTipoTramiteToAttach);
            }
            empleado.setTipoTramite(attachedTipoTramite);
            em.persist(empleado);
            if (unOrganismoC != null) {
                unOrganismoC.getEmpleados().add(empleado);
                unOrganismoC = em.merge(unOrganismoC);
            }
            if (unAreaA != null) {
                unAreaA.getEmpleados().add(empleado);
                unAreaA = em.merge(unAreaA);
            }
            for (TipoTramite tipoTramiteTipoTramite : empleado.getTipoTramite()) {
                tipoTramiteTipoTramite.getEmpleados().add(empleado);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
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
            Organismo unOrganismoCOld = persistentEmpleado.getUnOrganismoC();
            Organismo unOrganismoCNew = empleado.getUnOrganismoC();
            Area unAreaAOld = persistentEmpleado.getUnAreaA();
            Area unAreaANew = empleado.getUnAreaA();
            List<TipoTramite> tipoTramiteOld = persistentEmpleado.getTipoTramite();
            List<TipoTramite> tipoTramiteNew = empleado.getTipoTramite();
            if (unOrganismoCNew != null) {
                unOrganismoCNew = em.getReference(unOrganismoCNew.getClass(), unOrganismoCNew.getId());
                empleado.setUnOrganismoC(unOrganismoCNew);
            }
            if (unAreaANew != null) {
                unAreaANew = em.getReference(unAreaANew.getClass(), unAreaANew.getId());
                empleado.setUnAreaA(unAreaANew);
            }
            List<TipoTramite> attachedTipoTramiteNew = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteNewTipoTramiteToAttach : tipoTramiteNew) {
                tipoTramiteNewTipoTramiteToAttach = em.getReference(tipoTramiteNewTipoTramiteToAttach.getClass(), tipoTramiteNewTipoTramiteToAttach.getId());
                attachedTipoTramiteNew.add(tipoTramiteNewTipoTramiteToAttach);
            }
            tipoTramiteNew = attachedTipoTramiteNew;
            empleado.setTipoTramite(tipoTramiteNew);
            empleado = em.merge(empleado);
            if (unOrganismoCOld != null && !unOrganismoCOld.equals(unOrganismoCNew)) {
                unOrganismoCOld.getEmpleados().remove(empleado);
                unOrganismoCOld = em.merge(unOrganismoCOld);
            }
            if (unOrganismoCNew != null && !unOrganismoCNew.equals(unOrganismoCOld)) {
                unOrganismoCNew.getEmpleados().add(empleado);
                unOrganismoCNew = em.merge(unOrganismoCNew);
            }
            if (unAreaAOld != null && !unAreaAOld.equals(unAreaANew)) {
                unAreaAOld.getEmpleados().remove(empleado);
                unAreaAOld = em.merge(unAreaAOld);
            }
            if (unAreaANew != null && !unAreaANew.equals(unAreaAOld)) {
                unAreaANew.getEmpleados().add(empleado);
                unAreaANew = em.merge(unAreaANew);
            }
            for (TipoTramite tipoTramiteOldTipoTramite : tipoTramiteOld) {
                if (!tipoTramiteNew.contains(tipoTramiteOldTipoTramite)) {
                    tipoTramiteOldTipoTramite.getEmpleados().remove(empleado);
                    tipoTramiteOldTipoTramite = em.merge(tipoTramiteOldTipoTramite);
                }
            }
            for (TipoTramite tipoTramiteNewTipoTramite : tipoTramiteNew) {
                if (!tipoTramiteOld.contains(tipoTramiteNewTipoTramite)) {
                    tipoTramiteNewTipoTramite.getEmpleados().add(empleado);
                    tipoTramiteNewTipoTramite = em.merge(tipoTramiteNewTipoTramite);
                }
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
            Organismo unOrganismoC = empleado.getUnOrganismoC();
            if (unOrganismoC != null) {
                unOrganismoC.getEmpleados().remove(empleado);
                unOrganismoC = em.merge(unOrganismoC);
            }
            Area unAreaA = empleado.getUnAreaA();
            if (unAreaA != null) {
                unAreaA.getEmpleados().remove(empleado);
                unAreaA = em.merge(unAreaA);
            }
            List<TipoTramite> tipoTramite = empleado.getTipoTramite();
            for (TipoTramite tipoTramiteTipoTramite : tipoTramite) {
                tipoTramiteTipoTramite.getEmpleados().remove(empleado);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
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
