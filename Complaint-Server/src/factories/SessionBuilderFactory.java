package factories;

import models.Advisor;
import models.Student;
import models.Supervisor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionBuilderFactory {

    private static SessionFactory sessionFactory= null;


    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration()
                    .configure("factories/hibernate.cfg.xml")
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Supervisor.class)
                    .addAnnotatedClass(Advisor.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if (sessionFactory == null){
            sessionFactory.close();
        }
    }
}
