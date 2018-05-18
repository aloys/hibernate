package demo.hibernate.lab.association.one2one.polymorphism;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

/**
 * Created by amazimpaka on 2018-05-17
 */

@Entity
public class Person{

    @Id
    @GeneratedValue
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    @Any( metaColumn = @Column( name="type" ) )
    @AnyMetaDef(
            idType = "long",
            metaType = "string",
            metaValues = {
            @MetaValue( value="Passport", targetEntity=Passport.class ),
            @MetaValue( value="DrivingLicence", targetEntity=DrivingLicence.class )
    }
    )
    //@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Document document;

}