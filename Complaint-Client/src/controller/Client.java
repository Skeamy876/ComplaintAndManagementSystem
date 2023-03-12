package controller;

import models.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static ObjectInputStream objIs;
    private static ObjectOutputStream objOs;
    private Socket connectionSocket;

    private String action = "";


    public Client(Person person) {
        this.createConnection();
        this.configureStreams();

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
}
