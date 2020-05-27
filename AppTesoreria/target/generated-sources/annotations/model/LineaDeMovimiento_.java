package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Concepto;
import model.Empleado;
import model.RegistroDeMovimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-26T19:32:23")
@StaticMetamodel(LineaDeMovimiento.class)
public class LineaDeMovimiento_ { 

    public static volatile SingularAttribute<LineaDeMovimiento, Concepto> unConcepto;
    public static volatile SingularAttribute<LineaDeMovimiento, RegistroDeMovimiento> unRegistro;
    public static volatile SingularAttribute<LineaDeMovimiento, Float> monto;
    public static volatile SingularAttribute<LineaDeMovimiento, Empleado> unEmpleado;
    public static volatile SingularAttribute<LineaDeMovimiento, Long> id;

}