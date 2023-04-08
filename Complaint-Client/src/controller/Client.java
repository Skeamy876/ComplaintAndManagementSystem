package controller;

import models.Advisor;
import models.Person;
import models.Student;
import models.Supervisor;
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
    private Dashboard dashboardView;
    private Person person;
    private Student student;
    private Advisor advisor;
    private Supervisor supervisor;

        public Client(Person person) {
                this.person = person;
                this.createConnection();
                this.configureStreams();
        }
        public Client(Student student) {
                this.student = student;
                this.createConnection();
                this.configureStreams();
        }

        public Client(Supervisor supervisor) {
                this.supervisor = supervisor;
                this.createConnection();
                this.configureStreams();
        }
        public Client(Advisor advisor) {
                this.advisor = advisor;
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
                this.sendRequestStartRequest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    private void sendRequestStartRequest(){
        try {
            if (this.person != null) {
                objOs.writeObject(person.getIdNumber());
                objOs.writeObject(person.getFirstName());
            } else if (this.student != null) {
                objOs.writeObject(student.getIdNumber());
                objOs.writeObject(student.getFirstName());
            } else if (this.advisor != null) {
                objOs.writeObject(advisor.getIdNumber());
                objOs.writeObject(advisor.getFirstName());
            } else if (this.supervisor != null) {
                objOs.writeObject(supervisor.getIdNumber());
                objOs.writeObject(supervisor.getFirstName());
            }
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = Client.this.person;
    }


    public ObjectInputStream getObjIs() {
        return objIs;
    }

    public ObjectOutputStream getObjOs() {
        return objOs;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
