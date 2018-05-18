package demo.hibernate.lab.test;

import demo.hibernate.lab.association.one2one.polymorphism.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by amazimpaka on 2018-05-16
 */
public final class HibernateSession {

    private static final Logger logger = LoggerFactory.getLogger(HibernateSession.class);

    private static SessionFactory sessionFactory;

    private static final Set<Class<?>> ENTITY_CLASSES = new LinkedHashSet<>();

    public static void registerEntity(Class<?> entityClass){
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity class: "+entityClass+" misses annotation @Entity");
        }
        ENTITY_CLASSES.add(entityClass);
    }

    public static void clearEntityRegitry(){
        ENTITY_CLASSES.clear();
    }

    public static final SessionFactory getSessionFactory() {

        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        final MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        ENTITY_CLASSES.forEach( entityClass -> {
            metadataSources.addAnnotatedClass(entityClass);
            logger.debug("Registered entity: {}",entityClass.getName());
        });



        final Metadata metadata = metadataSources.buildMetadata();


        PersistentClass persistentClass = metadata.getEntityBinding(Person.class.getName());
        Property property = persistentClass.getProperty("document");


        /*InFlightMetadataCollectorImpl inFlightMetadataCollector = new InFlightMetadataCollectorImpl(((MetadataImplementor) metadata).getMetadataBuildingOptions(), ((MetadataImplementor) metadata).getTypeResolver());

        OneToOne oneToOne = new OneToOne(inFlightMetadataCollector, persistentClass.getTable(), persistentClass);
        oneToOne.setPropertyName("document");
        oneToOne.setEntityName(Person.class.getName());
        oneToOne.setForeignKeyType(ForeignKeyDirection.TO_PARENT);
        oneToOne.setIdentifier(persistentClass.getIdentifier());
        oneToOne.setFetchMode(FetchMode.SELECT);
        //oneToOne.setReferencedPropertyName("person");
        oneToOne.setReferencedEntityName(Passport.class.getName());*/


        //property.setValue(oneToOne);

        //persistentClass.addProperty(property);

        if(sessionFactory == null){
            synchronized (HibernateExecutionListener.class){
                if(sessionFactory == null){
                    sessionFactory = metadata.getSessionFactoryBuilder()
                            .applyInterceptor(HibernateChangeTracker.INSTANCE)
                            .build();

                    DatabaseQuery.describe();
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
