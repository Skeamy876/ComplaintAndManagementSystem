package domain;

import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "advisor")
@Table(name = "advisor")
public class Advisor extends Person implements Serializable {
    public Advisor(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public Advisor() {
    }

    public void createAdvisor(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();


        Transaction transaction = session.beginTransaction();
        session.save(this);
        transaction.commit();
        session.close();
    }

    public void updateAdvisor(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Advisor advisor = (Advisor) session.get(Advisor.class,this.getIdNumber());
        advisor.setFirstName(this.getFirstName());
        advisor.setLastName(this.getLastName());
        advisor.setEmail(this.getEmail());
        session.update(advisor);
        transaction.commit();
        session.close();

    }

    public void deleteAdvisor(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Advisor advisor = (Advisor) session.get(Advisor.class,this.getIdNumber());
        session.delete(advisor);
        transaction.commit();
        session.close();
    }
}
