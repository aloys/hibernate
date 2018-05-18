package demo.hibernate.lab.inheritace.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by amazimpaka on 2018-05-17
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter @Setter
    private long id;

}
