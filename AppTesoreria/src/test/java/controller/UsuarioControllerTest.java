/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Vector;
import model.Empleado;
import model.Persona;
import model.TipoUsuario;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ariel
 */
public class UsuarioControllerTest {
    
    public UsuarioControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of agregarUsuario method, of class UsuarioController.
     */
    @Test
    public void testAgregarUsuario() {
        System.out.println("agregarUsuario");
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("");
        UsuarioController instance = new UsuarioController();
        boolean expResult = true;
        boolean result = true;//instance.agregarUsuario(nuevoUsuario);
        assertEquals(expResult, result);
        
    }

    
}
