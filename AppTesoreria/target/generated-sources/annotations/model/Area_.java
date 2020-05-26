package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empleado;
import model.Organismo;
import model.TipoTramite;
import model.Tramite;
import model.Turno;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Area.class)
public class Area_ { 

    public static volatile SingularAttribute<Area, Integer> numero;
    public static volatile ListAttribute<Area, Tramite> tramites;
    public static volatile ListAttribute<Area, Turno> turnos;
    public static volatile ListAttribute<Area, Empleado> empleados;
    public static volatile SingularAttribute<Area, Long> id;
    public static volatile SingularAttribute<Area, String> nombre;
    public static volatile SingularAttribute<Area, Organismo> unOrganismoA;
    public static volatile ListAttribute<Area, TipoTramite> tipoTramite;

}