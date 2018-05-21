package demo.hibernate.lab.association.one2one.polymorphism;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by amazimpaka on 2018-05-17
 */

@ToString
@MappedSuperclass
public class Document {

    @Id
    @GeneratedValue
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String documentNo;

    @Getter
    private final String type;


    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Document(String type) {
        this.type = type;
    }
}