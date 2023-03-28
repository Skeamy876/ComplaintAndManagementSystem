package server.actions;

import factories.SessionBuilderFactory;
import models.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SupervisorActions {

    public void createStudent(Student student){
        String insertSQL = "INSERT INTO utechcomplaintdb.students (idNumber,email_address,first_name,last_name,password,phone_number)"
                +"VALUES('"+student.getIdNumber()+"','"+student.getEmail()+"','"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getPassword()+"','"+student.getPhoneNumber()+"');";

        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.createQuery(insertSQL,Student.class);
        session.close();
        transaction.commit();
    }

    public Student findStudent(long idNumber){
        String selectSql = "SELECT * FROM students WHERE idNumber ="+ idNumber;

        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Student student = (Student) session.createQuery(selectSql,Student.class);
        session.close();
        transaction.commit();
        return student;
    }

    public List<Student> getAllStudents(){
        String getAll ="SELECT idNumber,first_name,last_name,email_address, phone_number from utechcompalintdb.students";
        List<Student> students = new ArrayList<>();

        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();

        students = (List<Student>) session.createQuery (getAll,Student.class)
                .list();

        for (Student student: students){
            System.out.println(student);
        }

        return students;
    }




    public boolean deleteStudent(long idNumber){
        String deleteStudent = "DELETE FROM students WHERE student_ID ="+idNumber;
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.createQuery(deleteStudent,Student.class);
        transaction.commit();

        Transaction transactionfind = session.getTransaction();
        Student student = (Student) session.get(Student.class,idNumber);

        if (student != null){
            return false;
        }else {
            return true;
        }
    }
}
