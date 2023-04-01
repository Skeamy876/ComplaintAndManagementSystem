package server;

import at.favre.lib.crypto.bcrypt.BCrypt;
import factories.SessionBuilderFactory;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import server.actions.AdvisorActions;
import server.actions.StudentActions;
import server.actions.SupervisorActions;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private Socket conectionSocket;
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);


    public ClientHandler(Socket conectionSocket) {
        this.conectionSocket = conectionSocket;
        this.configureStreams();
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
                                objOs.flush();
                                logger.info("Student successfully Authenticated");
                            } else if (person instanceof Supervisor) {
                                person = this.authenticateUser((Student) person,dbPassword);
                                supervisor = (Supervisor) person;
                                objOs.writeObject(supervisor);
                                objOs.flush();
                                logger.info("Supervisor successfully Authenticated");
                            } else if (person instanceof Advisor) {
                                advisor = this.authenticateUser((Advisor) person,dbPassword);
                                objOs.writeObject(advisor);
                                objOs.flush();
                                logger.info("Advisor successfully Authenticated");
                            } else {
                                objOs.writeObject("failed");
                                objOs.flush();
                                logger.info("Authentication failed");
                            }

                            break;
                        case "Add Query":
                            models.Query query = (models.Query) objIs.readObject();
                            student = (Student) objIs.readObject();
                            student.addQuery(query);
                            studentActions.saveQuery(student);
                            student.removeQuery(query);
                            objOs.writeObject("successful");
                            logger.info("Query Added successfully and confirmation sent back to client");
                            break;
                        case "Add Complaint":
                            Complaint complaint = (Complaint) objIs.readObject();
                            student = (Student) objIs.readObject();
                            student.addComplaint(complaint);
                            studentActions = new StudentActions();
                            studentActions.saveComplaint(student);
                            objOs.writeObject("successful");
                            logger.info("Query Added successfully and confirmation sent back to client");
                            break;

                        case "AllStudentQueriesAndComplaints":
                            long id2 = (long) objIs.readObject();
                            StudentActions studentActions1 = new StudentActions();
                            List<models.Query> queries = studentActions1.getAllStudentQueries(id2);
                            List<Complaint> complaints = studentActions1.getAllStudentComplaints(id2);
                            objOs.writeObject(queries);
                            objOs.writeObject(complaints);
                            objOs.writeObject("successful");
                            logger.info("All student queries sent back to client");
                            break;
                        case "assignAdvisorToStudent":




                            break;
                        case "logout":

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

    private Student authenticateUser(Student student,String hashedPassword){
        StudentActions studentActions = new StudentActions();
        Student studentDTO = studentActions.findStudent(student.getIdNumber());
        BCrypt.Result result = BCrypt.verifyer().verify(student.getPassword().toCharArray(),hashedPassword);
        if (studentDTO != null && result.verified){
            return studentDTO;
        }
        return null;
    }
    private Advisor authenticateUser(Advisor advisor,String hashedPassword){
        AdvisorActions advisorActions = new AdvisorActions();
        Advisor advisorDTO = advisorActions.findAdvisor(advisor.getIdNumber());
        BCrypt.Result result = BCrypt.verifyer().verify(advisor.getPassword().toCharArray(),hashedPassword);

        if (advisorDTO != null && result.verified){
            return advisorDTO;
        }
        return null;
    }
    private Supervisor authenticateUser(Supervisor supervisor,String hashedPassword){
        SupervisorActions supervisorActions = new SupervisorActions();
        Supervisor supervisorDTO = supervisorActions.findSupervisor(supervisor.getIdNumber());
        BCrypt.Result result = BCrypt.verifyer().verify(supervisor.getPassword().toCharArray(),hashedPassword);

        if (supervisorDTO != null && result.verified){
            return supervisorDTO;
        }
        return null;
    }

    private  String getHashedPasswordFromDatabase(long id){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String hql1 = "SELECT s FROM Student s WHERE s.idNumber = :idNumber";
        Query query1 = session.createQuery(hql1);
        query1.setParameter("idNumber", id);
        Student student = (Student) query1.uniqueResult();

        String hql2 = "SELECT a FROM Advisor a WHERE a.idNumber = :idNumber";
        Query query2 = session.createQuery(hql2);
        query2.setParameter("idNumber", id);
        Advisor advisor = (Advisor) query2.uniqueResult();

        String hql3 = "SELECT sup FROM Supervisor sup WHERE sup.idNumber = :idNumber";
        Query query3 = session.createQuery(hql3);
        query3.setParameter("idNumber", id);
        Supervisor supervisor = (Supervisor) query3.uniqueResult();

        List<Object> results = new ArrayList<>();
        if (student != null) {
            results.add(student);
        }
        if (advisor != null) {
            results.add(advisor);
        }
        if (supervisor != null) {
            results.add(supervisor);
        }

        if (results.size() != 1) {
            throw new NonUniqueResultException(results.size());
        }
        Object result = results.get(0);
        Person person = (Person) result;
        transaction.commit();
        session.close();
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
