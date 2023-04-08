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
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import server.actions.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable{
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private Socket conectionSocket;
    private List<ClientHandler> advisorClients;
    private List<ClientHandler> studentClients;
    boolean isAdvisor;
    boolean isOnline;
    private long id;
    private String name;
    ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);


    public ClientHandler(Socket conectionSocket, List<ClientHandler> advisorClients, List<ClientHandler> studentClients) {
        this.conectionSocket = conectionSocket;
        this.advisorClients = advisorClients;
        this.studentClients = studentClients;
        this.configureStreams();
        this.isAdvisor = false;
        this.isOnline = true;
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
        List<Student> studentList;
        Student student;
        Supervisor supervisor;
        Advisor advisor;
        Person person;
        try {
            while (true){
                try {
                    //read details of client for system chat
                    id = (long) objIs.readObject();
                    name = (String) objIs.readObject();

                    //read action from client
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
                        case "viewStudentDetails":
                            id = (long) objIs.readObject();
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
                            id = (long) objIs.readObject();
                            AdvisorActions advisorActions = new AdvisorActions();
                            List<models.Query> allQueries1 = advisorActions.findAllQueriesByAdvisor(id);
                            List<Complaint> allComplaints1 = advisorActions.findAllComplaintsByAdvisor(id);
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
                            queryActions = new QueryActions();
                            complaintActions = new ComplaintActions();
                            ResponseActions responseActions = new ResponseActions();

                            if (queryActions.findQuery(id) != null) {
                                objOs.writeObject(responseActions.findAllResponsesByQuery(id));
                                objOs.writeObject("successful");
                                logger.info("Responses sent back to client");
                            } else if (complaintActions.findComplaint(id) != null){
                                objOs.writeObject(responseActions.findAllResponsesByComplaint(id));
                                objOs.writeObject("successful");
                                logger.info("Responses sent back to client");
                            }
                            objOs.writeObject("failed");
                            logger.info("Responses not sent back to client");
                            break;
                        case "closeQuery/Complaint":
                            id = (long) objIs.readObject();
                            queryActions = new QueryActions();
                            complaintActions = new ComplaintActions();
                            if (queryActions.findQuery(id) != null) {
                                queryActions.closeQuery(id);
                                objOs.writeObject("successful");
                                logger.info("Query closed successfully");
                            } else {
                                complaintActions.closeComplaint(id);
                                objOs.writeObject("successful");
                                logger.info("Complaint closed successfully");
                            }
                            objOs.writeObject("failed");
                            logger.info("Query/Complaint not closed");
                            break;
                        case "addResponsesForQuery/Complaint":
                            id = (long) objIs.readObject();
                            long queryComplaintId = (long) objIs.readObject();
                            String responseForQueryComplaint = (String) objIs.readObject();
                            responseActions = new ResponseActions();
                            advisorActions = new AdvisorActions();
                            complaintActions = new ComplaintActions();
                            queryActions = new QueryActions();
                            advisor = advisorActions.findAdvisorReturnAdvisor(id);
                            Query query = queryActions.findQuery(queryComplaintId);
                            Complaint complaint = complaintActions.findComplaint(queryComplaintId);

                            Response response = new Response();
                            response.setResponseDetail(responseForQueryComplaint);
                            response.setResponder(advisor);
                            if (query != null) {
                                response.setQuery(query);
                                objOs.writeObject("successful");
                                logger.info("Response added successfully");
                            } else if (complaint != null){
                                response.setComplaint(complaint);
                                objOs.writeObject("successful");
                                logger.info("Response added successfully");
                            }
                            objOs.writeObject("failed");
                            logger.info("Response not added");
                            break;
                        case "fetchAdvisors":
                            objOs.writeObject("successful");
                            advisorActions = new AdvisorActions();
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
                            query = queryActions2.findQuery(id);
                            if ( query!= null) {
                                advisorActions.assignQueryToAdvisor(query, advisorId);
                                objOs.writeObject("successful");
                                logger.info("Query assigned to advisor");
                            } else {
                                complaint = complaintActions2.findComplaint(id);
                                advisorActions.assignComplaintToAdvisor(complaint, advisorId);
                                objOs.writeObject("successful");
                                logger.info("Complaint assigned to advisor");
                            }
                            break;
                        case "logout":
                            objOs.writeObject("successful");
                            logger.info("Logout successful");
                            this.closeConnection();
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
                        case "onlineUsersStudentToAdvisor":
                            advisorActions = new AdvisorActions();
                            studentActions = new StudentActions();
                            student = studentActions.findStudentReurnStudent(id);
                            advisor = advisorActions.findAdvisorReturnAdvisor(id);

                            advisorList = new ArrayList<>();
                            studentList = new ArrayList<>();
                            queryList = new ArrayList<>();
                            complaintList = new ArrayList<>();

                            if(advisor!=null){
                                advisor.getQuery().stream().forEach(query1->queryList.add(query1));
                                advisor.getComplaint().stream().forEach(complaint1->complaintList.add(complaint1));
                                for (Query query1 : queryList) {
                                    if(student!=null){
                                        if(query1.getStudent().getIdNumber()==student.getIdNumber()){
                                            advisorList.add(advisor);
                                            studentList.add(student);
                                        }
                                    }
                                }
                                for (Complaint complaint1 : complaintList) {
                                    if(complaint1.getStudent().getIdNumber()==student.getIdNumber()){
                                        advisorList.add(advisor);
                                        studentList.add(student);
                                    }
                                }
                            }
                            objOs.writeObject(advisorList);
                            objOs.writeObject(studentList);
                            objOs.writeObject("successful");
                            logger.info("Online users sent back to client");
                            break;
                        case "chat":
                            break;
                        case "videoChat":
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
                advisor= advisorActions.findAdvisorReturnAdvisor(advisor.getIdNumber());
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
