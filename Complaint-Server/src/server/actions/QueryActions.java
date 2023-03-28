package server.actions;

import factories.SessionBuilderFactory;
import models.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class QueryActions {
    public void createQuery(Query query){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(query);
        transaction.commit();
        session.close();
    }

    public Query findQuery(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query1 = (Query) session.get(Query.class, id);
        transaction.commit();
        session.close();

        return query1;
    }

    public List<Query> findAllQueries(){
        List<Query> queries = new ArrayList<>();
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        queries = (List<Query>) session.createNativeQuery("SELECT * FROM query")
                .list();
        transaction.commit();
        session.close();


        return queries;
    }

    public void UpdateQueryCategory(Query query){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query1 = (Query) session.get(Query.class, query.getQueryId());
        query1.setCategory(query.getCategory());
        session.update(query1);
        transaction.commit();
        session.close();
    }
    public void UpdateQueryDetails(Query query){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query1 = (Query) session.get(Query.class,query.getQueryId());
        query1.setQueryDetail(query.getQueryDetail());
        session.update(query1);
        transaction.commit();
        session.close();
    }

    public void deleteQuery(long id){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query1 = (Query) session.get(Query.class,id);
        session.delete(query1);
        transaction.commit();
        session.close();
    }
}
