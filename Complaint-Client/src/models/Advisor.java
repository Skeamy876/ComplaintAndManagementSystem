package models;

import java.io.Serializable;


public class Advisor extends Person implements Serializable {
    public Advisor(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public Advisor() {
        super();
    }

}
