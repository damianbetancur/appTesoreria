package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Area;
import model.Empleado;
import model.Persona;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Organismo.class)
public class Organismo_ { 

    public static volatile SingularAttribute<Organismo, String> direccion;
    public static volatile ListAttribute<Organismo, Area> areas;
    public static volatile ListAttribute<Organismo, Empleado> empleados;
    public static volatile SingularAttribute<Organismo, Long> id;
    public static volatile SingularAttribute<Organismo, String> telefono;
    public static volatile SingularAttribute<Organismo, String> nombre;
    public static volatile ListAttribute<Organismo, Persona> personas;

}