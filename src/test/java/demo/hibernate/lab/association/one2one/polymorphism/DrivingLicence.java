package demo.hibernate.lab.association.one2one.polymorphism;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by amazimpaka on 2018-05-17
 */

@Entity
public class DrivingLicence extends Document{


    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public DrivingLicence() {
        super(DrivingLicence.class.getSimpleName());
    }
}