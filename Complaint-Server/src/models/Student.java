package models;



import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Student")
@Table(name = "students")
public class Student extends Person implements Serializable {
    @Serial
    private static  final long serialVersionUID = 443756519803593097L;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Complaint> complaints = new ArrayList<Complaint>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Query> queries = new ArrayList<Query>();


    public Student(){
        super();
    }


    public Student(String firstName, String lastName, long phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }



}
