package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empleado;
import model.Persona;
import model.TipoUsuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile SingularAttribute<Usuario, Empleado> unEmpleado;
    public static volatile SingularAttribute<Usuario, Persona> unaPersona;
    public static volatile SingularAttribute<Usuario, TipoUsuario> tipoUsuario;
    public static volatile SingularAttribute<Usuario, Long> id;
    public static volatile SingularAttribute<Usuario, String> nombre;

}