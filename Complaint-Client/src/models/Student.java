package models;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person implements Serializable {
    @Serial
    private static  final long serialVersionUID = 443756519803593097L;
    private List<Query> queries = new ArrayList<Query>();


    private List<Complaint> complaints = new ArrayList<Complaint>();

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
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
    public void addQuery(Query query) {
        this.queries.add(query);
        query.setStudent(this);
    }

    public void removeQuery(Query query) {
        this.queries.remove( query );
        query.setStudent( null );
    }

    public void addComplaint(Complaint complaint) {
        this.complaints.add( complaint );
        complaint.setStudent( this );
    }

    public void removeComplaint(Complaint compplaint) {
        this.complaints.remove( compplaint );
        compplaint.setStudent( null );
    }

}
