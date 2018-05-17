package demo.hibernate.lab.test;

import demo.hibernate.lab.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amazimpaka on 2018-01-02
 */
public class HibernateExecutionListener  extends RunListener {

    private static final Logger logger = LoggerFactory.getLogger(HibernateExecutionListener.class);


    public static SessionFactory sessionFactory;


    public static final void initialize() {
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
    }



    public void testRunStarted(Description description)  {
        logger.debug("Number of tests to execute: {}", description.testCount());


        initialize();

    }

    public void testRunFinished(Result result)  {
        logger.debug("Number of tests executed: {} " , result.getRunCount());
    }

    public void testStarted(Description description)  {
        logger.debug("Starting test: {}", description.getMethodName());

        final Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
    }

    public void testFinished(Description description)  {
        logger.debug("Finished test: {}" ,description.getMethodName());

        commit();
    }



    public void testFailure(Failure failure)  {
        logger.debug("Failed test: {}", failure.getDescription().getMethodName());

        rollback();
    }

    public void testAssumptionFailure(Failure failure) {
        logger.debug("Failed test: {}", failure.getDescription().getMethodName());

        rollback();
    }

    public void testIgnored(Description description)  {
        logger.debug("Ignored test: {} " + description.getMethodName());

        rollback();

    }

    private void commit() {
        final Session currentSession = sessionFactory.getCurrentSession();
        if( currentSession.getTransaction().isActive()){
           try{
               currentSession.getTransaction().commit();
           }catch (RuntimeException e){
               currentSession.getTransaction().rollback();
               throw e;
           }
        }

    }

    private void rollback() {
        final Session currentSession = sessionFactory.getCurrentSession();
        if( currentSession.getTransaction().isActive()){
            currentSession.getTransaction().rollback();
        }
    }
}
