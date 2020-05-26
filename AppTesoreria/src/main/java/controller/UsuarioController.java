/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.EmpleadoJpaController;
import dao.PersonaJpaController;
import dao.TipoUsuarioJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import model.Empleado;
import model.Persona;
import model.TipoUsuario;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class UsuarioController {

    //DAO
    private final UsuarioJpaController usuarioDAO;
    private final EmpleadoJpaController empleadoDAO;
    private final PersonaJpaController personaDAO;
    private final TipoUsuarioJpaController tipoUsuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioJpaController(Conexion.getEmf());
        this.tipoUsuarioDAO = new TipoUsuarioJpaController(Conexion.getEmf());
        this.empleadoDAO = new EmpleadoJpaController(Conexion.getEmf());
        this.personaDAO = new PersonaJpaController(Conexion.getEmf());
    }

    public boolean agregarUsuario(Usuario nuevoUsuario) {
        //verifica que el dni sea unico
        boolean nicknamePermitido = true;
        for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
            if (nuevoUsuario.getNombre().equals(usuarioRecorrido.getNombre())) {
                nicknamePermitido = false;
            }
        }

        if (nicknamePermitido) {
            
            System.out.println("USUARIO");
            System.out.println("nombre"+nuevoUsuario.getNombre());
            System.out.println("clave"+nuevoUsuario.getClave());
            System.out.println("Tipo de usuario"+nuevoUsuario.getTipoUsuario());
            System.out.println("empleado"+nuevoUsuario.getUnEmpleado());
            System.out.println("persona"+nuevoUsuario.getUnaPersona());
           
            if (nuevoUsuario.getUnEmpleado() == null && nuevoUsuario.getUnaPersona() == null) {
                //usuarioDAO.create(nuevoUsuario);
            } else{
                
            }

        }
        return nicknamePermitido;
    }

    public boolean modificarUsuario(Usuario actualUsuario, Usuario nuevoUsuario) throws Exception {
        //verifica que el dni sea unico
        nuevoUsuario.setId(actualUsuario.getId());
        boolean dniPermitido = true;
        if (actualUsuario.getNombre().equals(nuevoUsuario.getNombre())) {
            usuarioDAO.edit(nuevoUsuario);
        } else {
            for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
                if (nuevoUsuario.getNombre().equals(usuarioRecorrido.getNombre())) {
                    dniPermitido = false;
                }
            }
            if (dniPermitido) {
                usuarioDAO.edit(nuevoUsuario);
            }
        }
        return dniPermitido;
    }

    public void eliminarUsuario(Usuario nuevoUsuario) throws NonexistentEntityException {
        usuarioDAO.destroy(nuevoUsuario.getId());
    }

    public List<Usuario> buscarTodosLosUsuarios() {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        usuariosEncontrados = usuarioDAO.findUsuarioEntities();
        return usuariosEncontrados;
    }

    public List<Usuario> buscarUsuariosPorNickName(String nickName) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
            if (usuarioRecorrido.getNombre().contains(nickName)) {
                usuariosEncontrados.add(usuarioRecorrido);
            }
        }
        return usuariosEncontrados;
    }

    public Vector<TipoUsuario> buscarTodosLosTiposDeUsuarios() {
        Vector<TipoUsuario> tiposUsuariosEncontrados = new Vector<>();
        for (TipoUsuario tipoUsuarioRecorrido : tipoUsuarioDAO.findTipoUsuarioEntities()) {
            tiposUsuariosEncontrados.add(tipoUsuarioRecorrido);
        }
        return tiposUsuariosEncontrados;
    }
    
    public List<Empleado> buscarTodosLosEmpleados() {
        List<Empleado> empleadosEncontrados = new ArrayList<>();
        empleadosEncontrados = empleadoDAO.findEmpleadoEntities();
        return empleadosEncontrados;
    }
    
    public List<Empleado> buscarEmpleadoDeUsuarioPorDNI(String dni) {
        List<Empleado> empleadosEncontrados = new ArrayList<>();
        for (Empleado empleadoRecorrido : empleadoDAO.findEmpleadoEntities()) {
            if (empleadoRecorrido.getDni().contains(dni)) {
                empleadosEncontrados.add(empleadoRecorrido);
            }
        }
        return empleadosEncontrados;
    }
    
    public List<Persona> buscarTodasLasPersonas() {
        List<Persona> personasEncontradas = new ArrayList<>();
        personasEncontradas = personaDAO.findPersonaEntities();
        return personasEncontradas;
    }

    public List<Persona> buscarPersonaDeUsuarioPorDNI(String dni) {
        List<Persona> personasEncontradas = new ArrayList<>();
        for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
            if (personaRecorrido.getDni().contains(dni)) {
                personasEncontradas.add(personaRecorrido);
            }
        }
        return personasEncontradas;
    }

}
