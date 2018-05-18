package demo.hibernate.lab.inheritace.mappedsuperclass;

import demo.hibernate.lab.test.HibernateRunner;
import demo.hibernate.lab.test.HibernateSession;
import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(HibernateRunner.class)
public class MappedSuperclassInheritanceTest {

    @BeforeClass
    public static void initialize(){
        HibernateSession.registerEntity(ChildA.class);
        HibernateSession.registerEntity(ChildB.class);
    }



    @Test
    public void save() {
        final Session session = HibernateSession.getCurrentSession();

        ChildA a = new ChildA();
        a.setColumnA("Value 100");

        ChildB b = new ChildB();
        b.setColumnB("Value 200");

        session.save(a);
        session.save(b);
    }


}
