package models;


import java.io.Serializable;
import java.util.Date;

public class Query implements Serializable {
    private static final long serialVersionUID = 760771714612821918L;
    private long queryId;
    private Category category;
    private String queryDetail;
    private Date queryDate;
    private Status status;
    private Student student;

    public Query(Category category, String queryDetail, Date queryDate, Status status) {
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

    public String getStatus() {
        return this.status.name();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
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
