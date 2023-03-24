package models;


import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "complaints")
public class Complaint implements Serializable {
    @Serial
    private static  final long serialVersionUID = 5639873163017606842L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long complaintId;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;
    @Column(name = "category")
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


}
