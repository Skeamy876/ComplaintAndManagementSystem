package controller;

import models.Student;
import views.Dashboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private  ObjectInputStream objIs;
    private  ObjectOutputStream objOs;
    private Socket connectionSocket;

    private String action = "";
    private Dashboard dashboard;
    private Student student;



    public Client(Student student) {
        this.createConnection();
        this.configureStreams();
        this.dashboard = new Dashboard(this);
        this.student = student;


    }



    private void createConnection() {
        try {
            connectionSocket = new Socket("12.0.0.1", 8888);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureStreams() {
        try {
            objIs = new ObjectInputStream(connectionSocket.getInputStream());
            objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        try {
            objOs.close();
            objIs.close();
            connectionSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ObjectInputStream getObjIs() {
        return objIs;
    }

    public void setObjIs(ObjectInputStream objIs) {
        this.objIs = objIs;
    }

    public ObjectOutputStream getObjOs() {
        return objOs;
    }

    public void setObjOs(ObjectOutputStream objOs) {
        this.objOs = objOs;
    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public void setConnectionSocket(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
