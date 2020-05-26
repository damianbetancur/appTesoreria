/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AreaJpaController;
import dao.Conexion;
import dao.EmpleadoJpaController;
import dao.TipoEmpleadoJpaController;
import dao.TurnoJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import model.Area;
import model.Empleado;
import model.Organismo;
import model.TipoEmpleado;
import model.Turno;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class EmpleadoController {
    //llamar al organismo Singleton
    //DAO
    private final EmpleadoJpaController empleadoDAO;
    private final TipoEmpleadoJpaController tipoEmpleadoDAO;
    private final AreaJpaController areaDAO;

    private final UsuarioJpaController usuarioDAO;
    private final TurnoJpaController turnoDAO;

    //Model
    private static Organismo organismoInstanciaUnica;

    public EmpleadoController() {
        this.organismoInstanciaUnica = LoginController.getInstanceOrganismo();

        this.empleadoDAO = new EmpleadoJpaController(Conexion.getEmf());
        this.tipoEmpleadoDAO = new TipoEmpleadoJpaController(Conexion.getEmf());
        this.usuarioDAO = new UsuarioJpaController(Conexion.getEmf());
        this.turnoDAO = new TurnoJpaController(Conexion.getEmf());
        this.areaDAO = new AreaJpaController(Conexion.getEmf());
    }

    public boolean agregarEmpleado(Empleado nuevoEmpleado) {
        //verifica que el dni sea unico
        boolean dniPermitido = true;
        for (Empleado empleadoRecorrido : empleadoDAO.findEmpleadoEntities()) {
            if (nuevoEmpleado.getDni().equals(empleadoRecorrido.getDni())) {
                dniPermitido = false;
            }
        }

        if (dniPermitido) {
            nuevoEmpleado.setUnOrganismoC(organismoInstanciaUnica);
            empleadoDAO.create(nuevoEmpleado);
        }
        return dniPermitido;
    }

    public boolean modificarEmpleado(Empleado actualEmpleado, Empleado nuevoEmpleado) throws Exception {
        //verifica que el dni sea unico
        nuevoEmpleado.setId(actualEmpleado.getId());
        nuevoEmpleado.setUnOrganismoC(organismoInstanciaUnica);
        nuevoEmpleado.getUnAreaA().setId(actualEmpleado.getUnAreaA().getId());
        boolean dniPermitido = true;
        if (actualEmpleado.getDni().equals(nuevoEmpleado.getDni())) {
            empleadoDAO.edit(nuevoEmpleado);
        } else {
            for (Empleado empleadoRecorrido : empleadoDAO.findEmpleadoEntities()) {
                if (nuevoEmpleado.getDni().equals(empleadoRecorrido.getDni())) {
                    dniPermitido = false;
                }
            }
            if (dniPermitido) {
                empleadoDAO.edit(nuevoEmpleado);
            }
        }
        return dniPermitido;
    }

    public boolean eliminarEmpleado(Empleado nuevoEmpleado) throws NonexistentEntityException {
        boolean estadoEliminacionUsuario = true;
        if (verificarEliminarEmpleado(nuevoEmpleado)) {
            empleadoDAO.destroy(nuevoEmpleado.getId());
        } else {
            estadoEliminacionUsuario = false;
        }
        return estadoEliminacionUsuario;
    }

    public List<Empleado> buscarTodasLosEmpleados() {
        List<Empleado> empleadosEncontrados = new ArrayList<>();
        empleadosEncontrados = empleadoDAO.findEmpleadoEntities();
        return empleadosEncontrados;
    }

    public List<Empleado> buscarEmpleadosPorDNI(String dni) {
        List<Empleado> empleadosEncontrados = new ArrayList<>();
        for (Empleado empleadoRecorrido : empleadoDAO.findEmpleadoEntities()) {
            if (empleadoRecorrido.getDni().contains(dni)) {
                empleadosEncontrados.add(empleadoRecorrido);
            }
        }
        return empleadosEncontrados;
    }

    public Vector<TipoEmpleado> buscarTodosLosTiposDeEmpleados() {
        Vector<TipoEmpleado> tiposEmpleadosEncontrados = new Vector<>();
        for (TipoEmpleado tipoEmpleadoRecorrido : tipoEmpleadoDAO.findTipoEmpleadoEntities()) {
            tiposEmpleadosEncontrados.add(tipoEmpleadoRecorrido);
        }
        return tiposEmpleadosEncontrados;
    }
    
    public Vector<Area> buscarTodasLasAreas() {
        Vector<Area> areasEncontradas = new Vector<>();
        for (Area areaRecorrido : areaDAO.findAreaEntities()) {
            areasEncontradas.add(areaRecorrido);
        }
        return areasEncontradas;
    }

    private boolean verificarEliminarEmpleado(Empleado empleadoAEliminar) {
        boolean estadoEliminacionUsuario = true;

        for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
            if (empleadoAEliminar.getId().equals(usuarioRecorrido.getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        for (Turno turnoRecorrido : turnoDAO.findTurnoEntities()) {
            if (empleadoAEliminar.getId().equals(turnoRecorrido.getUnaPersona().getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        return estadoEliminacionUsuario;
    }
}
