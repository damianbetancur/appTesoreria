/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AreaJpaController;
import dao.Conexion;
import dao.EmpleadoJpaController;
import dao.EstadoTurnoJpaController;
import dao.HorarioAtencionTurnoJpaController;
import dao.TipoAtencionJpaController;
import dao.TipoEmpleadoJpaController;
import dao.TurnoJpaController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Tramite;
import model.Turno;

/**
 *
 * @author Ariel
 */
public class ProcesarTramiteController {

    private final Tramite nuevoTramite;

    private final TurnoJpaController turnoDAO;

    private final AreaJpaController areaDAO;
    private final EmpleadoJpaController empleadoDAO;
    private final TipoEmpleadoJpaController tipoEmpleadoDAO;
    private final TipoAtencionJpaController tipoAtencionDAO;
    private final HorarioAtencionTurnoJpaController horarioTurnoDAO;
    private final EstadoTurnoJpaController estadoTurnoDAO;

    public ProcesarTramiteController() {

        this.turnoDAO = new TurnoJpaController(Conexion.getEmf());

        this.nuevoTramite = new Tramite();

        areaDAO = new AreaJpaController(Conexion.getEmf());

        empleadoDAO = new EmpleadoJpaController(Conexion.getEmf());

        tipoEmpleadoDAO = new TipoEmpleadoJpaController(Conexion.getEmf());

        tipoAtencionDAO = new TipoAtencionJpaController(Conexion.getEmf());

        horarioTurnoDAO = new HorarioAtencionTurnoJpaController(Conexion.getEmf());

        estadoTurnoDAO = new EstadoTurnoJpaController(Conexion.getEmf());
    }

    public List<Turno> buscarTodosLosTurnos() {
        List<Turno> turnosEncontrados = new ArrayList<>();

        LoginController.getInstanceUsuario().getUnEmpleado();

        for (Turno turnoRecorrido : turnoDAO.findTurnoEntities()) {
            turnosEncontrados.add(turnoRecorrido);
        }
        return turnosEncontrados;
    }

    public Tramite getNuevoTramite() {
        return nuevoTramite;
    }

    public List<Turno> buscarTodosLosTurnosDeUnEmpleadoEnElDiaActual() {
        List<Turno> turnosParaAtenderA = new ArrayList<>();
        List<Turno> turnosParaAtenderOrdenado = turnoDAO.buscarTurnosDelEmpleado(LoginController.getInstanceUsuario().getUnEmpleado().getUnAreaA(), LoginController.getInstanceUsuario().getUnEmpleado(), estadoTurnoDAO.findEstadoTurno(1l), new Date());
        //Todos los turnos de un area
        for (Turno tr : turnosParaAtenderOrdenado) {
            turnosParaAtenderA.add(tr);
        }
        
        
        return turnosParaAtenderA;
    }
    
    
    

}
