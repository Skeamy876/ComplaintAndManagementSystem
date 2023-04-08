package server.actions;

import factories.SessionBuilderFactory;
import models.Student;
import models.hibernate.ComplaintEntity;
import models.hibernate.QueryEntity;
import models.hibernate.StudentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import server.ClientHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StudentActions {
    private static Connection dbConn= null;
    private Statement statement= null;
    private ResultSet result = null;
    ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);


    public StudentActions() {
        dbConn = getDatabaseConnection();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

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

    public void createStudent(StudentEntity studentEntity) {
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(studentEntity);
        transaction.commit();
        session.close();
    }


    public StudentEntity findStudent(long idNumber){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        StudentEntity studentEntity = session.get(StudentEntity.class, idNumber);
        transaction.commit();
        session.close();


        return studentEntity;
    }

    public List<StudentEntity> getAllStudents(){
        String getAll ="SELECT idNumber,first_name,last_name,email_address,password,phone_number from utechcompalintdb.students";
        List<StudentEntity> studentEntities = new ArrayList<>();

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

                studentEntities.add( new StudentEntity(firstname,lastname,phoneNumber,email,password));
            }

        }catch (SQLException e){
            logger.error("SQL EXCEPTION in student read all"+ e.getMessage());
        }catch (Exception e){
            logger.error("Unexpected error in student"+ e.getMessage());
        }
        return studentEntities;
    }


    public int deleteStudent(long idNumber){
        String deleteStudent = "DELETE FROM person WHERE StudentEntity_idNumber ="+idNumber;
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

    public List <QueryEntity> getAllStudentQueries(long studentId) {
        String getAll = "SELECT * FROM utechcomplaintdb.queries WHERE studentEntity_idNumber ="+ studentId;
        List<QueryEntity> queries = new ArrayList<>();

        try {
            statement = dbConn.createStatement();
            result = statement.executeQuery(getAll);
            while (result.next()) {
                QueryEntity queryEntity = new QueryEntity();
                queryEntity.setQueryId(result.getLong("queryId"));
                queryEntity.setQueryDetail(result.getString("query_detail"));
                queryEntity.setQueryDate(result.getDate("query_date"));
                queryEntity.setStatus(result.getString("status"));
                queryEntity.setCategory(result.getString("category"));
                queries.add(queryEntity);
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

    public List<ComplaintEntity> getAllStudentComplaints(long idNumber) {
        String getAll = "SELECT * FROM utechcomplaintdb.complaints WHERE studentEntity_idNumber ="+ idNumber + "";
        List<ComplaintEntity> complaintEntities = new ArrayList<>();

        try {
            statement = dbConn.createStatement();
            result = statement.executeQuery(getAll);
            while (result.next()) {
                ComplaintEntity complaintEntity = new ComplaintEntity();
                complaintEntity.setComplaintId(result.getLong("complaintId"));
                complaintEntity.setComplaintDetail(result.getString("complaint_detail"));
                complaintEntity.setComplaintDate(result.getDate("complaint_date"));
                complaintEntity.setStatus(result.getString("status"));
                complaintEntity.setCategory(result.getString("category"));
                complaintEntities.add(complaintEntity);
            }

        } catch (SQLException e) {
            logger.error("SQL EXCEPTION in student read all" + e.getMessage());

        } catch (Exception e) {
            logger.error("Unexpected error in student" + e.getMessage());
        }
        return complaintEntities;


    }

    public void saveQuery(Student student){
        Session session = SessionBuilderFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        StudentEntity studentEntityDTO =  session.get(StudentEntity.class, student.getIdNumber());

        if (studentEntityDTO != null && studentEntityDTO.getIdNumber() == student.getIdNumber()){
            List<QueryEntity> queries = student.getQueries().stream()
                    .map(query -> modelMapper.map(query, QueryEntity.class))
                    .collect(Collectors.toList());
            StudentEntity studentConverted = modelMapper.map(student, StudentEntity.class);
            studentConverted.setQueries(queries);
            for (QueryEntity queryEntity : studentConverted.getQueries()){
                studentEntityDTO.addQuery(queryEntity);
                queryEntity.setStudent(studentConverted);
            }
            session.merge(studentEntityDTO);
            transaction.commit();
            session.close();
        }
    }

    public void saveComplaint(Student student){
        Session session = SessionBuilderFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        StudentEntity studentEntityDTO =  session.get(StudentEntity.class, student.getIdNumber());

        if (studentEntityDTO != null && studentEntityDTO.getIdNumber() == student.getIdNumber()){
            List<ComplaintEntity> complaints = student.getComplaints().stream()
                    .map(query -> modelMapper.map(query, ComplaintEntity.class))
                    .collect(Collectors.toList());
            StudentEntity studentConverted = modelMapper.map(student, StudentEntity.class);
            studentConverted.setComplaints(complaints);
            for (ComplaintEntity complaintEntity : studentConverted.getComplaints()){
                studentEntityDTO.addComplaint(complaintEntity);
                complaintEntity.setStudent(studentConverted);
            }
            session.merge(studentEntityDTO);
            transaction.commit();
            session.close();
        }
    }

}
