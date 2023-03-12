package models;


import java.io.Serializable;
import java.util.Date;

public class Query implements Serializable {
    private long queryId;
    private Category category;
    private String queryDetail;
    private Student student;
    private Date queryDate;
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

}
