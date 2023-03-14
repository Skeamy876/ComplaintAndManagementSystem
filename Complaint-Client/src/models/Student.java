package models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person implements Serializable {
    private static final long serialVersionUID = 1755490458986052803L;
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


}
