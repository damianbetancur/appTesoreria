/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.EmpresaJpaController;
import model.Empresa;

/**
 *
 * @author Ariel
 */
public class EmpresaController {

    //DAO
    private final EmpresaJpaController organismoDAO;

    //Model
    private static Empresa organismoInstanciaUnica = null;

    public EmpresaController() {
        //Inicializacion de DAO
        this.organismoDAO = new EmpresaJpaController(Conexion.getEmf());

        //Singleton Empresa
        this.organismoInstanciaUnica = LoginController.getInstanceEmpresa();
    }

    public static Empresa getOrganismoInstanciaUnica() {
        return organismoInstanciaUnica;
    }
    
   

}
