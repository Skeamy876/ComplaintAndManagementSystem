package server.actions;

import factories.SessionBuilderFactory;
import models.Complaint;
import models.Query;
import models.Status;
import models.Student;
import models.hibernate.ComplaintEntity;
import models.hibernate.StudentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ComplaintActions {
    ModelMapper modelMapper = new ModelMapper();
    public void createComplaint(ComplaintEntity complaintEntity){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(complaintEntity);
        transaction.commit();
        session.close();
    }

    public Complaint findComplaint(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        ComplaintEntity complaintEntity = (ComplaintEntity) session.get(ComplaintEntity.class,  id);
        if (complaintEntity == null) {
            return null;
        }
        StudentEntity studentEntity = complaintEntity.getStudent();
        Student student = modelMapper.map(studentEntity, Student.class);
        Complaint complaint = modelMapper.map(complaintEntity, Complaint.class);
        complaint.setStudent(student);
        transaction.commit();
        session.close();
        return complaint;
    }



    public void UpdateComplaintCategory(Complaint complaint){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        ComplaintEntity ComplaintEntity = (ComplaintEntity) session.get(ComplaintEntity.class, complaint.getComplaintId());
        ComplaintEntity.setCategory(complaint.getCategory());
        session.update(ComplaintEntity);
        transaction.commit();
        session.close();
    }
    public void UpdateComplaintDetails(Complaint complaint){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        ComplaintEntity ComplaintEntity = (ComplaintEntity) session.get(ComplaintEntity.class, complaint.getComplaintId());
        ComplaintEntity.setComplaintDetail(complaint.getComplaintDetail());
        session.update(ComplaintEntity);
        transaction.commit();
        session.close();
    }

    public void deleteComplaint(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        ComplaintEntity ComplaintEntity = (ComplaintEntity) session.get(ComplaintEntity.class,id);
        session.delete(ComplaintEntity);
        transaction.commit();
        session.close();
    }

    public List<Complaint> findAllComplaints() {
        List<ComplaintEntity> complaintEntities = new ArrayList<>();
        List<Complaint> complaintList = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        complaintEntities = (List<ComplaintEntity>) session.createNativeQuery("SELECT * FROM complaints", ComplaintEntity.class)
                .list();

        for (ComplaintEntity complaintEntity : complaintEntities) {
            StudentEntity studentEntity = complaintEntity.getStudent();
            Student student = modelMapper.map(studentEntity, Student.class);
            Complaint complaint = modelMapper.map(complaintEntity, Complaint.class);
            complaint.setStudent(student);
            complaintList.add(complaint);
        }
        transaction.commit();
        session.close();

        return complaintList;
    }

    public List<Complaint> findAllComplaintsByCategory(String category) {
        List<ComplaintEntity> complaintEntities = new ArrayList<>();
        List<Complaint> complaintList = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        complaintEntities = (List<ComplaintEntity>) session.createNativeQuery("SELECT * FROM complaints WHERE category = :category", ComplaintEntity.class)
                .setParameter("category", category)
                .list();

        for (ComplaintEntity complaintEntity : complaintEntities) {
            StudentEntity studentEntity = complaintEntity.getStudent();
            Student student = modelMapper.map(studentEntity, Student.class);
            Complaint complaint = modelMapper.map(complaintEntity, Complaint.class);
            complaint.setStudent(student);
            complaintList.add(complaint);
        }
        transaction.commit();
        session.close();
        return complaintList;
    }

    public List<Complaint> findAllComplaintsByUser(long userId) {
        List<ComplaintEntity> complaintEntities = new ArrayList<>();
        List<Complaint> complaintList = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        complaintEntities = (List<ComplaintEntity>) session.createNativeQuery("SELECT * FROM complaints WHERE StudentEntity_idNumber = :userId", ComplaintEntity.class)
                .setParameter("userId", userId)
                .list();

        for (ComplaintEntity complaintEntity : complaintEntities) {
            StudentEntity studentEntity = complaintEntity.getStudent();
            Student student = modelMapper.map(studentEntity, Student.class);
            Complaint complaint = modelMapper.map(complaintEntity, Complaint.class);
            complaint.setStudent(student);
            complaintList.add(complaint);
        }
        transaction.commit();
        session.close();
        return complaintList;
    }

    public void closeComplaint(long id) {
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        ComplaintEntity complaintEntity = (ComplaintEntity) session.get(ComplaintEntity.class, id);
        complaintEntity.setStatus(Status.CLOSED.name());
        session.update(complaintEntity);
        transaction.commit();
        session.close();
    }
}
