package server;


import models.Advisor;
import models.Student;
import models.Supervisor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.actions.AdvisorActions;
import server.actions.StudentActions;
import server.actions.SupervisorActions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;


public class Server {
    private ServerSocket serverSocket;
    private Socket conectionSocket;
    private int clientCount;
    private static final Logger logger = LogManager.getLogger(Server.class);

    public Server(){

//        Advisor advisor = new Advisor();
//        advisor.setFirstName("Sam");
//        advisor.setLastName("Denver");
//        advisor.setPassword("3");
//        advisor.setIdNumber(4);
//        AdvisorActions advisorActions = new AdvisorActions();
//        advisorActions.createAdvisor(advisor);
//
//        Supervisor supervisor = new Supervisor();
//        supervisor.setFirstName("Max");
//        supervisor.setLastName("Reacher");
//        supervisor.setPassword("3");
//        supervisor.setIdNumber(5);
//        SupervisorActions supervisorActions = new SupervisorActions();
//        supervisorActions.createSupervisor(supervisor);
//
//        Student student = new Student();
//        student.setFirstName("John");
//        student.setLastName("Doe");
//        student.setPassword("3");
//        student.setIdNumber(3);
//        StudentActions studentActions = new StudentActions();
//        studentActions.createStudent(student);

        try{
            serverSocket = new ServerSocket(8888);
            logger.info("Server Started, Time: "+ LocalDate.now());
            while (true){
                conectionSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(conectionSocket);
                Thread thread = new Thread(client);
                thread.start();
                clientCount++;
                logger.info("Starting a thread for new client, Timme: "+LocalDate.now());
                logger.info("Client count: "+ clientCount);
            }
        } catch (IOException e) {
           e.printStackTrace();
           logger.warn("IO Exception occurred within this try catch block for server");
        }



    }

}
