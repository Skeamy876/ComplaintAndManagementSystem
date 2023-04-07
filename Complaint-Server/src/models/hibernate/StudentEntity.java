package models.hibernate;



import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Student")
@Table(name = "students")
public class StudentEntity extends PersonEntity implements Serializable {
    @Serial
    private static  final long serialVersionUID = 443756519803593097L;
    @OneToMany(mappedBy = "studentEntity",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<ComplaintEntity> complaintEntities = new ArrayList<ComplaintEntity>();

    @OneToMany(mappedBy = "studentEntity",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueryEntity> queries = new ArrayList<QueryEntity>();

    public StudentEntity(){
        super();
    }


    public StudentEntity(String firstName, String lastName, long phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
    }

    public List<ComplaintEntity> getComplaints() {
        return complaintEntities;
    }

    public void setComplaints(List<ComplaintEntity> complaintEntities) {
        this.complaintEntities = complaintEntities;
    }

    public List<QueryEntity> getQueries() {
        return queries;
    }

    public void setQueries(List<QueryEntity> queries) {
        this.queries = queries;
    }

    public void addQuery(QueryEntity queryEntity) {
        this.queries.add(queryEntity);
        queryEntity.setStudent( this );
    }

    public void removeQuery(QueryEntity queryEntity) {
        this.queries.remove(queryEntity);
        queryEntity.setStudent( null );
    }

    public void addComplaint(ComplaintEntity compplaint) {
        complaintEntities.add( compplaint );
        compplaint.setStudent( this );
    }

    public void removeComplaint(ComplaintEntity compplaint) {
        complaintEntities.remove( compplaint );
        compplaint.setStudent( null );
    }


}
