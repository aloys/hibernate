package demo.hibernate.lab;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Created by amazimpaka on 2018-01-02
 */
public class HibernateExecutionListener  extends RunListener {


    public void testRunStarted(Description description) throws Exception {
        System.out.println("Number of tests to execute: " + description.testCount());

    }

    public void testRunFinished(Result result) throws Exception {
        System.out.println("Number of tests executed: " + result.getRunCount());
    }

    public void testStarted(Description description) throws Exception {
        System.out.println("Starting: " + description.getMethodName());

        final Session currentSession = HibernateRunner.sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
    }

    public void testFinished(Description description) throws Exception {
        System.out.println("Finished: " + description.getMethodName());

        commit();
    }



    public void testFailure(Failure failure) throws Exception {
        System.out.println("Failed: " + failure.getDescription().getMethodName());

        rollback();
    }

    public void testAssumptionFailure(Failure failure) {
        System.out.println("Failed: " + failure.getDescription().getMethodName());

        rollback();
    }

    public void testIgnored(Description description) throws Exception {
        System.out.println("Ignored: " + description.getMethodName());

        rollback();

    }

    private void commit() {
        final Session currentSession = HibernateRunner.sessionFactory.getCurrentSession();
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
        final Session currentSession = HibernateRunner.sessionFactory.getCurrentSession();
        if( currentSession.getTransaction().isActive()){
            currentSession.getTransaction().rollback();
        }
    }
}
