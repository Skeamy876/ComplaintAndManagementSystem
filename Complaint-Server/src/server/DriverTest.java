package server;

import domain.Category;
import domain.Complaint;
import domain.Query;
import domain.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

public class DriverTest {
    private static final Logger  logger = LogManager.getLogger(DriverTest.class);

    public static void main(String[] args) {
//        Student student = new Student();
//        student.setFirstName("Jack");
//        student.setLastName("Daniels");
//        student.setPhoneNumber(9875643);
//        student.setEmail("jackedaniels@hotmail.com");
//        student.setPassword("iloveschool");
//        student.createStudent(student);
//
//        Category category = new Category(Category.CategoryEnum.MISSING_GRADES);
//
//        long now = System.currentTimeMillis();
//        Timestamp time = new Timestamp(now);
//
//        Query queryTest = new Query();
//        queryTest.setCategory(category);
//        queryTest.setQueryDetail("No grades for Advanced Programming");
//        queryTest.setStudent(student);
//        queryTest.setQueryDate(time);
//        queryTest.setStatus(Complaint.Status.OPEN);
//        queryTest.createQuery();
        new Server();



    }
}
