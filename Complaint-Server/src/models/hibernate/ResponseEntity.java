package models.hibernate;


import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "Response")
@Table(name = "responses")
public class ResponseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "response_id")
    private long responseId;
    @Column(name = "response_detail")
    private String responseDetail;
    @Column(name = "response_date")
    private Timestamp responseDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    @JoinColumn(name = "complaint_id", foreignKey = @ForeignKey(name = "COMPLAINT_ID_FK"))
    private ComplaintEntity complaintEntity;
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    @JoinColumn(name = "advisor_id" , foreignKey = @ForeignKey(name = "ADVISOR_ID_FK"))
    private AdvisorEntity responder;
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    @JoinColumn(name = "query_id" , foreignKey = @ForeignKey(name = "QUERY_ID_FK"))
    private QueryEntity queryEntity;


    public ResponseEntity(long responseId, String responseDetail, Timestamp responseDate, ComplaintEntity complaintEntity, AdvisorEntity responder, QueryEntity queryEntity) {
        this.responseId = responseId;
        this.responseDetail = responseDetail;
        this.responseDate = responseDate;
        this.complaintEntity = complaintEntity;
        this.responder = responder;
        this.queryEntity = queryEntity;
    }

    public ResponseEntity() {
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

    public ComplaintEntity getComplaint() {
        return complaintEntity;
    }

    public void setComplaint(ComplaintEntity complaintEntity) {
        this.complaintEntity = complaintEntity;
    }

    public QueryEntity getQuery() {
        return queryEntity;
    }

    public void setQuery(QueryEntity queryEntity) {
        this.queryEntity = queryEntity;
    }

    public AdvisorEntity getResponder() {
        return responder;
    }

    public void setResponder(AdvisorEntity responder) {
        this.responder = responder;
    }
    @Override
    public String toString() {
        return  "\n responseId :" + responseId +
                "\nresponseDetail :" + responseDetail +
                "\n responseDate :" + responseDate;
    }
}
