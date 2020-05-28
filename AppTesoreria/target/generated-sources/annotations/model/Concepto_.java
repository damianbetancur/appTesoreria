package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empresa;
import model.TipoMovimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-28T17:07:19")
@StaticMetamodel(Concepto.class)
public class Concepto_ { 

    public static volatile SingularAttribute<Concepto, String> descripcion;
    public static volatile SingularAttribute<Concepto, Empresa> unaEmpresaCC;
    public static volatile SingularAttribute<Concepto, TipoMovimiento> unTipoMovimiento;
    public static volatile SingularAttribute<Concepto, Long> id;

}