package models;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "Query")
@Table(name = "queries")
public class Query implements Serializable {
    @Serial
    private static  final long serialVersionUID = 760771714612821918L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long queryId;

    @Column(name = "category")
    private Category category;
    @Column(name="query_detail")
    private String queryDetail;
    @Column(name="query_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date queryDate;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Complaint.Status status;
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private Student student;

    public Query( Category category, String queryDetail, Date queryDate, Complaint.Status status) {
        this.queryId = 0;
        this.category = category;
        this.queryDetail = queryDetail;
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
    public Student getStudent() {

        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Query{" +
                "queryId=" + queryId +
                ", category=" + category +
                ", queryDetail='" + queryDetail + '\'' +

                ", queryDate=" + queryDate +
                ", status=" + status +
                '}';
    }


}
