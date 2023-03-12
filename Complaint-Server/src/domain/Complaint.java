package domain;


import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "complaints")
public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long complaintId;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;
    @OneToOne
    @JoinColumn(name = "category_ID")
    private Category category;
    @Column(name = "complaint_detail")
    private String complaintDetail;
    @Column(name = "complaint_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date complaintDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Complaint(long complaintId, Student student, Category category, String complaintDetail, Date complaintDate, Status status) {
        this.complaintId = complaintId;
        this.student = student;
        this.category = category;
        this.complaintDetail = complaintDetail;
        this.complaintDate = complaintDate;
        this.status = status;
    }

    public Complaint() {
    }

    public enum Status{
        OPEN,
        CLOSE
    }

    public long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(long complaintId) {
        this.complaintId = complaintId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getComplaintDetail() {
        return complaintDetail;
    }

    public void setComplaintDetail(String complaintDetail) {
        this.complaintDetail = complaintDetail;
    }


    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", student=" + student +
                ", category=" + category +
                ", complaintDetail='" + complaintDetail + '\'' +
                ", complaintDate=" + complaintDate +
                ", status=" + status +
                '}';
    }


    public void createComplaint(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(this);
        transaction.commit();
        session.close();
    }

    public Complaint findComplaint(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint complaint = (Complaint) session.get(Complaint.class,  this.complaintId);
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

    public void UpdateComplaintCategory(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class,this.complaintId);
        Complaint.setCategory(this.category);
        session.update(Complaint);
        transaction.commit();
        session.close();
    }
    public void UpdateComplaintDetails(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class,this.complaintId);
        Complaint.setComplaintDetail(this.complaintDetail);
        session.update(Complaint);
        transaction.commit();
        session.close();
    }

    public void deleteComplaint(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class,this.complaintId);
        session.delete(Complaint);
        transaction.commit();
        session.close();
    }
}
