package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Area;
import model.Empleado;
import model.RequisitoObligatorio;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(TipoTramite.class)
public class TipoTramite_ { 

    public static volatile SingularAttribute<TipoTramite, String> codigo;
    public static volatile ListAttribute<TipoTramite, RequisitoObligatorio> requisitosObligatorios;
    public static volatile ListAttribute<TipoTramite, Area> areas;
    public static volatile ListAttribute<TipoTramite, Empleado> empleados;
    public static volatile SingularAttribute<TipoTramite, Long> id;
    public static volatile SingularAttribute<TipoTramite, String> nombre;

}