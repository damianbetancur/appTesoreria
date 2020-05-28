package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empresa;
import model.RegistroDeMovimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-28T05:16:34")
@StaticMetamodel(Cuenta.class)
public class Cuenta_ { 

    public static volatile SingularAttribute<Cuenta, String> descripcion;
    public static volatile SingularAttribute<Cuenta, String> numeroDeCuenta;
    public static volatile ListAttribute<Cuenta, RegistroDeMovimiento> registros;
    public static volatile SingularAttribute<Cuenta, Long> id;
    public static volatile SingularAttribute<Cuenta, Float> saldoTotal;
    public static volatile SingularAttribute<Cuenta, Empresa> unaEmpresaC;

}