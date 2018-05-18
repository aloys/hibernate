package demo.hibernate.lab.inheritace.mappedsuperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by amazimpaka on 2018-05-17
 */
@Entity
public class ChildB extends Parent {

    @Getter @Setter
    @Column(name = "COLUMN_B")
    private String columnB;


}
