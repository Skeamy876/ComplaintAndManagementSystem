package server.actions;

import factories.SessionBuilderFactory;
import models.Complaint;
import models.Query;
import models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import server.ClientHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentActions {
    private static Connection dbConn= null;
    private Statement statement= null;
    private ResultSet result = null;
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);


    public StudentActions() {
        dbConn = getDatabaseConnection();
    }

    private Connection getDatabaseConnection(){
        if (dbConn==null){
            String url = "Jdbc:mysql://localhost:3306/utechcomplaintdb";
            try {
                dbConn = DriverManager.getConnection(url,"root","");
                logger.error("Native Database Connection Established");
            }catch (SQLException e){
                logger.error("SQL EXCEPTION"+ e.getMessage());
            }catch (Exception e){
                logger.error("Unexpected error"+ e.getMessage());
            }
        }
        return dbConn;
    }

    public void createStudent(Student student) {
        String insertSQL = "INSERT INTO utechcomplaintdb.students (idNumber,email_address,first_name,last_name,password,phone_number)"
                + "VALUES('"+ student.getIdNumber()+ "','"  + student.getEmail()  + "','" + student.getFirstName()+ "','" + student.getLastName()+ "','" + student.getPassword() + "','" + student.getPhoneNumber() + "');";

        try {
            statement = dbConn.createStatement();
            int inserted = statement.executeUpdate(insertSQL);
            if (inserted == 1) {
                logger.info("Student Inserted");
            } else {
                logger.error("Student Not Inserted");
            }
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION in student create" + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error in student" + e.getMessage());
        }
    }


    public Student findStudent(long idNumber){
        String selectSql ="SELECT * FROM students WHERE idNumber ="+ idNumber;
        Student student = new Student();

        try{
            statement = dbConn.createStatement();
            result = statement.executeQuery(selectSql);
            if (result.next()){
                student.setIdNumber(result.getLong("idNumber"));
                student.setFirstName(result.getString("first_name"));
                student.setLastName(result.getString("last_name"));
                student.setPassword(result.getString("password"));
                student.setPassword(result.getString("phone_number"));

                return student;
            }else {
                logger.error("No Product found");
            }
        } catch (SQLException e) {
            logger.error("Sql Error"+ e.getMessage());
        }catch (Exception ex){
            logger.error("Unexpected  Error"+ ex.getMessage());
        }
        return student;
    }

    public List<Student> getAllStudents(){
        String getAll ="SELECT idNumber,first_name,last_name,email_address,password,phone_number from utechcompalintdb.students";
        List<Student> students = new ArrayList<>();

        try{
            statement = dbConn.createStatement();
            result = statement.executeQuery(getAll);

            while (result.next()){
                long id= result.getLong("idNumber");
                String firstname= result.getString("first_name");
                String lastname= result.getString("last_name");
                String email= result.getString("email_address");
                String password= result.getString("password");
                long phoneNumber= result.getLong("phone_number");

                students.add( new Student(firstname,lastname,phoneNumber,email,password));
            }

        }catch (SQLException e){
            logger.error("SQL EXCEPTION in student read all"+ e.getMessage());
        }catch (Exception e){
            logger.error("Unexpected error in student"+ e.getMessage());
        }
        return students;
    }


    public int deleteStudent(long idNumber){
        String deleteStudent = "DELETE FROM students WHERE student_ID ="+idNumber;
        int numberOfAffectedRecords =0;
        try {
            statement = dbConn.createStatement();
            numberOfAffectedRecords = statement.executeUpdate(deleteStudent);
            logger.error("Successfully deleted product");
        } catch (SQLException e) {
            logger.error("Sql Error"+ e.getMessage());
        }catch (Exception ex) {
            logger.error("Unexpected Error"+ ex.getMessage());

        }
        return numberOfAffectedRecords;
    }

    public List <Query> getAllStudentQueries(long studentId) {
        String getAll = "SELECT * FROM utechcomplaintdb.queries WHERE student_idNumber ="+ studentId;
        List<Query> queries = new ArrayList<>();

        try {
            statement = dbConn.createStatement();
            result = statement.executeQuery(getAll);
            while (result.next()) {
                Query query = new Query();
                query.setQueryId(result.getLong("queryId"));
                query.setQueryDetail(result.getString("query_detail"));
                query.setQueryDate(result.getDate("query_date"));
                query.setStatus(result.getString("status"));
                query.setCategory(result.getString("category"));
                queries.add(query);
            }

            if (queries.size() == 0) {
                logger.error("No queries found");
            }else {
                logger.info("Queries found");
            }

        } catch (SQLException e) {
            logger.error("SQL EXCEPTION in student Actions read all" + e.getMessage());

        } catch (Exception e) {
            logger.error("Unexpected error in student Actions" + e.getMessage());
        }
        return queries;
    }

    public void saveQuery(Student student){
        Session session = SessionBuilderFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Student studentDTO = (Student) session.get(Student.class, student.getIdNumber());
        if (studentDTO != null && studentDTO.getIdNumber() == student.getIdNumber()){
            for (Query query : student.getQueries()) {
                studentDTO.addQuery(query);
                query.setStudent(studentDTO);
            }

            session.merge(studentDTO);
            transaction.commit();
            session.close();
        }
    }

    public void saveComplaint(Student student){
        Session session = SessionBuilderFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Student studentDTO = (Student) session.get(Student.class, student.getIdNumber());
        if (studentDTO.getIdNumber() == student.getIdNumber()){
            for (Complaint complaint : student.getComplaints()) {
                studentDTO.addComplaint(complaint);
                complaint.setStudent(studentDTO);
            }
            session.save(studentDTO);
            transaction.commit();
            session.close();
        }
    }

    public List<Complaint> getAllStudentComplaints(long idNumber) {
        String getAll = "SELECT * FROM utechcomplaintdb.complaints WHERE student_idNumber ="+ idNumber + "";
        List<Complaint> complaints = new ArrayList<>();

        try {
            statement = dbConn.createStatement();
            result = statement.executeQuery(getAll);
            while (result.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintId(result.getLong("complaintId"));
                complaint.setComplaintDetail(result.getString("category"));
                complaint.setComplaintDate(result.getDate("complaint_date"));
                complaint.setStatus(result.getString("status"));
                complaints.add(complaint);
            }

        } catch (SQLException e) {
            logger.error("SQL EXCEPTION in student read all" + e.getMessage());

        } catch (Exception e) {
            logger.error("Unexpected error in student" + e.getMessage());
        }
        return complaints;


    }
}
