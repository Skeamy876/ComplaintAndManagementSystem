package models;


import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "Response")
@Table(name = "responses")
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "response_id")
    private long responseId;
    @Column(name = "response_detail")
    private String responseDetail;
    @Column(name = "response_date")
    private Timestamp responseDate;
    @Column(name = "complaint_id")
    private Complaint complaint;
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private Advisor responder;
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private Query query;


    public Response(long responseId, String responseDetail, Timestamp responseDate, Complaint complaint, Advisor responder, Query query) {
        this.responseId = responseId;
        this.responseDetail = responseDetail;
        this.responseDate = responseDate;
        this.complaint = complaint;
        this.responder = responder;
        this.query = query;
    }

    public Response() {
    }

    public long getResponseId() {
        return responseId;
    }

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    public String getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(String responseDetail) {
        this.responseDetail = responseDetail;
    }

    public Timestamp getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Advisor getResponder() {
        return responder;
    }

    public void setResponder(Advisor responder) {
        this.responder = responder;
    }
}
