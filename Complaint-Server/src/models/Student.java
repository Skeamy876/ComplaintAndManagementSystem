package models;

import factories.DbConnectorFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Student extends Person{
    private Connection dbConn = null;
    private Statement stmt;
    private ResultSet  result;
    private ArrayList<Query> queries;
    private ArrayList<Complaint> complaints;

    public Student(){
        dbConn = DbConnectorFactory.getDatabaseConnection();
    }
    public Student(long idNumber, String firstName, String lastName, long phoneNumber, String email) {
        super(idNumber, firstName, lastName, phoneNumber, email);
    }

    public void createStudent(long idNumber, String firstName, String lastName, long phoneNumber, String email){
        String insertSQL = "INSERT INTO utechcomplaintdb.students (student_ID,first_name,last_name,email_address,phone_number)"
                +"VALUES('"+idNumber+"','"+firstName+"','"+lastName+"','"+email+"','"+phoneNumber+"');";
        try{
            stmt = dbConn.createStatement();
            int inserted = stmt.executeUpdate(insertSQL);
            if(inserted == 1){
                JOptionPane.showMessageDialog(null,"Student record added","Insertion status",JOptionPane.INFORMATION_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null,"Student record Failed","Insertion status",JOptionPane.ERROR_MESSAGE);
            }
        }catch (SQLException e){
            System.err.println("SQL EXCEPTION in student create"+ e.getMessage());
        }catch (Exception e){
            System.err.println("Unexpected error in student"+ e.getMessage());

        }

    }

}
