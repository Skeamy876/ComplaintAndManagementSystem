package models.hibernate;


import at.favre.lib.crypto.bcrypt.BCrypt;
import models.Student;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@MappedSuperclass
public class PersonEntity implements Serializable {
    @Serial
    private static  final long serialVersionUID = 333148454057186020L;
    @Id
    private long idNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private long phoneNumber;
    @Column(name = "email_address")
    private String email;
    @Column(name = "password")
    private  String password;

    public PersonEntity(String firstName, String lastName, long phoneNumber, String email) {
        this.idNumber = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public PersonEntity(long idNumber, String firstName, String lastName, long phoneNumber, String email, String password) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public PersonEntity(String firstName, String lastName, long phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public PersonEntity() {

    }


    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String hashedPassword ="";
        hashedPassword = BCrypt.withDefaults().hashToString(12,password.toCharArray());
        this.password = hashedPassword;
    }

    public void setNormalPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return  "\n idNumber: " + idNumber +
                "\n firstName: " + firstName + ' ' +
                "\n lastName: " + lastName + ' ' +
                "\n phoneNumber: " + phoneNumber +
                "\n email: " + email + ' ' +
                "\n password: " + password;
    }
}
