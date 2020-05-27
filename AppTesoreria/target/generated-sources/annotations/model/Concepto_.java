package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TipoMovimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-27T06:12:14")
@StaticMetamodel(Concepto.class)
public class Concepto_ { 

    public static volatile SingularAttribute<Concepto, String> descripcion;
    public static volatile SingularAttribute<Concepto, TipoMovimiento> unTipoMovimiento;
    public static volatile SingularAttribute<Concepto, Long> id;

}