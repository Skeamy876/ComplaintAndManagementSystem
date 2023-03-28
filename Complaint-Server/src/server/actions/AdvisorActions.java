package server.actions;

import factories.SessionBuilderFactory;
import models.Advisor;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class AdvisorActions {
    public void createAdvisor(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();


        Transaction transaction = session.beginTransaction();
        session.save(this);
        transaction.commit();
        session.close();
    }

    public void updateAdvisor(Advisor advisor){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Advisor advisorDTO = (Advisor) session.get(Advisor.class,advisor.getIdNumber());
        advisor.setFirstName(advisor.getFirstName());
        advisor.setLastName(advisor.getLastName());
        advisor.setEmail(advisor.getEmail());
        session.update(advisorDTO);
        transaction.commit();
        session.close();

    }

    public void deleteAdvisor(Advisor advisor){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Advisor advisorDTO = (Advisor) session.get(Advisor.class,advisor.getIdNumber());
        session.delete(advisorDTO);
        transaction.commit();
        session.close();
    }
}
