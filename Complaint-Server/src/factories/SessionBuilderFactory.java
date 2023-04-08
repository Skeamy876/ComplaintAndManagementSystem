package factories;

import models.*;
import models.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionBuilderFactory {
    private static SessionFactory sessionFactory= null;


    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration()
                    .configure("factories/hibernate.cfg.xml")
                    .addAnnotatedClass(StudentEntity.class)
                    .addAnnotatedClass(SupervisorEntity.class)
                    .addAnnotatedClass(AdvisorEntity.class)
                    .addAnnotatedClass(QueryEntity.class)
                    .addAnnotatedClass(ComplaintEntity.class)
                    .addAnnotatedClass(ResponseEntity.class)
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
