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
 * @author USER
 */
@Entity
public class Tramite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA")
    private Date fecha;

    @Temporal(TemporalType.TIME)
    @Column(name = "HORA")
    private Date hora;

    @ManyToOne
    @JoinColumn(name = "fk_area", nullable = false, updatable = true)
    private Area unAreaC;

    @ManyToOne
    @JoinColumn(name = "fk_Tipo_Tramite")
    private TipoTramite unTipoTramite;

    @ManyToOne
    @JoinColumn(name = "fk_Turno")
    private Turno unTurno;

    @OneToMany
    private List<RequisitoObligatorio> requisitoObligatorio;

    @OneToMany
    private List<RequisitoOpcional> requisitoOpcional;

    public Tramite() {
        this.requisitoObligatorio = new ArrayList<>();
        this.requisitoOpcional = new ArrayList<>();
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
        if (!(object instanceof Tramite)) {
            return false;
        }
        Tramite other = (Tramite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tramite[ id=" + id + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Area getUnAreaC() {
        return unAreaC;
    }

    public void setUnAreaC(Area unAreaC) {
        this.unAreaC = unAreaC;
    }

    public TipoTramite getUnTipoTramite() {
        return unTipoTramite;
    }

    public void setUnTipoTramite(TipoTramite unTipoTramite) {
        this.unTipoTramite = unTipoTramite;
    }

    public Turno getUnTurno() {
        return unTurno;
    }

    public void setUnTurno(Turno unTurno) {
        this.unTurno = unTurno;
    }

    public List<RequisitoObligatorio> getRequisitoObligatorio() {
        return requisitoObligatorio;
    }

    public void setRequisitoObligatorio(List<RequisitoObligatorio> requisitoObligatorio) {
        this.requisitoObligatorio = requisitoObligatorio;
    }

    public List<RequisitoOpcional> getRequisitoOpcional() {
        return requisitoOpcional;
    }

    public void setRequisitoOpcional(List<RequisitoOpcional> requisitoOpcional) {
        this.requisitoOpcional = requisitoOpcional;
    }

    

}
