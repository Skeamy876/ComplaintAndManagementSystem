package models;

import factories.DbConnectorFactory;
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
    private static  final long serialVersionUID = 1755490458986052803L;
    @Transient
    private Connection dbConn = null;
    @Transient
    private Statement stmt;
    @Transient
    private ResultSet  result;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Complaint> complaints = new ArrayList<Complaint>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Query> queries = new ArrayList<Query>();


    public Student(){
        super();
        dbConn = DbConnectorFactory.getDatabaseConnection();
    }
    public Student( String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }


    // NEEDS TO BE FIXED, NOT PERSISTING USER OBJECT
    public void createStudent(Student student){
        String insertSQL = "INSERT INTO utechcomplaintdb.students (idNumber,email_address,first_name,last_name,password,phone_number)"
                +"VALUES('"+student.getIdNumber()+"','"+student.getEmail()+"','"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getPassword()+"','"+student.getPhoneNumber()+"');";
        try{
            stmt = dbConn.createStatement();
            int inserted = stmt.executeUpdate(insertSQL);
            if(inserted == 1){
                //replace with logs
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
        String selectSql = "SELECT * FROM students WHERE idNumber ="+ idNumber;
        Student student = new Student();

        try{
            stmt = dbConn.createStatement();
            result= stmt.executeQuery(selectSql);

            if (result.next()){
                student.setIdNumber(result.getLong("idNumber"));
                student.setFirstName(result.getString("first_name"));
                student.setLastName(result.getString("last_name"));
                student.setEmail(result.getString("email_address"));
                student.setPassword(result.getString("password"));
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
                Student student = new Student(firstname,lastname,phone,email);
                student.setIdNumber(id);
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
    public void saveComplaint(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Student studentDTO = (Student) session.get(Student.class, this.getIdNumber());
        if (studentDTO.getIdNumber() == this.getIdNumber()){
            studentDTO.getComplaints().add(this.getComplaints().get(0));
            session.save(studentDTO);
            transaction.commit();
        }
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

    public Connection getDbConn() {
        return dbConn;
    }

    public void setDbConn(Connection dbConn) {
        this.dbConn = dbConn;
    }
}
