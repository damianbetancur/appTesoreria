/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
public class Turno implements Serializable, Comparable<Turno> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_area", nullable = false, updatable = true)
    private Area unAreaB;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "fk_hora_turno")
    private HorarioAtencionTurno unaHoraTurno;

    @ManyToOne
    @JoinColumn(name = "fk_persona")
    private Persona unaPersona;

    @ManyToOne
    @JoinColumn(name = "fk_empleado")
    private Empleado unEmpleado;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_atencion")
    private TipoAtencion unTipoAtencion;

    @ManyToOne
    @JoinColumn(name = "fk_estado_turno")
    private EstadoTurno unEstadoTurno;

    @ManyToOne
    @JoinColumn(name = "fk_Tipo_Tramite")
    private TipoTramite unTipoTramite;

    public Turno() {
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
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Turno[ id=" + id + " ]";
    }

    public Area getUnAreaB() {
        return unAreaB;
    }

    public void setUnAreaB(Area unAreaB) {
        this.unAreaB = unAreaB;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Persona getUnaPersona() {
        return unaPersona;
    }

    public void setUnaPersona(Persona unaPersona) {
        this.unaPersona = unaPersona;
    }

    public Empleado getUnEmpleado() {
        return unEmpleado;
    }

    public void setUnEmpleado(Empleado unEmpleado) {
        this.unEmpleado = unEmpleado;
    }

    public TipoAtencion getUnTipoAtencion() {
        return unTipoAtencion;
    }

    public void setUnTipoAtencion(TipoAtencion unTipoAtencion) {
        this.unTipoAtencion = unTipoAtencion;
    }

    public EstadoTurno getUnEstadoTurno() {
        return unEstadoTurno;
    }

    public void setUnEstadoTurno(EstadoTurno unEstadoTurno) {
        this.unEstadoTurno = unEstadoTurno;
    }

    public TipoTramite getUnTipoTramite() {
        return unTipoTramite;
    }

    public void setUnTipoTramite(TipoTramite unTipoTramite) {
        this.unTipoTramite = unTipoTramite;
    }

    public HorarioAtencionTurno getUnaHoraTurno() {
        return unaHoraTurno;
    }

    public void setUnaHoraTurno(HorarioAtencionTurno unaHoraTurno) {
        this.unaHoraTurno = unaHoraTurno;
    }

    @Override
    public int compareTo(Turno unTurno) {
        int resultado = 0;
        //1-Se busca por area
        if (this.unAreaB.getId() < unTurno.getUnAreaB().getId()) {
            //2- Se busca por fecha
            if (this.fecha.before(unTurno.getFecha())) {
                //3-Se busca por Empleado
                if (this.unEmpleado.getId() < unTurno.getUnEmpleado().getId()) {
                    //4-Se ordena por estado
                    if (this.unEstadoTurno.getId() < unTurno.getUnEstadoTurno().getId()) {
                        //5-Se ordena por Tipo de Atencion
                        if (this.unTipoAtencion.getId() < unTurno.getUnTipoAtencion().getId()) {
                            resultado = 1;
                        }else{
                            resultado = -1;
                        }
                    }else{
                        resultado = -1;
                    }
                }
            }else{
                resultado = -1;
            }
        }else{
            resultado = -1;
        }
        
        return resultado;
    }

    //
}
