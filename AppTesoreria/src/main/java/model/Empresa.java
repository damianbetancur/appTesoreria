/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Ariel
 */
@Entity
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "unaEmpresaE")
    private List<Empleado> empleados;

    @OneToMany(mappedBy = "unaEmpresaC")
    private List<Cuenta> cuentas;

    @OneToMany(mappedBy = "unaEmpresaTM")
    private List<TipoMovimiento> tiposDeMovimientos;
    
    @OneToMany(mappedBy = "unaEmpresaCC")
    private List<Concepto> conceptos;

    public Empresa() {
        this.empleados = new ArrayList<>();
        this.cuentas = new ArrayList<>();
        this.tiposDeMovimientos = new ArrayList<>();
        this.conceptos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Empresa[ id=" + id + " ]";
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<TipoMovimiento> getTiposDeMovimientos() {
        return tiposDeMovimientos;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void setTiposDeMovimientos(List<TipoMovimiento> tiposDeMovimientos) {
        this.tiposDeMovimientos = tiposDeMovimientos;
    }

    public Cuenta buscarCuenta(Cuenta unaCuenta) {
        Cuenta cuentaEncontrada = null;

        for (Cuenta cuentaRecorrido : cuentas) {
            if (unaCuenta.getId() == cuentaRecorrido.getId()) {
                cuentaEncontrada = cuentaRecorrido;
            }
        }
        return cuentaEncontrada;
    }

    public List<Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    
}
