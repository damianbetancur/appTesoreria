/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ariel
 */
@Entity
public class RegistroDeMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    private Float saldo;

    @ManyToOne
    @JoinColumn(name = "fk_cuenta")
    private Cuenta unaCuenta;

    @OneToMany(mappedBy = "unRegistro")
    private List<LineaDeMovimiento> lineasDeRegistroDeMovimiento;

    public RegistroDeMovimiento() {
        this.lineasDeRegistroDeMovimiento = new ArrayList<>();
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
        if (!(object instanceof RegistroDeMovimiento)) {
            return false;
        }
        RegistroDeMovimiento other = (RegistroDeMovimiento) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "model.RegistroDeMovimiento[ id=" + id + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }
    
    public List<LineaDeMovimiento> getLineasDeRegistroDeMovimiento() {
        return lineasDeRegistroDeMovimiento;
    }

    public Cuenta getUnaCuenta() {
        return unaCuenta;
    }

    public void setUnaCuenta(Cuenta unaCuenta) {
        this.unaCuenta = unaCuenta;
    }

    public void setLineasDeRegistroDeMovimiento(List<LineaDeMovimiento> lineasDeRegistroDeMovimiento) {
        this.lineasDeRegistroDeMovimiento = lineasDeRegistroDeMovimiento;
    }

    
}
