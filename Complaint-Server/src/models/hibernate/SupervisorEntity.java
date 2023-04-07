package models.hibernate;



import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Supervisor")
@Table(name = "supervisors")
public class SupervisorEntity extends PersonEntity implements Serializable {
    @Serial
    private static  final long serialVersionUID = 1755490458986052803L;
   @Transient
    private List<QueryEntity> queries;
   @Transient
    private List<ComplaintEntity> complaintEntities;
   @Transient
    private List <AdvisorEntity> advisorEntities;

    @Transient
    private List<StudentEntity> studentEntities;
    public SupervisorEntity(String firstName, String lastName, long phoneNumber, String email) {
        super( firstName, lastName, phoneNumber, email);
    }

    public SupervisorEntity() {
        super();
    }

    public List<QueryEntity> getQueries() {
        return queries;
    }

    public void setQueries(List<QueryEntity> queries) {
        this.queries = queries;
    }

    public List<ComplaintEntity> getComplaints() {
        return complaintEntities;
    }

    public void setComplaints(List<ComplaintEntity> complaintEntities) {
        this.complaintEntities = complaintEntities;
    }

    public List<AdvisorEntity> getAdvisors() {
        return advisorEntities;
    }

    public void setAdvisors(List<AdvisorEntity> advisorEntities) {
        this.advisorEntities = advisorEntities;
    }

    public List<StudentEntity> getStudents() {
        return studentEntities;
    }

    public void setStudents(List<StudentEntity> studentEntities) {
        this.studentEntities = studentEntities;
    }

}
