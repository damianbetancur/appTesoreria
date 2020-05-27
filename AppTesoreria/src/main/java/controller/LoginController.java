/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.EmpresaJpaController;
import dao.UsuarioJpaController;
import model.Empresa;
import model.Usuario;

/**
 * Controlador de Login de Usuario
 *
 */
public class LoginController {

    //DAO
    private final UsuarioJpaController usuarioDAO;
    private final EmpresaJpaController empresaDAO;

    //Model
    private static Usuario usuarioRegistradoInstanciaUnica = null;
    private static Empresa empresaInstanciaUnica = null;

    public LoginController() {
        //Inicializacion de DAO
        this.usuarioDAO = new UsuarioJpaController(Conexion.getEmf());
        this.empresaDAO = new EmpresaJpaController(Conexion.getEmf());
        
        //Singleton Empresa
        this.empresaInstanciaUnica = empresaDAO.findEmpresa(1l);
        createInstanceEmpresa();
    }

    /**
     * Creacion Singleton Usuario Logeado
     */
    private synchronized static void createInstanceUsuario() {
        if (usuarioRegistradoInstanciaUnica == null) {
            usuarioRegistradoInstanciaUnica = new Usuario();
        }
    }

    /**
     * Devuele la instancia unica del usuario logeado
     *
     * @return
     */
    public static Usuario getInstanceUsuario() {
        createInstanceUsuario();
        return usuarioRegistradoInstanciaUnica;
    }

    /**
     * Creacion Singleton organismo
     */
    private synchronized static void createInstanceEmpresa() {
        if (empresaInstanciaUnica == null) {
            empresaInstanciaUnica = new Empresa();
        }
    }

    /**
     * Devuele la instancia unica del Empresa
     *
     * @return
     */
    public static Empresa getInstanceEmpresa() {
        createInstanceUsuario();
        return empresaInstanciaUnica;
    }

    /**
     * Recibe un usuario desde la vista
     * Si el usuario y el password concuerdan, crea instancia unica de usuario proveniente de la base de datos y devuelve verdadero
     * Si el usuario no concuerda devuelve falso
     * @param unUsuario
     * @return
     */
    public boolean iniciarSesion(Usuario unUsuario) {
        boolean estado = false;
        usuarioRegistradoInstanciaUnica = usuarioDAO.iniciarSesion(unUsuario);
        if (usuarioRegistradoInstanciaUnica != null) {
            //Instacia Unica Singleton
            createInstanceUsuario();
            estado = true;
        }
        return estado;
    }
}
