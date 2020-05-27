/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Ariel
 */
@Entity
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numeroDeCuenta;

    @OneToMany(mappedBy = "unaCuenta")
    private List<RegistroDeMovimiento> registros;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa unaEmpresaC;

    public Cuenta() {
        this.registros = new ArrayList<>();
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
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cuenta[ id=" + id + " ]";
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public Empresa getUnaEmpresaC() {
        return unaEmpresaC;
    }

    public void setUnaEmpresaC(Empresa unaEmpresaC) {
        this.unaEmpresaC = unaEmpresaC;
    }

    public List<RegistroDeMovimiento> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroDeMovimiento> registros) {
        this.registros = registros;
    }

    
}
