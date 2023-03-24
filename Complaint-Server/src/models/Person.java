package models;


import at.favre.lib.crypto.bcrypt.BCrypt;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@MappedSuperclass
public abstract class  Person implements Serializable {
    @Serial
    private static  final long serialVersionUID = 333148454057186020L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Person(String firstName, String lastName, long phoneNumber, String email) {
        this.idNumber = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Person(String firstName, String lastName, long phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public Person() {

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


    @Override
    public String toString() {
        return "Person{" +
                "idNumber=" + idNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
