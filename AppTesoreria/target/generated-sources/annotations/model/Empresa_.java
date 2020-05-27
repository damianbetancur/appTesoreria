package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cuenta;
import model.Empleado;
import model.TipoMovimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-26T19:32:23")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile ListAttribute<Empresa, TipoMovimiento> tiposDeMovimientos;
    public static volatile SingularAttribute<Empresa, String> razonSocial;
    public static volatile SingularAttribute<Empresa, String> direccion;
    public static volatile ListAttribute<Empresa, Cuenta> cuentas;
    public static volatile ListAttribute<Empresa, Empleado> empleados;
    public static volatile SingularAttribute<Empresa, Long> id;
    public static volatile SingularAttribute<Empresa, String> telefono;

}