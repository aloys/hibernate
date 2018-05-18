package demo.hibernate.lab.association.one2one.polymorphism;

import demo.hibernate.lab.test.HibernateRunner;
import demo.hibernate.lab.test.HibernateSession;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;


@RunWith(HibernateRunner.class)
public class PolymorphicOne2OneAssociationTest {

    //  http://webdev.jhuep.com/~jcs/ejava-javaee/coursedocs/content/html/jpa-relationex-one2one.html
    @BeforeClass
    public static void initialize(){
        HibernateSession.registerEntity(Person.class);
        HibernateSession.registerEntity(Passport.class);
        HibernateSession.registerEntity(DrivingLicence.class);
        //HibernateSession.registerEntity(Document.class);
    }



    @Test
    public void save() {
        final Session session = HibernateSession.getCurrentSession();

        //Case 1: Create  a person with passport
        final Passport passport = new Passport();
        passport.setDocumentNo(UUID.randomUUID().toString());

        final Person person01 = new Person();
        person01.setName("Has Passport");
        person01.setDocument(passport);

        session.save(passport);
        session.save(person01);


        //Case 2: Create  a person with Driving Licence
        final DrivingLicence licence = new DrivingLicence();
        licence.setDocumentNo(UUID.randomUUID().toString());

        final Person person02 = new Person();
        person02.setName("Has DrivingLicence");
        person02.setDocument(licence);

        session.save(licence);
        session.save(person02);


        HibernateSession.commit();

        final Person loadePerson01 = HibernateSession.getCurrentSession().load(Person.class, person01.getId());
        Assert.assertEquals(Passport.class.getSimpleName(),loadePerson01.getDocument().getType());

        final Person loadePerson02 = HibernateSession.getCurrentSession().load(Person.class, person02.getId());
        Assert.assertEquals(DrivingLicence.class.getSimpleName(),loadePerson02.getDocument().getType());

    }


}
