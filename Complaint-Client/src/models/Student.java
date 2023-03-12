package models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person implements Serializable {
    private List<Query> queries = new ArrayList<Query>();


    private List<Complaint> complaints = new ArrayList<Complaint>();

    public Student(){
        super();
    }
    public Student( String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

}
