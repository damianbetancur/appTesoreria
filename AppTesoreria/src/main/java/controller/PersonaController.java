/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.PersonaJpaController;
import dao.TipoPersonaJpaController;
import dao.TurnoJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import model.Organismo;
import model.Persona;
import model.TipoPersona;
import model.Turno;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class PersonaController {

    //llamar al organismo Singleton
    //DAO
    private final PersonaJpaController personaDAO;
    private final TipoPersonaJpaController tipoPersonaDAO;

    private final UsuarioJpaController usuarioDAO;
    private final TurnoJpaController turnoDAO;

    //Model
    private static Organismo organismoInstanciaUnica;

    public PersonaController() {
        this.organismoInstanciaUnica = LoginController.getInstanceOrganismo();

        this.personaDAO = new PersonaJpaController(Conexion.getEmf());
        this.tipoPersonaDAO = new TipoPersonaJpaController(Conexion.getEmf());
        this.usuarioDAO = new UsuarioJpaController(Conexion.getEmf());
        this.turnoDAO = new TurnoJpaController(Conexion.getEmf());
    }

    public boolean agregarPersona(Persona nuevaPersona) {
        //verifica que el dni sea unico
        boolean dniPermitido = true;
        for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
            if (nuevaPersona.getDni().equals(personaRecorrido.getDni())) {
                dniPermitido = false;
            }
        }

        if (dniPermitido) {
            nuevaPersona.setUnOrganismoB(organismoInstanciaUnica);
            personaDAO.create(nuevaPersona);
        }
        return dniPermitido;
    }

    public boolean modificarPersona(Persona actualPersona, Persona nuevaPersona) throws Exception {
        //verifica que el dni sea unico
        nuevaPersona.setId(actualPersona.getId());
        nuevaPersona.setUnOrganismoB(organismoInstanciaUnica);
        boolean dniPermitido = true;
        if (actualPersona.getDni().equals(nuevaPersona.getDni())) {
            personaDAO.edit(nuevaPersona);
        } else {
            for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
                if (nuevaPersona.getDni().equals(personaRecorrido.getDni())) {
                    dniPermitido = false;
                }
            }
            if (dniPermitido) {
                personaDAO.edit(nuevaPersona);
            }
        }
        return dniPermitido;
    }

    public boolean eliminarPersona(Persona nuevaPersona) throws NonexistentEntityException {
        boolean estadoEliminacionUsuario = true;
        if (verificarEliminarPersona(nuevaPersona)) {
            personaDAO.destroy(nuevaPersona.getId());
        } else {
            estadoEliminacionUsuario = false;
        }
        return estadoEliminacionUsuario;
    }

    public List<Persona> buscarTodasLasPersonas() {
        List<Persona> personasEncontradas = new ArrayList<>();
        personasEncontradas = personaDAO.findPersonaEntities();
        return personasEncontradas;
    }

    public List<Persona> buscarPersonasPorDNI(String dni) {
        List<Persona> personasEncontradas = new ArrayList<>();
        for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
            if (personaRecorrido.getDni().contains(dni)) {
                personasEncontradas.add(personaRecorrido);
            }
        }
        return personasEncontradas;
    }

    public Vector<TipoPersona> buscarTodosLosTiposDePersonas() {
        Vector<TipoPersona> tiposPersonasEncontradas = new Vector<>();
        for (TipoPersona tipoPersonaRecorrido : tipoPersonaDAO.findTipoPersonaEntities()) {
            if (!tipoPersonaRecorrido.getDescripcion().equals("Publico en General")) {
                tiposPersonasEncontradas.add(tipoPersonaRecorrido);
            }
        }
        return tiposPersonasEncontradas;
    }

    private boolean verificarEliminarPersona(Persona personaAEliminar) {
        boolean estadoEliminacionUsuario = true;

        for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
            if (personaAEliminar.getId().equals(usuarioRecorrido.getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        for (Turno turnoRecorrido : turnoDAO.findTurnoEntities()) {
            if (personaAEliminar.getId().equals(turnoRecorrido.getUnaPersona().getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        return estadoEliminacionUsuario;
    }
}
