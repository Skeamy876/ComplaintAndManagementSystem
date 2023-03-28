package server;

import at.favre.lib.crypto.bcrypt.BCrypt;
import factories.SessionBuilderFactory;
import models.Complaint;
import models.Person;
import models.Query;
import models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import server.actions.StudentActions;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        Student student;
        boolean flag = false;
        try {
            while (true){
                try {
                    action = (String) objIs.readObject();
                    switch (action){
                        case "Authenticate":
                            student= (Student) objIs.readObject();
                            //Person person = (Person) student;
                            String dbPassword = this.getHashedPasswordFromDatabase(student.getIdNumber());
                            flag = this.authenticateUser(student,dbPassword);
                            if (flag == true){
                                objOs.writeObject(true);
                                objOs.flush();
                                logger.info("User successfully Authenticated");
                            }else {
                                objOs.writeObject(false);
                                logger.info("Authentication Unsuccessful");
                            }

                            break;
                        case "Add Query":
                            Query query = (Query) objIs.readObject();
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
                            List<Query> queries = studentActions1.getAllStudentQueries(id2);
                            List<Complaint> complaints = studentActions1.getAllStudentComplaints(id2);
                            objOs.writeObject(queries);
                            objOs.writeObject(complaints);
                            objOs.writeObject("successful");
                            logger.info("All student queries sent back to client");
                            break;
                        case "Add View All student Queries Responses":

                            break;
                        case "Add View All student Complaints Responses":

                            break;

                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                    logger.fatal("Class not found exception, check try/catch block");
                }finally {
                    //this.closeConnection();
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

    private boolean authenticateUser(Person person, String hashedPassword){
        boolean present = this.verifyIDInDatabase(person.getIdNumber());
        BCrypt.Result result = BCrypt.verifyer().verify(person.getPassword().toCharArray(),hashedPassword);

        if (present == true && result.verified){
            return true;
        }
        return false;
    }

    private  String getHashedPasswordFromDatabase(long id){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Person person = (Person) session.get(Student.class,id);
        transaction.commit();
        session.close();

        return person.getPassword();
    }

    private boolean verifyIDInDatabase(long id){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Student student = (Student) session.createQuery("FROM Student WHERE idNumber = :id",Student.class)
                .setParameter("id", id)
                .uniqueResult();
        transaction.commit();
        session.close();

        if(student!= null){
            return true;
        }else{
            return false;
        }
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
