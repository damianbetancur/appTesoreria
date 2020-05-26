package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Area;
import model.Organismo;
import model.TipoEmpleado;
import model.TipoTramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, String> apellido;
    public static volatile SingularAttribute<Empleado, Long> id;
    public static volatile SingularAttribute<Empleado, Area> unAreaA;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile ListAttribute<Empleado, TipoTramite> tipoTramite;
    public static volatile SingularAttribute<Empleado, String> dni;
    public static volatile SingularAttribute<Empleado, Organismo> unOrganismoC;
    public static volatile SingularAttribute<Empleado, TipoEmpleado> unTipoEmpleado;

}