package models;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;


public class Supervisor extends Person implements Serializable {
    @Serial
    private static  final long serialVersionUID = 1755490458986052803L;
    private List<Query> queries;
    private List<Complaint> complaints;
    private List<Advisor> advisors;

    private List<Student> students;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}