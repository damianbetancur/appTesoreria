package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Area;
import model.Empleado;
import model.EstadoTurno;
import model.HorarioAtencionTurno;
import model.Persona;
import model.TipoAtencion;
import model.TipoTramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Turno.class)
public class Turno_ { 

    public static volatile SingularAttribute<Turno, Date> fecha;
    public static volatile SingularAttribute<Turno, TipoTramite> unTipoTramite;
    public static volatile SingularAttribute<Turno, Empleado> unEmpleado;
    public static volatile SingularAttribute<Turno, Persona> unaPersona;
    public static volatile SingularAttribute<Turno, HorarioAtencionTurno> unaHoraTurno;
    public static volatile SingularAttribute<Turno, TipoAtencion> unTipoAtencion;
    public static volatile SingularAttribute<Turno, Long> id;
    public static volatile SingularAttribute<Turno, Area> unAreaB;
    public static volatile SingularAttribute<Turno, EstadoTurno> unEstadoTurno;

}