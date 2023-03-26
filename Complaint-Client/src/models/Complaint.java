package models;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Complaint implements Serializable {
    private static  final long serialVersionUID = 5639873163017606842L;
    private long complaintId;
    private Student student;

    private Category category;

    private String complaintDetail;

    private Date complaintDate;

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

    public String getCategory() {
        return this.category.name();
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
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

    public String getStatus() {
        return this.status.name();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }


    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Complaint complaint = (Complaint ) o;
        return Objects.equals( complaintId, complaint.complaintId );
    }

    @Override
    public int hashCode() {
        return Objects.hash( complaintId );
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
