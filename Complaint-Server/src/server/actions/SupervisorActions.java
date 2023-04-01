package server.actions;

import factories.SessionBuilderFactory;
import models.Advisor;
import models.Supervisor;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class SupervisorActions {
    public void createSupervisor(Supervisor supervisor){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(supervisor);
        transaction.commit();
        session.close();
    }

    public Supervisor findSupervisor(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Supervisor supervisor = (Supervisor) session.get(Supervisor.class, id);
        transaction.commit();
        session.close();

        return supervisor;
    }

    public void updateSupervisor(Supervisor supervisor){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Supervisor supervisorDTO = (Supervisor) session.get(Supervisor.class,supervisor.getIdNumber());
        supervisor.setFirstName(supervisor.getFirstName());
        supervisor.setLastName(supervisor.getLastName());
        supervisor.setEmail(supervisor.getEmail());
        session.update(supervisorDTO);
        transaction.commit();
        session.close();

    }

    public void deleteSupervisor(Supervisor supervisor){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Supervisor supervisorDTO = (Supervisor) session.get(Supervisor.class,supervisor.getIdNumber());
        session.delete(supervisorDTO);
        transaction.commit();
        session.close();
    }


}
