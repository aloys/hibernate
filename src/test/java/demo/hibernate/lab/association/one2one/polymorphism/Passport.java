package demo.hibernate.lab.association.one2one.polymorphism;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by amazimpaka on 2018-05-17
 */

@Entity
public class Passport  extends Document{


    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Passport() {
        super(Passport.class.getSimpleName());
    }
}