package demo.hibernate.lab;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Created by amazimpaka on 2018-01-02
 */
public class HibernateRunner extends BlockJUnit4ClassRunner {

    public static SessionFactory sessionFactory;

    @BeforeClass
    public static final void initialize() {
        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        final Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Book.class)
                .buildMetadata();


        sessionFactory = metadata.getSessionFactoryBuilder().build();

        Assert.assertNotNull(sessionFactory);
    }




    public HibernateRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }


    @Override public void run(RunNotifier notifier){
        notifier.addListener(new HibernateExecutionListener());
        notifier.fireTestRunStarted(getDescription());
        super.run(notifier);
    }
}
