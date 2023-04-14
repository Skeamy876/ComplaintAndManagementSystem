package server.actions;

import factories.SessionBuilderFactory;
import models.Advisor;
import models.Complaint;
import models.Query;
import models.Response;
import models.hibernate.AdvisorEntity;
import models.hibernate.ComplaintEntity;
import models.hibernate.QueryEntity;
import models.hibernate.ResponseEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ResponseActions {
    ModelMapper modelMapper = new ModelMapper();

    public void addResponse(Response response){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        QueryEntity queryEntity = null;
        ComplaintEntity complaintEntity = null;
        Complaint Complaint = response.getComplaint();
        Query query = response.getQuery();

        ResponseEntity responseEntity = modelMapper.map(response, ResponseEntity.class);

        if (Complaint != null) {
            complaintEntity = modelMapper.map(Complaint, ComplaintEntity.class);
            responseEntity.setComplaint(complaintEntity);
        }
        if (query != null) {
            queryEntity = modelMapper.map(query, QueryEntity.class);
            responseEntity.setQuery(queryEntity);
        }
        AdvisorEntity advisorEntity = modelMapper.map(response.getResponder(), AdvisorEntity.class);
        responseEntity.setComplaint(complaintEntity);
        responseEntity.setQuery(queryEntity);
        responseEntity.setResponder(advisorEntity);

        session.save(responseEntity);
        transaction.commit();
        session.close();
    }

    public Response findResponse(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        ResponseEntity responseEntity1 = (ResponseEntity) session.get(ResponseEntity.class, id);
        Response response = modelMapper.map(responseEntity1, Response.class);
        Complaint complaint = modelMapper.map(responseEntity1.getComplaint(), Complaint.class);
        Query query = modelMapper.map(responseEntity1.getQuery(), Query.class);
        Advisor advisor = modelMapper.map(responseEntity1.getResponder(), Advisor.class);
        response.setComplaint(complaint);
        response.setQuery(query);
        response.setResponder(advisor);
        transaction.commit();
        session.close();

        return response;
    }

    public List<Response> findAllResponses(){
        List<ResponseEntity> responseEntities = new ArrayList<>();
        List<Response> responses = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        responseEntities = (List<ResponseEntity>) session.createNativeQuery("SELECT * FROM responses")
                .list();
        for (ResponseEntity responseEntity : responseEntities) {
            Response response1 = modelMapper.map(responseEntity, Response.class);
            Complaint complaint = modelMapper.map(responseEntity.getComplaint(), Complaint.class);
            Query query = modelMapper.map(responseEntity.getQuery(), Query.class);
            Advisor advisor = modelMapper.map(responseEntity.getResponder(), Advisor.class);
            response1.setComplaint(complaint);
            response1.setQuery(query);
            response1.setResponder(advisor);
            responses.add(response1);
        }
        transaction.commit();
        session.close();
        return responses;
    }


    public void deleteResponse(long id) {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        ResponseEntity responseEntity1 = (ResponseEntity) session.get(ResponseEntity.class, id);
        session.delete(responseEntity1);
        transaction.commit();
        session.close();
    }


    public List<Response> findAllResponsesByUser(long userId) {
        List<ResponseEntity> responseEntity = new ArrayList<>();
        List<Response> responses = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        responseEntity = (List<ResponseEntity>) session.createNativeQuery("SELECT * FROM responses WHERE advisor_id = :userId")
                .setParameter("userId", userId)
                .list();

        for (ResponseEntity responseEntity1 : responseEntity) {
            Response response1 = modelMapper.map(responseEntity1, Response.class);
            Complaint complaint = modelMapper.map(responseEntity1.getComplaint(), Complaint.class);
            Query query = modelMapper.map(responseEntity1.getQuery(), Query.class);
            Advisor advisor = modelMapper.map(responseEntity1.getResponder(), Advisor.class);
            response1.setComplaint(complaint);
            response1.setQuery(query);
            response1.setResponder(advisor);
            responses.add(response1);
        }
        transaction.commit();
        session.close();
        return responses;
    }

    public List<Response> findAllResponsesByQueryOrComplaint(long queryComplainId) {
        List<ResponseEntity> responseEntity = new ArrayList<>();
        List<Response> responses = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        responseEntity = (List<ResponseEntity>) session.createNativeQuery("SELECT * FROM responses WHERE query_id = :queryComplainId OR complaint_id = :queryComplainId")
                .setParameter("queryComplainId", queryComplainId)
                .list();
        for (ResponseEntity responseEntity1 : responseEntity) {
            Response response1 = modelMapper.map(responseEntity1, Response.class);
            Complaint complaint = modelMapper.map(responseEntity1.getComplaint(), Complaint.class);
            Query query = modelMapper.map(responseEntity1.getQuery(), Query.class);
            Advisor advisor = modelMapper.map(responseEntity1.getResponder(), Advisor.class);
            response1.setComplaint(complaint);
            response1.setQuery(query);
            response1.setResponder(advisor);
            responses.add(response1);
        }
        transaction.commit();
        session.close();
        return responses;
    }


    public List<Response> findAllResponsesByQuery(long id) {
        List<ResponseEntity> responseEntity = new ArrayList<>();
        List<Response> responses = new ArrayList<>();
        ComplaintEntity complaintEntity;
        QueryEntity queryEntity;
        Complaint complaint;
        Query query ;
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        responseEntity = (List<ResponseEntity>) session.createNativeQuery("SELECT * FROM responses WHERE query_id = :id", ResponseEntity.class)
                .setParameter("id", id)
                .list();
        for (ResponseEntity responseEntity1 : responseEntity) {
            Response response1 = modelMapper.map(responseEntity1, Response.class);
            if (responseEntity1.getComplaint() != null) {
                complaint = modelMapper.map(responseEntity1.getComplaint(), Complaint.class);
                response1.setComplaint(complaint);
            }
            if (responseEntity1.getQuery() != null) {
                query = modelMapper.map(responseEntity1.getQuery(), Query.class);
                response1.setQuery(query);
            }
            Advisor advisor = modelMapper.map(responseEntity1.getResponder(), Advisor.class);
            response1.setResponder(advisor);
            responses.add(response1);
        }
        transaction.commit();
        session.close();
        return responses;
    }

    public List<Response> findAllResponsesByComplaint(long id) {
        List<ResponseEntity> responseEntity = new ArrayList<>();
        List<Response> responses = new ArrayList<>();
        ComplaintEntity complaintEntity;
        QueryEntity queryEntity;
        Complaint complaint;
        Query query ;
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

            Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        responseEntity = (List<ResponseEntity>) session.createNativeQuery("SELECT * FROM responses WHERE complaint_id = :id", ResponseEntity.class)
                .setParameter("id", id)
                .list();
        for (ResponseEntity responseEntity1 : responseEntity) {
            Response response1 = modelMapper.map(responseEntity1, Response.class);
            if (responseEntity1.getComplaint() != null) {
                complaint = modelMapper.map(responseEntity1.getComplaint(), Complaint.class);
                response1.setComplaint(complaint);
            }
            if (responseEntity1.getQuery() != null) {
                query = modelMapper.map(responseEntity1.getQuery(), Query.class);
                response1.setQuery(query);
            }
            Advisor advisor = modelMapper.map(responseEntity1.getResponder(), Advisor.class);
            response1.setResponder(advisor);
            responses.add(response1);
        }
        transaction.commit();
        session.close();
        return responses;
    }
}
