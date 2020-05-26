package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Organismo;
import model.TipoPersona;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-22T23:15:29")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, TipoPersona> unTipoPersona;
    public static volatile SingularAttribute<Persona, String> apellido;
    public static volatile SingularAttribute<Persona, Long> id;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, String> dni;
    public static volatile SingularAttribute<Persona, Organismo> unOrganismoB;

}