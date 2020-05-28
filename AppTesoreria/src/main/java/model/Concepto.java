/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ariel
 */
@Entity
public class Concepto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "fk_TipoMovimiento")
    private TipoMovimiento unTipoMovimiento;
    
    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa unaEmpresaCC;

    public Concepto() {
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
        if (!(object instanceof Concepto)) {
            return false;
        }
        Concepto other = (Concepto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoMovimiento getUnTipoMovimiento() {
        return unTipoMovimiento;
    }

    public void setUnTipoMovimiento(TipoMovimiento unTipoMovimiento) {
        this.unTipoMovimiento = unTipoMovimiento;
    }

    public Empresa getUnaEmpresaCC() {
        return unaEmpresaCC;
    }

    public void setUnaEmpresaCC(Empresa unaEmpresaCC) {
        this.unaEmpresaCC = unaEmpresaCC;
    }

    
    
}
