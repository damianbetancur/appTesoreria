/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import dao.Conexion;
import view.JFrameLogin;

/**
 *
 * @author USER
 */
public class Main {
    public static void main(String[] args) {   
        new Conexion();
        JFrameLogin vista = new JFrameLogin();
        vista.arranca();
    }
}
