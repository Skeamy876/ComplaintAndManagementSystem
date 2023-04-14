package server.actions;

import factories.SessionBuilderFactory;
import models.Query;
import models.Status;
import models.Student;
import models.hibernate.QueryEntity;
import models.hibernate.StudentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;

public class QueryActions {
    ModelMapper modelMapper = new ModelMapper();

    public void createQuery(QueryEntity queryEntity){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        session.save(queryEntity);
        transaction.commit();
        session.close();
    }
    public void createQuery(Query query){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        session.save(query);
        transaction.commit();
        session.close();
    }

    public Query findQuery(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        QueryEntity queryEntity1 = (QueryEntity) session.get(QueryEntity.class, id);
        if (queryEntity1 == null) {
            return null;
        }
        StudentEntity studentEntity = queryEntity1.getStudent();
        Student student1 = modelMapper.map(studentEntity, Student.class);
        Query query = modelMapper.map(queryEntity1, Query.class);
        query.setStudent(student1);
        transaction.commit();
        session.close();

        return query;
    }

    public List<Query> findAllQueries(){
        List<QueryEntity> queries = new ArrayList<>();
        List<Query> queryList = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        queries = session.createNativeQuery("SELECT * FROM queries", QueryEntity.class)
                .list();

        for (QueryEntity queryEntity : queries) {
            StudentEntity studentEntity = queryEntity.getStudent();
            Student student1 = modelMapper.map(studentEntity, Student.class);
            Query query = modelMapper.map(queryEntity, Query.class);
            query.setStudent(student1);
            queryList.add(query);
        }
        transaction.commit();
        session.close();
        return queryList;
    }

    public List<Query> findAllQuerysByUser(long userId) {
        List<QueryEntity> queries = new ArrayList<>();
        List<Query> queryList = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        queries = session.createNativeQuery("SELECT * FROM queries WHERE studentEntity_idNumber = :userId", QueryEntity.class)
                .setParameter("userId", userId)
                .list();
        transaction.commit();
        session.close();

        for (QueryEntity queryEntity : queries) {
            Query query = modelMapper.map(queryEntity, Query.class);
            queryList.add(query);
        }
        return queryList;
    }

    public void UpdateQueryCategory(Query query){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        QueryEntity queryEntity = modelMapper.map(query, QueryEntity.class);
        QueryEntity queryEntity1 = session.get(QueryEntity.class, queryEntity.getQueryId());
        queryEntity1.setCategory(queryEntity.getCategory());
        session.update(queryEntity1);
        transaction.commit();
        session.close();
    }
    public void UpdateQueryDetails(Query query){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        QueryEntity queryEntity = modelMapper.map(query, QueryEntity.class);
        QueryEntity queryEntity1 = session.get(QueryEntity.class, queryEntity.getQueryId());
        queryEntity1.setQueryDetail(queryEntity.getQueryDetail());
        session.update(queryEntity1);
        transaction.commit();
        session.close();
    }

    public void deleteQuery(long id) {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        QueryEntity queryEntity1 = (QueryEntity) session.get(QueryEntity.class, id);
        session.delete(queryEntity1);
        transaction.commit();
        session.close();
    }

    public List<Query> findAllQueriesByCategory(String category) {
        List<QueryEntity> queries = new ArrayList<>();
        List<Query> queryList = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        queries = (List<QueryEntity>) session.createNativeQuery("SELECT * FROM queries WHERE category = :category", QueryEntity.class)
                .setParameter("category", category)
                .list();
        for (QueryEntity queryEntity : queries){
            StudentEntity studentEntity = queryEntity.getStudent();
            Student student1 = modelMapper.map(studentEntity, Student.class);
            Query query = modelMapper.map(queryEntity, Query.class);
            query.setStudent(student1);
            queryList.add(query);
        }
        transaction.commit();
        session.close();


        return queryList;
    }


    public void closeQuery(long id) {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

             Transaction transaction;
        if (session.getTransaction().isActive()){
            transaction = session.getTransaction();
        }else {
            transaction = session.beginTransaction();

        }
        QueryEntity queryEntity1 = (QueryEntity) session.get(QueryEntity.class, id);
        queryEntity1.setStatus(Status.CLOSE.name());
        session.update(queryEntity1);
        transaction.commit();
        session.close();
    }
}
