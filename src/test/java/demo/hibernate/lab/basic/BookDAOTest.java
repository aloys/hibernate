package demo.hibernate.lab.basic;

import demo.hibernate.lab.test.HibernateRunner;
import demo.hibernate.lab.test.HibernateSession;
import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(HibernateRunner.class)
public class BookDAOTest {

    @BeforeClass
    public static void initialize(){
        HibernateSession.registerEntity(Book.class);
    }

    @Test
    public void save() {
        final Session session = HibernateSession.getCurrentSession();
        Book book = new Book();
        book.setTitle("Demo");
        session.save(book);
    }


}
