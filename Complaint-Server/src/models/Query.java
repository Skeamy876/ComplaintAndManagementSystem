package models;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity(name = "Query")
@Table(name = "queries")
public class Query implements Serializable {
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
    @OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private List<Response> responses = new ArrayList<Response>();
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private Student student;

    public Query( Category category, String queryDetail, Date queryDate, Status status) {
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
    public Student getStudent() {

        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }



    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Query query = (Query) o;
        return Objects.equals( queryId, query.queryId );
    }

    @Override
    public int hashCode() {
        return Objects.hash( queryId );
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
