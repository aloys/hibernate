package demo.hibernate.lab;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(HibernateRunner.class)
public class BookDAOTest {


    @Test
    public void bootstartap() {
        final Session session = HibernateRunner.sessionFactory.getCurrentSession();
        session.save(null);
    }



}
