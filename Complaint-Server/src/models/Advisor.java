package models;

import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "advisors")
public class Advisor extends Person implements Serializable {
    public Advisor(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public Advisor() {
        super();
    }


}
