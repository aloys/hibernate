package demo.hibernate.lab;

import demo.hibernate.lab.test.HibernateExecutionListener;
import demo.hibernate.lab.test.HibernateRunner;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(HibernateRunner.class)
public class BookDAOTest {


    @Test
    public void bootstartap() {
        final Session session = HibernateExecutionListener.sessionFactory.getCurrentSession();
        session.save(new Book());
    }



}
