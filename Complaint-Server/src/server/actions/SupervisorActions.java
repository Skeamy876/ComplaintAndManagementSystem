package server.actions;

import factories.SessionBuilderFactory;
import models.hibernate.SupervisorEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class SupervisorActions {
    public void createSupervisor(SupervisorEntity supervisorEntity){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        session.save(supervisorEntity);
        transaction.commit();
        session.close();
    }

    public SupervisorEntity findSupervisor(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        SupervisorEntity supervisorEntity = (SupervisorEntity) session.get(SupervisorEntity.class, id);
        transaction.commit();
        session.close();

        return supervisorEntity;
    }

    public void updateSupervisor(SupervisorEntity supervisorEntity){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        SupervisorEntity supervisorEntityDTO = (SupervisorEntity) session.get(SupervisorEntity.class, supervisorEntity.getIdNumber());
        supervisorEntity.setFirstName(supervisorEntity.getFirstName());
        supervisorEntity.setLastName(supervisorEntity.getLastName());
        supervisorEntity.setEmail(supervisorEntity.getEmail());
        session.update(supervisorEntityDTO);
        transaction.commit();
        session.close();

    }



    public void deleteSupervisor(SupervisorEntity supervisorEntity){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        SupervisorEntity supervisorEntityDTO = (SupervisorEntity) session.get(SupervisorEntity.class, supervisorEntity.getIdNumber());
        session.delete(supervisorEntityDTO);
        transaction.commit();
        session.close();
    }


}
