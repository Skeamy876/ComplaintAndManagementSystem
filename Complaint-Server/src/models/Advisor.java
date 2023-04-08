package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Advisor extends Person implements Serializable {
    private List<Query> query;
    private List<Complaint> complaint;

    public Advisor(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public Advisor() {
        super();
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

    public List<Complaint> getComplaint() {
        return complaint;
    }

    public void setComplaint(List<Complaint> complaint) {
        this.complaint = complaint;
    }

    public void addComplaint(Complaint complaint){
        this.complaint.add(complaint);
    }

    public void removeComplaint(Complaint complaint){
        this.complaint.remove(complaint);
    }


}
