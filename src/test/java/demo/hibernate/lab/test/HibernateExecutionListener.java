package demo.hibernate.lab.test;

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


    public void testRunStarted(Description description)  {
        logger.debug("Number of tests to execute: {}", description.testCount());
    }

    public void testRunFinished(Result result)  {
        logger.debug("Number of tests executed: {} " , result.getRunCount());
    }

    public void testStarted(Description description)  {
        logger.debug("Starting test: {}", description.getMethodName());

        HibernateSession.beginTransaction();
    }


    public void testFinished(Description description)  {
        logger.debug("Finished test: {}" ,description.getMethodName());

        HibernateSession.commit();

        HibernateChangeTracker.INSTANCE.cleanUp();

        HibernateSession.clearEntityRegitry();
    }



    public void testFailure(Failure failure)  {
        logger.debug("Failed test: {}", failure.getDescription().getMethodName());

        HibernateSession.rollback();
    }

    public void testAssumptionFailure(Failure failure) {
        logger.debug("Failed test: {}", failure.getDescription().getMethodName());

        HibernateSession.rollback();
    }

    public void testIgnored(Description description)  {
        logger.debug("Ignored test: {} " + description.getMethodName());

        HibernateSession.rollback();

    }


}
