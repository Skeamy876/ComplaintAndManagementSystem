package models;


import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "supervisors")
public class Supervisor extends Person implements Serializable {

    @Serial
    private static  final long serialVersionUID = 1755490458986052803L;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Query> queries;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Complaint> complaints;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List <Advisor> advisors;
    public Supervisor( String firstName, String lastName, long phoneNumber, String email) {
        super( firstName, lastName, phoneNumber, email);
    }

    public Supervisor() {
        super();
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }

    public void createSupervisor(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();


        Transaction transaction = session.beginTransaction();
        session.save(this);
        transaction.commit();
        session.close();
    }

    public void updateSupervisor(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Supervisor supervisor = (Supervisor) session.get(Supervisor.class,this.getIdNumber());
        supervisor.setFirstName(this.getFirstName());
        supervisor.setLastName(this.getLastName());
        supervisor.setEmail(this.getEmail());
        session.update(supervisor);
        transaction.commit();
        session.close();

    }

    public void assignComplaintToAdvisor(){
    //Look up best way to do this


    }
    public void assignQueryToAdvisor(){
        //Look up best way to do this

    }

    public void deleteSupervisor(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Supervisor supervisor = (Supervisor) session.get(Supervisor.class,this.getIdNumber());
        session.delete(supervisor);
        transaction.commit();
        session.close();
    }



}
