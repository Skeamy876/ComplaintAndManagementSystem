package controller;

import models.Category;
import models.Complaint;
import models.Query;
import models.Student;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DriverTest {
    public static void main(String[] args) {
        Student student = new Student();
        student.createStudent(new Student(1001,"Jack","Daniels",9875643,"jackedaniels@hotmail.com"));

        Category category = new Category(1, Category.CategoryEnum.MISSING_GRADES);

        long now = System.currentTimeMillis();
        Timestamp time = new Timestamp(now);

        Query queryTest = new Query(231,category,"No grades for Advanced Programming",student,time, Complaint.Status.OPEN);
        queryTest.createQuery();
    }
}
