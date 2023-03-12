package models;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Supervisor extends Person implements Serializable {

    private List<Query> queries;
    private List<Complaint> complaints;

    private List <Advisor> advisors;
    public Supervisor( String firstName, String lastName, long phoneNumber, String email) {
        super( firstName, lastName, phoneNumber, email);
    }

    public Supervisor() {
        super();
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }



}
