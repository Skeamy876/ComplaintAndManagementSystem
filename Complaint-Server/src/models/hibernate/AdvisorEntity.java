package models.hibernate;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Advisor")
@Table(name = "advisors")
public class AdvisorEntity extends PersonEntity implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueryEntity> queryEntity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComplaintEntity> complaintEntity;
    public AdvisorEntity(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public AdvisorEntity() {
        super();
    }

    public List<QueryEntity> getQueryEntity() {
        return queryEntity;
    }

    public void setQueryEntity(List<QueryEntity> queryEntity) {
        this.queryEntity = queryEntity;
    }

    public List<ComplaintEntity> getComplaintEntity() {
        return complaintEntity;
    }

    public void setComplaintEntity(List<ComplaintEntity> complaintEntity) {
        this.complaintEntity = complaintEntity;
    }

    public void addQuery(QueryEntity queryEntity){
        this.queryEntity.add(queryEntity);
    }

    public void addComplaint(ComplaintEntity complaintEntity){
        this.complaintEntity.add(complaintEntity);
    }




}
