package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cuenta;
import model.LineaDeMovimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-26T19:32:23")
@StaticMetamodel(RegistroDeMovimiento.class)
public class RegistroDeMovimiento_ { 

    public static volatile SingularAttribute<RegistroDeMovimiento, Date> fecha;
    public static volatile SingularAttribute<RegistroDeMovimiento, Cuenta> unaCuenta;
    public static volatile SingularAttribute<RegistroDeMovimiento, Long> id;
    public static volatile SingularAttribute<RegistroDeMovimiento, Float> saldo;
    public static volatile ListAttribute<RegistroDeMovimiento, LineaDeMovimiento> lineasDeRegistroDeMovimiento;

}