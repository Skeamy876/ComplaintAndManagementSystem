package models.hibernate;

import models.Category;
import models.Status;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "Query")
@Table(name = "queries")
public class QueryEntity implements Serializable {
    @Serial
    private static  final long serialVersionUID = 760771714612821918L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long queryId;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name="query_detail")
    private String queryDetail;
    @Column(name="query_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date queryDate;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
    @ManyToOne
    private StudentEntity studentEntity;

    public QueryEntity(Category category, String queryDetail, Date queryDate, Status status) {
        this.queryId = 0;
        this.category = category;
        this.queryDetail = queryDetail;
        this.queryDate = queryDate;
        this.status = status;
    }

    public QueryEntity(long queryId, Category category, String queryDetail, Date queryDate, Status status, StudentEntity studentEntity) {
        this.queryId = queryId;
        this.category = category;
        this.queryDetail = queryDetail;
        this.queryDate = queryDate;
        this.status = status;
        this.studentEntity = studentEntity;
    }

    public QueryEntity() {
    }

    public long getQueryId() {
        return queryId;
    }

    public void setQueryId(long queryId) {
        this.queryId = queryId;
    }

    public String getCategory() {
        return this.category.name();
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
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

    public String getStatus() {
        return this.status.name();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }
    public StudentEntity getStudent() {

        return studentEntity;
    }

    public void setStudent(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
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
