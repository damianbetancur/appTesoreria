package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empresa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-27T06:12:14")
@StaticMetamodel(TipoMovimiento.class)
public class TipoMovimiento_ { 

    public static volatile SingularAttribute<TipoMovimiento, String> descripcion;
    public static volatile SingularAttribute<TipoMovimiento, Empresa> unaEmpresaTM;
    public static volatile SingularAttribute<TipoMovimiento, Long> id;

}