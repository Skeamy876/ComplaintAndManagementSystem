package server;

import at.favre.lib.crypto.bcrypt.BCrypt;
import models.*;
import factories.DbConnectorFactory;
import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private ServerSocket serverSocket;
    private Socket conectionSocket;


    public Server(){
        this.createConnection();
        this.waitForRequests();

    }



    private void createConnection() {
        try{
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureStreams(){
        try {
            objOs = new ObjectOutputStream(conectionSocket.getOutputStream());
            objIs = new ObjectInputStream(conectionSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void waitForRequests() {
        DbConnectorFactory.getDatabaseConnection();
        String action = " ";
        Query query = null;
        Person person = null;
        Student student = null;
        boolean flag = false;

        try {
            while (true){
                conectionSocket = serverSocket.accept();
                this.configureStreams();
                try {
                    action = (String) objIs.readObject();

                    //configure logging messages for all successful and fail actions in switch statement
                    switch (action){
                        case "Authenticate":
                            person = (Person) objIs.readObject();
                            String dbPassword = this.getHashedPasswordFromDatabase(person.getIdNumber());
                            flag = this.authenticateUser(person,dbPassword);
                            if (flag == true){
                                objOs.writeObject("login successful");
                                //Logging message here
                            }

                            break;
                        case "Add Query":
                             student = (Student) objIs.readObject();
                            student.setFirstName("Jack");
                            student.setLastName("Daniels");
                            student.setPhoneNumber(9875643);
                            student.setEmail("jackedaniels@hotmail.com");
                            student.setPassword("iloveschool");
                             Session session = SessionBuilderFactory
                                    .getSessionFactory()
                                    .getCurrentSession();

                            Transaction transaction = session.beginTransaction();
                            session.save(student);
                            transaction.commit();
                            session.close();
                            System.out.println(student);
                            objOs.writeObject("successful"); //should be change to something appropriate
                            break;
                        case "Add Complaint":
                            break;
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                this.closeConnection();
            }
        }catch (EOFException ex){
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private boolean authenticateUser(Person person,String hashedPassword){
        long dbId = this.getIdFromDatabase(person.getPassword());
        BCrypt.Result result = BCrypt.verifyer().verify(person.getPassword().toCharArray(),hashedPassword);

        if (dbId == person.getIdNumber() && result.verified){
            return true;
        }
        return false;
    }

    private  String getHashedPasswordFromDatabase(long id){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Student student = (Student) session.createNativeQuery("SELECT * FROM  utechcomplaintdb.students,utechcomplaintdb.supervisors, utechcomplaintdb.advisors WHERE idNumber ="+id);
        transaction.commit();
        session.close();

        return student.getPassword();
    }

    private long getIdFromDatabase(String password){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Person person = (Person) session.createNativeQuery("SELECT * FROM utechcomplaintdb.students,utechcomplaintdb.supervisors, utechcomplaintdb.advisors WHERE password ="+password);
        transaction.commit();
        session.close();

        return person.getIdNumber();
    }


    private void closeConnection(){
        try {
            objOs.close();
            objIs.close();
            conectionSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
