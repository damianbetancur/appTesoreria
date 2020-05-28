/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.CuentaJpaController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Concepto;
import model.Cuenta;
import model.Empleado;
import model.LineaDeMovimiento;
import model.RegistroDeMovimiento;
import view.ValidadorDeCampos;

/**
 *
 * @author Ariel
 */
public class ProcesarMovimientosDeCuentaDeEmpresa {

    private final ValidadorDeCampos validador;
    //DAO
    //Model 
    private static Cuenta cuentaSeleccionada;
    private Date fechaSeleccionada;

    private RegistroDeMovimiento registroSeleccionado;

    private final Empleado empleadoLoEmpleado;

    public ProcesarMovimientosDeCuentaDeEmpresa() {
        validador = new ValidadorDeCampos();
        empleadoLoEmpleado = LoginController.getInstanceUsuario().getUnEmpleado();
    }

    public List<Cuenta> buscarTodasLasCuentasDeEmpresa() {
        List<Cuenta> cuentasEncontradas = new ArrayList<>();
        cuentasEncontradas = LoginController.getInstanceEmpresa().getCuentas();
        return cuentasEncontradas;
    }

    public boolean verificarExistenciaDeRegistro(Cuenta unaCuenta, Date unaFecha) {
        boolean registroDeMovimientoVerificado = false;

        RegistroDeMovimiento registroEncontrado = null;
        System.out.println("fehca param: " + unaFecha);
        if (LoginController.getInstanceEmpresa().buscarCuenta(unaCuenta) != null) {
            for (RegistroDeMovimiento registroRecorrido : LoginController.getInstanceEmpresa().buscarCuenta(unaCuenta).getRegistros()) {
                if (this.validador.comprarFecha(registroRecorrido.getFecha(), unaFecha)) {
                    registroDeMovimientoVerificado = true;
                    this.registroSeleccionado = registroRecorrido;
                } else {
                    crearNuevoRegistroDeMovimiento(unaCuenta, unaFecha);
                    System.out.println("No Existe Registro para la fecha");
                }
            }
        }
        return registroDeMovimientoVerificado;
    }

    public List<Concepto> buscarTodosLosConceptos() {
        List<Concepto> conceptosEncontrados = new ArrayList<>();
        conceptosEncontrados = LoginController.getInstanceEmpresa().getConceptos();
        return conceptosEncontrados;
    }
    
    
    public void agregarNuevoRegistro(LineaDeMovimiento nlm){
        this.registroSeleccionado.getLineasDeRegistroDeMovimiento().add(nlm);
    
    }
    
    public List<LineaDeMovimiento> buscarTodasLasLineasDeMovimientoDeUnRegistro(RegistroDeMovimiento unRegistro){
        List<LineaDeMovimiento> lineasDeMovimientoEncontradas = new ArrayList<>();
        
        for (LineaDeMovimiento lineaDeMovimientoRecorrido : unRegistro.getLineasDeRegistroDeMovimiento()) {
            lineasDeMovimientoEncontradas.add(lineaDeMovimientoRecorrido);
        }
        
        return lineasDeMovimientoEncontradas;
    
    }
    
    
    public void crearNuevoRegistroDeMovimiento(Cuenta unaCuenta, Date unaFecha) {
        this.registroSeleccionado = new RegistroDeMovimiento();
        this.registroSeleccionado.setFecha(unaFecha);
        this.registroSeleccionado.setUnaCuenta(unaCuenta);
        this.registroSeleccionado.setSaldo(0);
    }

    public RegistroDeMovimiento getRegistroSeleccionado() {
        return registroSeleccionado;
    }    
    
}
