package factories;

import models.*;
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
                    .addAnnotatedClass(Query.class)
                    .addAnnotatedClass(Complaint.class)
                    .addAnnotatedClass(Category.class)
                    .addAnnotatedClass(Response.class)
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
