package controller;

import models.Student;

public class DriverTest {
    public static void main(String[] args) {
        //authentication would be called before client

        Student student = new Student();
        new Client(student);
    }
}
