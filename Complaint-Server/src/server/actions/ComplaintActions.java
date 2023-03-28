package server.actions;

import factories.SessionBuilderFactory;
import models.Complaint;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class ComplaintActions {
    public void createComplaint(Complaint complaint){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(complaint);
        transaction.commit();
        session.close();
    }

    public Complaint findComplaint(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint complaint = (Complaint) session.get(Complaint.class,  id);
        transaction.commit();
        session.close();

        return complaint;
    }

    public List<Complaint> findAllQueries(){
        List<Complaint> complaints = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        complaints = (List<Complaint>) session.createNativeQuery("SELECT * FROM complaints")
                .list();
        transaction.commit();
        session.close();


        return complaints;
    }

    public void UpdateComplaintCategory(Complaint complaint1){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class, complaint1.getComplaintId());
        Complaint.setCategory(complaint1.getCategory());
        session.update(Complaint);
        transaction.commit();
        session.close();
    }
    public void UpdateComplaintDetails(Complaint complaint1){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class, complaint1.getComplaintId());
        Complaint.setComplaintDetail(complaint1.getComplaintDetail());
        session.update(Complaint);
        transaction.commit();
        session.close();
    }

    public void deleteComplaint(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class,id);
        session.delete(Complaint);
        transaction.commit();
        session.close();
    }
}
