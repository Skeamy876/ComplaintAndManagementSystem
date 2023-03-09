package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "advisor")
@Table(name = "advisor")
public class Advisor extends Person implements Serializable {
    public Advisor(long idNumber, String firstName, String lastName, long phoneNumber, String email) {
        super(idNumber, firstName, lastName, phoneNumber, email);
    }
}
