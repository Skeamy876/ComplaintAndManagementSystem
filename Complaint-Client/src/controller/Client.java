package controller;

import models.Student;
import views.DashboardView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private  ObjectInputStream objIs;
    private  ObjectOutputStream objOs;
    private Socket connectionSocket;

    private DashboardView dashboardView;
    private Student student;



    public Client(Student student) {
        this.student = student;
            this.createConnection();
            this.configureStreams();
        }



        private void createConnection() {
            try {
                connectionSocket = new Socket("127.0.0.1", 8888);
                System.out.println("Connected to server");

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

        public void sendRequest(String action){
            try {
                objOs.writeObject(action);
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


    public ObjectInputStream getObjIs() {
        return objIs;
    }

    public ObjectOutputStream getObjOs() {
        return objOs;
    }
}
