package models;

import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public Student( String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
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
