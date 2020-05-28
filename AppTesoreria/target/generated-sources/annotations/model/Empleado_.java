package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empresa;
import model.TipoEmpleado;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-28T19:23:40")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, String> apellido;
    public static volatile SingularAttribute<Empleado, Long> id;
    public static volatile SingularAttribute<Empleado, Empresa> unaEmpresaE;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile SingularAttribute<Empleado, String> dni;
    public static volatile SingularAttribute<Empleado, TipoEmpleado> unTipoEmpleado;

}