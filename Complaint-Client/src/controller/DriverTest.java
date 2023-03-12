package controller;

import models.Student;

public class DriverTest {
    public static void main(String[] args) {
        Student student = new Student();
        student.setFirstName("Jack");
        student.setLastName("Daniels");
        student.setPhoneNumber(9875643);
        student.setEmail("jackedaniels@hotmail.com");
        student.setPassword("iloveschool");
        new Client(student);
    }
}
