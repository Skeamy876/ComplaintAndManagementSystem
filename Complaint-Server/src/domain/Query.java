package domain;

import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity(name = "query")
@Table(name = "query")
public class Query implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long queryId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_ID")
    private Category category;
    @Column(name="query_detail")
    private String queryDetail;
    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;
    @Column(name="query_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date queryDate;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Complaint.Status status;

    public Query( Category category, String queryDetail, Student student, Date queryDate, Complaint.Status status) {
        this.queryId = 0;
        this.category = category;
        this.queryDetail = queryDetail;
        this.student = student;
        this.queryDate = queryDate;
        this.status = status;
    }

    public Query() {
    }

    public long getQueryId() {
        return queryId;
    }

    public void setQueryId(long queryId) {
        this.queryId = queryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getQueryDetail() {
        return queryDetail;
    }

    public void setQueryDetail(String queryDetail) {
        this.queryDetail = queryDetail;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Complaint.Status getStatus() {
        return status;
    }

    public void setStatus(Complaint.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Query{" +
                "queryId=" + queryId +
                ", category=" + category +
                ", queryDetail='" + queryDetail + '\'' +
                ", student=" + student +
                ", queryDate=" + queryDate +
                ", status=" + status +
                '}';
    }


    // Needs fixing
    public void createQuery(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(this);
        transaction.commit();
        session.close();
    }

    public Query findQuery(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query = (Query) session.get(Query.class,  this.queryId);
        transaction.commit();
        session.close();

        return query;
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

    public void UpdateQueryCategory(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query = (Query) session.get(Query.class,this.queryId);
        query.setCategory(this.category);
        session.update(query);
        transaction.commit();
        session.close();
    }
    public void UpdateQueryDetails(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query = (Query) session.get(Query.class,this.queryId);
        query.setQueryDetail(this.queryDetail);
        session.update(query);
        transaction.commit();
        session.close();
    }

    public void deleteQuery(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Query query = (Query) session.get(Query.class,this.queryId);
        session.delete(query);
        transaction.commit();
        session.close();
    }
}
