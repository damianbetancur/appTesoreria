/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author USER
 */
@Entity
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "fk_organismo", nullable = false, updatable = true)
    private Organismo unOrganismoA;

    @OneToMany(mappedBy = "unAreaA")
    private List<Empleado> empleados;

    @OneToMany(mappedBy = "unAreaB")
    private List<Turno> turnos;

    @OneToMany(mappedBy = "unAreaC")
    private List<Tramite> tramites;

    @JoinTable(
            name = "rel_Tipo_Tramite_Area",
            joinColumns = @JoinColumn(name = "FK_Area"),
            inverseJoinColumns = @JoinColumn(name = "FK_Tipo_Tramite")
    )
    @ManyToMany
    private List<TipoTramite> tipoTramite;

    public Area() {
        this.empleados = new ArrayList<>();
        this.turnos = new ArrayList<>();
        this.tramites = new ArrayList<>();
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
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Organismo getUnOrganismoA() {
        return unOrganismoA;
    }

    public void setUnOrganismoA(Organismo unOrganismoA) {
        this.unOrganismoA = unOrganismoA;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }

    public List<TipoTramite> getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(List<TipoTramite> tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public List<Turno> buscarTurnosPorFecha(Date unaFecha) {
        List<Turno> turnosEncontrados = new ArrayList<>();
        for (Turno turnoRecorrido : this.turnos) {
            if (compararFecha(turnoRecorrido.getFecha(), unaFecha)) {
                turnosEncontrados.add(turnoRecorrido);
            }
        }
        return turnosEncontrados;
    }

    public List<Turno> buscarTurnosDelEmpleado(Empleado unEmpleado) {
        List<Turno> turnosEncontrados = new ArrayList<>();
        for (Turno turnoRecorrido : this.turnos) {
            if (turnoRecorrido.getUnEmpleado().getId().equals(unEmpleado.getId())) {
                turnosEncontrados.add(turnoRecorrido);
            }
        }
        return turnosEncontrados;
    }

    public List<Turno> buscarTurnosDelEmpleadoPorFecha(Empleado unEmpleado, Date unaFecha) {
        List<Turno> turnosEncontrados = new ArrayList<>();
        for (Turno turnoRecorrido : buscarTurnosDelEmpleado(unEmpleado)) {
            if (compararFecha(turnoRecorrido.getFecha(), unaFecha)) {
                turnosEncontrados.add(turnoRecorrido);
            }
        }
        return turnosEncontrados;
    }
    
    public List<Turno> priorizarTurno(List<Turno> turnosDesorganizados) {
        List<Turno> turnosEncontrados = new ArrayList<>();
        
        //1 Ordenar por fecha 
        for (Turno turnosDesorganizado : turnosDesorganizados) {
            
        }
        return turnosEncontrados;
    } 

    private boolean compararFecha(Date fechaA, Date fechaB) {
        boolean comparacion = false;

        Calendar calendario = Calendar.getInstance(); // fecha actual
        calendario.setTime(fechaA); // fecha de turno
        int aniofechaA = calendario.get(Calendar.YEAR);
        int mesfechaA = calendario.get(Calendar.MONTH);
        int diafechaA = calendario.get(Calendar.DAY_OF_MONTH) + 1;

        calendario.setTime(fechaB); // fecha de turno
        int aniofechaB = calendario.get(Calendar.YEAR);
        int mesfechaB = calendario.get(Calendar.MONTH);
        int diafechaB = calendario.get(Calendar.DAY_OF_MONTH);

        if (aniofechaA == aniofechaB) {
            if (mesfechaA == mesfechaB) {
                if (diafechaA == diafechaB) {
                    comparacion = true;
                }
            }
        }
        return comparacion;
    }

}
