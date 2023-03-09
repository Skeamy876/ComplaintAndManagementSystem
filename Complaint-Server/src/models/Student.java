package models;

import factories.DbConnectorFactory;

import javax.swing.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person implements Serializable {
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

    public void createStudent(Student student){
        String insertSQL = "INSERT INTO utechcomplaintdb.students (student_ID,first_name,last_name,email_address,phone_number)"
                +"VALUES('"+student.getIdNumber()+"','"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getEmail()+"','"+student.getPhoneNumber()+"');";
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
    public Student findStudent(long idNumber){
        String selectSql = "SELECT * FROM students WHERE student_ID ="+ idNumber;
        Student student = new Student();

        try{
            stmt = dbConn.createStatement();
            result= stmt.executeQuery(selectSql);

            if (result.next()){
                student.setIdNumber(result.getLong("student_ID"));
                student.setFirstName(result.getString("first_name"));
                student.setLastName(result.getString("last_name"));
                student.setEmail(result.getString("email_address"));
                student.setPhoneNumber(result.getLong("phone_number"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
        System.out.println(student);
        return student;
    }

    public List<Student> getAllStudents(){
        String getAll ="SELECT idNumber,first_name,last_name,email_address, phone_number from utechcompalintdb.students";
        List<Student> students = new ArrayList<>();
        try{
            stmt = dbConn.createStatement();
            result = stmt.executeQuery(getAll);

            while (result.next()){
                long id = result.getLong("idNumber");
                String firstname = result.getString("first_name");
                String lastname = result.getString("last_name");
                String email = result.getString("email_address");
                long phone = result.getLong("phone_number");
                Student student = new Student(id,firstname,lastname,phone,email);
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        for (Student student: students){
            System.out.println(student);
        }

        return students;
    }

    public void deleteStudent(long idNumber){
        String deleteStudent = "DELETE FROM students WHERE student_ID ="+idNumber;
        try {
            stmt = dbConn.createStatement();
            int numberOfRecordsAffeccted = stmt.executeUpdate(deleteStudent);
            System.out.println("Student Deleted");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex) {
            System.err.println("Unexpected error occured"+ ex.getMessage());
        }
    }

}
