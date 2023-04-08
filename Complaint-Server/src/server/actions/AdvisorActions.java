package server.actions;

import factories.SessionBuilderFactory;
import models.Advisor;
import models.Complaint;
import models.Query;
import models.Student;
import models.hibernate.AdvisorEntity;
import models.hibernate.ComplaintEntity;
import models.hibernate.QueryEntity;
import models.hibernate.StudentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


public class AdvisorActions {
    ModelMapper modelMapper = new ModelMapper();


    public void createAdvisor(Advisor advisor){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(modelMapper.map(advisor, AdvisorEntity.class));
        transaction.commit();
        session.close();
    }

    public AdvisorEntity findAdvisor(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        AdvisorEntity advisorEntity = (AdvisorEntity) session.get(AdvisorEntity.class, id);
        transaction.commit();
        session.close();

        return advisorEntity;
    }
    public Advisor findAdvisorReturnAdvisor(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        AdvisorEntity advisorEntity = (AdvisorEntity) session.get(AdvisorEntity.class, id);
        Advisor advisor = modelMapper.map(advisorEntity, Advisor.class);
        transaction.commit();
        session.close();

        return advisor;
    }

    public void updateAdvisor(Advisor advisor){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        AdvisorEntity advisorEntityDTO = (AdvisorEntity) session.get(AdvisorEntity.class, advisor.getIdNumber());
        advisorEntityDTO.setFirstName(advisor.getFirstName());
        advisorEntityDTO.setLastName(advisor.getLastName());
        advisorEntityDTO.setEmail(advisor.getEmail());
        session.update(advisorEntityDTO);
        transaction.commit();
        session.close();

    }

    public void deleteAdvisor(Advisor advisor){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        AdvisorEntity advisorEntityDTO = session.get(AdvisorEntity.class, advisor.getIdNumber());
        session.delete(advisorEntityDTO);
        transaction.commit();
        session.close();
    }

    public List<Advisor> findAllAdvisors() {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<AdvisorEntity> advisorEntities = session.createQuery("from Advisor").list();

        Advisor advisor = new Advisor();
        Student student = new Student();
        Query query = new Query();
        Complaint complaint = new Complaint();
        List<Query> queryList = new ArrayList<>();
        List<Complaint> complaintList = new ArrayList<>();
        List<Advisor> advisorList = new ArrayList<>();

        for (AdvisorEntity advisorEntity : advisorEntities) {
            for (QueryEntity queryEntity : advisorEntity.getQueryEntity()) {
                StudentEntity studentEntity = queryEntity.getStudent();
                 student= modelMapper.map(studentEntity, Student.class);
                 query = modelMapper.map(queryEntity, Query.class);
                 query.setStudent(student);
                 queryList.add(query);
            }

            for (ComplaintEntity complaintEntity : advisorEntity.getComplaintEntity()) {
                StudentEntity studentEntity = complaintEntity.getStudent();
                student = modelMapper.map(studentEntity, Student.class);
                complaint = modelMapper.map(complaintEntity, Complaint.class);
                complaint.setStudent(student);
                complaintList.add(complaint);
            }
            advisor = modelMapper.map(advisorEntity, Advisor.class);
            advisor.setComplaint( complaintList);
            advisor.setQuery( queryList);
            advisorList.add(advisor);
        }
        transaction.commit();
        session.close();
        return advisorList;
    }

    public void assignQueryToAdvisor(Query query, long advisorId) {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        AdvisorEntity advisorEntity = session.get(AdvisorEntity.class, advisorId);
        advisorEntity.getQueryEntity().add(modelMapper.map(query, QueryEntity.class));
        session.update(advisorEntity);
        transaction.commit();
        session.close();
    }

    public void assignComplaintToAdvisor(Complaint complaint, long advisorId) {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        AdvisorEntity advisorEntity = session.get(AdvisorEntity.class, advisorId);
        advisorEntity.getComplaintEntity().add(modelMapper.map(complaint, ComplaintEntity.class));
        session.update(advisorEntity);
        transaction.commit();
        session.close();
    }
}
