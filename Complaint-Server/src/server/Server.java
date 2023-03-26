package server;


import models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.actions.StudentActions;

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

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setPassword("3");
        student.setIdNumber(3);

        StudentActions studentActions = new StudentActions();
        studentActions.createStudent(student);
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
