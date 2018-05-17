package demo.hibernate.lab.test;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Created by amazimpaka on 2018-01-02
 */
public class HibernateRunner extends BlockJUnit4ClassRunner {


    public HibernateRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }


    @Override public void run(RunNotifier notifier){
        notifier.addListener(new HibernateExecutionListener());
        notifier.fireTestRunStarted(getDescription());
        super.run(notifier);
    }
}
