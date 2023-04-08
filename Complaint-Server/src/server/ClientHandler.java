package server;

import at.favre.lib.crypto.bcrypt.BCrypt;
import factories.SessionBuilderFactory;
import models.*;
import models.hibernate.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import server.actions.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable{
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private Socket conectionSocket;
    ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);


    public ClientHandler(Socket conectionSocket) {
        this.conectionSocket = conectionSocket;
        this.configureStreams();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
    private void configureStreams(){
        try {
            objOs = new ObjectOutputStream(conectionSocket.getOutputStream());
            objIs = new ObjectInputStream(conectionSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("IO Exception occurred within this try catch block for client handler config streams");
        }
    }

    @Override
    public void run() {
      this.waitForRequests();
    }
    private void waitForRequests() {
        StudentActions studentActions = new StudentActions();
        String action = " ";
        String dbPassword;
        StudentEntity studentEntity;
        SupervisorEntity supervisorEntity;
        AdvisorEntity advisorEntity;
        Student student;
        Supervisor supervisor;
        Advisor advisor;
        Person person;
        boolean flag = false;
        try {
            while (true){
                try {
                    action = (String) objIs.readObject();
                    switch (action){
                        case "Authenticate":
                            person = (Person) objIs.readObject();
                            dbPassword = this.getHashedPasswordFromDatabase(person.getIdNumber());
                            if (person instanceof Student) {
                                student = this.authenticateUser((Student) person,dbPassword);
                                objOs.writeObject(student);
                                logger.info("Student successfully Authenticated");
                            } else if (person instanceof Advisor) {
                                advisor = this.authenticateUser((Advisor) person,dbPassword);
                                objOs.writeObject(advisor);
                                logger.info("Advisor successfully Authenticated");
                            } else if (person instanceof Supervisor) {
                                supervisor = this.authenticateUser((Supervisor) person, dbPassword);
                                objOs.writeObject(supervisor);
                                logger.info("Supervisor successfully Authenticated");
                            }else {
                                objOs.writeObject("failed");
                                logger.info("Authentication failed");
                            }

                            break;
                        case "Add Query":
                            student = (Student) objIs.readObject();
                            studentActions.saveQuery(student);
                            objOs.writeObject("successful");
                            logger.info("Query Added successfully and confirmation sent back to client");
                            break;
                        case "Add Complaint":
                            student = (Student) objIs.readObject();
                            studentActions = new StudentActions();
                            studentActions.saveComplaint(student);
                            objOs.writeObject("successful");
                            logger.info("Query Added successfully and confirmation sent back to client");
                            break;

                        case "AllStudentQueriesAndComplaints":
                            long id2 = (long) objIs.readObject();
                            StudentActions studentActions1 = new StudentActions();
                            List<QueryEntity> queries = studentActions1.getAllStudentQueries(id2);
                            List<ComplaintEntity> complaintEntities = studentActions1.getAllStudentComplaints(id2);

                            List<models.Query> queryList = new ArrayList<>();
                            List<Complaint> complaintList = new ArrayList<>();
                            for (QueryEntity queryEntity : queries) {
                                queryList.add(modelMapper.map(queryEntity, models.Query.class));
                            }
                            for (ComplaintEntity complaintEntity : complaintEntities) {
                                complaintList.add(modelMapper.map(complaintEntity, Complaint.class));
                            }

                            objOs.writeObject(queryList);
                            objOs.writeObject(complaintList);
                            objOs.writeObject("successful");
                            logger.info("All student queries sent back to client");
                            break;
                        case "loadStudentDetailsForAdvisor":
                            long id = (long) objIs.readObject();
                            QueryActions queryActionsAdvisor = new QueryActions();
                            ComplaintActions complaintActionsAdvisor = new ComplaintActions();
                            queryActionsAdvisor.findQuery(id);
                            if (queryActionsAdvisor.findQuery(id) != null) {
                                objOs.writeObject(queryActionsAdvisor.findQuery(id).getStudent());
                                objOs.writeObject("successful");
                                logger.info("Student details sent back to client");
                            } else {
                                objOs.writeObject(complaintActionsAdvisor.findComplaint(id).getStudent());
                                objOs.writeObject("successful");
                                logger.info("Student details sent back to client");
                            }

                            break;
                        case "allQueries/Complaints":
                            QueryActions queryActions = new QueryActions();
                            ComplaintActions complaintActions = new ComplaintActions();
                            List<models.Query> allQueries = queryActions.findAllQueries();
                            List<Complaint> allComplaintEntities = complaintActions.findAllComplaints();
                            objOs.writeObject(allQueries);
                            objOs.writeObject(allComplaintEntities);
                            objOs.writeObject("successful");
                            logger.info("All queries and complaints sent back to client");
                            break;
                        case "viewAssignQueries/Complaints":
                            id = (int) objIs.readObject();
                            QueryActions queryActions1 = new QueryActions();
                            ComplaintActions complaintActions1 = new ComplaintActions();
                            List<models.Query> allQueries1 = queryActions1.findAllQuerysByUser(id);
                            List<Complaint> allComplaints1 = complaintActions1.findAllComplaintsByUser(id);
                            objOs.writeObject(allQueries1);
                            objOs.writeObject(allComplaints1);
                            objOs.writeObject("successful");
                            logger.info("All queries and complaints sent back to client");
                            break;
                        case "GetSupervisor":
                            id = (int) objIs.readObject();
                            SupervisorActions supervisorActions = new SupervisorActions();
                            SupervisorEntity supervisorEntity1 = supervisorActions.findSupervisor(id);
                            objOs.writeObject(supervisorEntity1);
                            logger.info("Supervisor details sent back to client");

                            break;
                        case "getResponsesForQuery/Complaint":
                            id = (long) objIs.readObject();
                            ResponseActions responseActions = new ResponseActions();
                            List<Response> responses = responseActions.findAllResponsesByQueryOrComplaint(id);
                            objOs.writeObject(responses);
                            objOs.writeObject("successful");
                            logger.info("All responses sent back to client");
                            break;
                        case "fetchAdvisors":
                            objOs.writeObject("successful");
                            AdvisorActions advisorActions = new AdvisorActions();
                            List<Advisor> advisorList = advisorActions.findAllAdvisors();
                            objOs.writeObject(advisorList);
                            logger.info("All advisors sent back to client");
                            break;
                        case "assignAdvisor":
                            long advisorId = (long) objIs.readObject();
                            id = (long) objIs.readObject();
                            QueryActions queryActions2 = new QueryActions();
                            ComplaintActions complaintActions2 = new ComplaintActions();
                            advisorActions = new AdvisorActions();
                            Query query = queryActions2.findQuery(id);
                            if ( query!= null) {
                                advisorActions.assignQueryToAdvisor(query, advisorId);
                                objOs.writeObject("successful");
                                logger.info("Query assigned to advisor");
                            } else {
                                Complaint complaint = complaintActions2.findComplaint(id);
                                advisorActions.assignComplaintToAdvisor(complaint, advisorId);
                                objOs.writeObject("successful");
                                logger.info("Complaint assigned to advisor");
                            }
                            break;
                        case "logout":

                            break;
                        case "getComplaints/QueriesByCategory":
                            String category = (String) objIs.readObject();
                            QueryActions queryActions3 = new QueryActions();
                            ComplaintActions complaintActions3 = new ComplaintActions();
                            List<models.Query> queriesByCategory = queryActions3.findAllQueriesByCategory(category);
                            List<Complaint> complaintsByCategory = complaintActions3.findAllComplaintsByCategory(category);
                            objOs.writeObject(queriesByCategory);
                            objOs.writeObject(complaintsByCategory);
                            objOs.writeObject("successful");
                            logger.info("All queries and complaints sent back to client");
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    logger.fatal("Class not found exception, check try/catch block");
                }
            }
        }catch (EOFException ex){
            ex.printStackTrace();
            logger.warn("EOF Exception occurred within this try catch block");
        }catch (IOException e){
            e.printStackTrace();
            logger.warn("IO Exception occurred within this try catch block");
        }

    }


    private Student authenticateUser(Student student, String hashedPassword){
        StudentActions studentActions = new StudentActions();
        StudentEntity studentEntity = studentActions.findStudent(student.getIdNumber());
        if (studentEntity != null){
            BCrypt.Result result = BCrypt.verifyer().verify(studentEntity.getPassword().toCharArray(),hashedPassword);
            if (hashedPassword!=null) {
                Session session = SessionBuilderFactory.getSessionFactory().getCurrentSession();
                Transaction transaction = session.beginTransaction();

                studentEntity = session.get(StudentEntity.class, studentEntity.getIdNumber());
                List<Query> queries = studentEntity.getQueries().stream()
                        .map(queryEntity -> modelMapper.map(queryEntity, Query.class))
                        .collect(Collectors.toList());
                List<Complaint> complaints = studentEntity.getQueries().stream()
                        .map(complaintEntity -> modelMapper.map(complaintEntity, Complaint.class))
                        .collect(Collectors.toList());
                student.setQueries(queries);
                student.setComplaints(complaints);
                student = modelMapper.map(studentEntity, Student.class);
                transaction.commit();
                session.close();
                return student;
            }
        }
        return null;
    }
    private Advisor authenticateUser(Advisor advisor, String hashedPassword){
        AdvisorActions advisorActions = new AdvisorActions();
        AdvisorEntity advisorEntity = advisorActions.findAdvisor(advisor.getIdNumber());
        if (advisorEntity != null){
            BCrypt.Result result = BCrypt.verifyer().verify(advisor.getPassword().toCharArray(),hashedPassword);
            if(result.verified){
                advisor = modelMapper.map(advisorEntity,Advisor.class);
                return advisor;
            }
        }

        return null;
    }
    private Supervisor authenticateUser(Supervisor supervisor, String hashedPassword){
        SupervisorActions supervisorActions = new SupervisorActions();
        SupervisorEntity supervisorEntityDTO = supervisorActions.findSupervisor(supervisor.getIdNumber());
        if (supervisorEntityDTO != null){
            BCrypt.Result result = BCrypt.verifyer().verify(supervisor.getPassword().toCharArray(),hashedPassword);
            if(result.verified){
                supervisor = modelMapper.map(supervisorEntityDTO,Supervisor.class);
                return supervisor;
            }
        }
        return null;
    }

    private  String getHashedPasswordFromDatabase(long id){
        StudentActions studentActions = new StudentActions();
        StudentEntity studentEntity1 =studentActions.findStudent(id);

        AdvisorActions advisorActions = new AdvisorActions();
        AdvisorEntity advisorEntity1 = advisorActions.findAdvisor(id);

        SupervisorActions supervisorActions = new SupervisorActions();
        SupervisorEntity supervisorEntity1 = supervisorActions.findSupervisor(id);

        List<Object> results = new ArrayList<>();
        if (studentEntity1 != null) {
            results.add(studentEntity1);
        }
        if (advisorEntity1 != null) {
            results.add(advisorEntity1);
        }
        if (supervisorEntity1 != null) {
            results.add(supervisorEntity1);
        }

        if (results.size() != 1) {
            throw new NonUniqueResultException(results.size());
        }
        Object result = results.get(0);
        PersonEntity person = (PersonEntity) result;

        return person.getPassword();
    }

    private void closeConnection(){
        try {
            if (objOs != null) {
                objOs.close();
            }
            if (objIs != null) {
                objIs.close();
            }
            if (conectionSocket != null) {
                conectionSocket.close();
            }
            logger.info("Closed connection streams and sockets");

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IO Exception occurred check try catch block within this closeConnection method");
        }
    }

}
