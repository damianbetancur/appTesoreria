/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.OrganismoJpaController;
import model.Organismo;

/**
 *
 * @author Ariel
 */
public class OrganismoController {

    //DAO
    private final OrganismoJpaController organismoDAO;

    //Model
    private static Organismo organismoInstanciaUnica = null;

    public OrganismoController() {
        //Inicializacion de DAO
        this.organismoDAO = new OrganismoJpaController(Conexion.getEmf());

        //Singleton Organismo
        this.organismoInstanciaUnica = LoginController.getInstanceOrganismo();
    }

    public static Organismo getOrganismoInstanciaUnica() {
        return organismoInstanciaUnica;
    }
    
   

}
