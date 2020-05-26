package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Area;
import model.RequisitoObligatorio;
import model.RequisitoOpcional;
import model.TipoTramite;
import model.Turno;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Tramite.class)
public class Tramite_ { 

    public static volatile SingularAttribute<Tramite, Date> fecha;
    public static volatile SingularAttribute<Tramite, TipoTramite> unTipoTramite;
    public static volatile ListAttribute<Tramite, RequisitoOpcional> requisitoOpcional;
    public static volatile SingularAttribute<Tramite, Date> hora;
    public static volatile SingularAttribute<Tramite, Area> unAreaC;
    public static volatile SingularAttribute<Tramite, Long> id;
    public static volatile ListAttribute<Tramite, RequisitoObligatorio> requisitoObligatorio;
    public static volatile SingularAttribute<Tramite, Turno> unTurno;

}