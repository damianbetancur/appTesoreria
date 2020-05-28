/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.CuentaJpaController;
import dao.RegistroDeMovimientoJpaController;
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
    private final RegistroDeMovimientoJpaController registroDeMovimientoDAO;
    //Model 
    private Cuenta cuentaSeleccionada;
    private Date fechaSeleccionada;

    private RegistroDeMovimiento registroSeleccionado;

    private Empleado empleadoLoEmpleado;

    public ProcesarMovimientosDeCuentaDeEmpresa() {
        validador = new ValidadorDeCampos();
        empleadoLoEmpleado = LoginController.getInstanceUsuario().getUnEmpleado();
        registroDeMovimientoDAO = new RegistroDeMovimientoJpaController(Conexion.getEmf());
        registroSeleccionado = new RegistroDeMovimiento();
    }

    public List<Cuenta> buscarTodasLasCuentasDeEmpresa() {
        List<Cuenta> cuentasEncontradas = new ArrayList<>();
        cuentasEncontradas = LoginController.getInstanceEmpresa().getCuentas();
        return cuentasEncontradas;
    }

    public boolean verificarExistenciaDeRegistro(Cuenta unaCuenta, Date unaFecha) {
        boolean registroDeMovimientoVerificado = false;
        if (LoginController.getInstanceEmpresa().buscarCuenta(unaCuenta) != null) {
            for (RegistroDeMovimiento registroRecorrido : registroDeMovimientoDAO.findRegistroDeMovimientoEntities()) {
                if (registroRecorrido.getUnaCuenta().equals(unaCuenta)) {
                    if (validador.comprarFecha(registroRecorrido.getFecha(), unaFecha)) {
                        registroDeMovimientoVerificado = true;
                    } 
                } 

            }
        }       
        return registroDeMovimientoVerificado;
    }
    
    public RegistroDeMovimiento buscarRegistroDeMovimiento(Cuenta unaCuenta, Date unaFecha){
        RegistroDeMovimiento registroDeMovimientoAuxiliar = null;
        for (RegistroDeMovimiento registroRecorrido : registroDeMovimientoDAO.findRegistroDeMovimientoEntities()) {
                if (registroRecorrido.getUnaCuenta().equals(unaCuenta)) {
                    if (validador.comprarFecha(registroRecorrido.getFecha(), unaFecha)) {
                        registroDeMovimientoAuxiliar = registroRecorrido;
                    } 
                } 

            }
        return registroDeMovimientoAuxiliar;
    }

    public List<Concepto> buscarTodosLosConceptos() {
        List<Concepto> conceptosEncontrados = new ArrayList<>();
        conceptosEncontrados = LoginController.getInstanceEmpresa().getConceptos();
        return conceptosEncontrados;
    }

    public void agregarNuevoRegistro(LineaDeMovimiento nlm) {
        this.registroSeleccionado.getLineasDeRegistroDeMovimiento().add(nlm);
        actualizarSaldo();
    }

    public List<LineaDeMovimiento> buscarTodasLasLineasDeMovimientoDeUnRegistro() {
        List<LineaDeMovimiento> lineasDeMovimientoEncontradas = new ArrayList<>();

        for (LineaDeMovimiento lineaDeMovimientoRecorrido : this.registroSeleccionado.getLineasDeRegistroDeMovimiento()) {
            lineasDeMovimientoEncontradas.add(lineaDeMovimientoRecorrido);
        }

        return lineasDeMovimientoEncontradas;

    }

    public void actualizarSaldo() {
        float saldoAuxiliar = 0;
        for (LineaDeMovimiento lineaDeMovimientoRecorrido : this.registroSeleccionado.getLineasDeRegistroDeMovimiento()) {

            if (lineaDeMovimientoRecorrido.getUnConcepto().getUnTipoMovimiento().getDescripcion().equals("Credito")) {
                saldoAuxiliar = saldoAuxiliar + lineaDeMovimientoRecorrido.getMonto();
            }
            if (lineaDeMovimientoRecorrido.getUnConcepto().getUnTipoMovimiento().getDescripcion().equals("Debito")) {
                saldoAuxiliar = saldoAuxiliar - lineaDeMovimientoRecorrido.getMonto();
            }

        }
        this.registroSeleccionado.setSaldo(saldoAuxiliar);
    }

    public void crearNuevoRegistroDeMovimiento(Cuenta unaCuenta, Date unaFecha) {
        this.registroSeleccionado.setFecha(unaFecha);
        this.registroSeleccionado.setUnaCuenta(unaCuenta);
        this.registroSeleccionado.setSaldo(0);
    }

    public RegistroDeMovimiento getRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void setRegistroSeleccionado(RegistroDeMovimiento registroSeleccionado) {
        this.registroSeleccionado = registroSeleccionado;
    }

}
