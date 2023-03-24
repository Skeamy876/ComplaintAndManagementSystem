package server;


import models.Category;
import models.Complaint;
import models.Query;
import models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.actions.QueryActions;
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
        student.setIdNumber(34567);
        student.setFirstName("John");
        student.setLastName("Lennon");
        student.setPassword("iloveschool");

        StudentActions studentActions = new StudentActions();
        studentActions.createStudent(student);

        Category category = new Category();
        category.setCategoryName(Category.CategoryEnum.MISSING_GRADES);
        Query query = new Query();
        query.setQueryDetail("No grades for AOA");
        query.setStatus(Complaint.Status.OPEN);
        query.setCategory(category);
        query.setStudent(student);

        QueryActions queryActions = new QueryActions();
        queryActions.createQuery(query);
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
