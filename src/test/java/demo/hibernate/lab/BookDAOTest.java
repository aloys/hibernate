package demo.hibernate.lab;

import demo.hibernate.lab.test.DatabaseQuery;
import demo.hibernate.lab.test.HibernateExecutionListener;
import demo.hibernate.lab.test.HibernateRunner;
import demo.hibernate.lab.test.HibernateSession;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(HibernateRunner.class)
public class BookDAOTest {


    @Test
    public void bootstartap() {
        final Session session = HibernateSession.getCurrentSession();
        session.save(new Book());
    }

    @After
    public void checkValues(){

    }



}
