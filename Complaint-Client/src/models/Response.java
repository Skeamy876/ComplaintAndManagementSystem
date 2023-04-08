package models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private long responseId;
    private String responseDetail;
    private Timestamp responseDate;
    private Complaint complaint;
    private Query query;
    private Advisor responder;


    public Response(long responseId, String responseDetail, Timestamp responseDate, Complaint complaint, Query query) {
        this.responseId = responseId;
        this.responseDetail = responseDetail;
        this.responseDate = responseDate;
        this.complaint = complaint;
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

    @Override
    public String toString() {
        return  "\n responseId :" + responseId +
                "\nresponseDetail :" + responseDetail +
                "\n responseDate :" + responseDate +
                "\n by :" + responder;


    }
}