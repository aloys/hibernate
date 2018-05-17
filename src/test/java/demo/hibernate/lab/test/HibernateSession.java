package demo.hibernate.lab.test;

import demo.hibernate.lab.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;

/**
 * Created by amazimpaka on 2018-05-16
 */
public final class HibernateSession {


    private static SessionFactory sessionFactory;

    public static final SessionFactory getSessionFactory() {
        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        final Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Book.class)
                .buildMetadata();


        if(sessionFactory == null){
            synchronized (HibernateExecutionListener.class){
                if(sessionFactory == null){
                    sessionFactory = metadata.getSessionFactoryBuilder().build();
                }
            }

        }

        Assert.assertNotNull(sessionFactory);

        return sessionFactory;
    }



    public static Session getCurrentSession(){
        return getCurrentSession(true);
    }


    public static  Session getCurrentSession(boolean startTransaction){
        Session currentSession = getSessionFactory().getCurrentSession();
        if(startTransaction){
            beginTransaction();
        }
        return currentSession;
    }

    public static void commit() {
        final Session currentSession = getSessionFactory().getCurrentSession();
        if( currentSession.getTransaction().isActive()){
            try{
                currentSession.getTransaction().commit();
            }catch (RuntimeException e){
                currentSession.getTransaction().rollback();
                throw e;
            }
        }

    }

    public static void rollback() {
        final Session currentSession = getSessionFactory().getCurrentSession();
        if( currentSession.getTransaction().isActive()){
            currentSession.getTransaction().rollback();
        }
    }

    public static Transaction beginTransaction() {
        final Session currentSession = getSessionFactory().getCurrentSession();
        if(currentSession.getTransaction() == null || !currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
        }
        return currentSession.getTransaction();
    }
}
