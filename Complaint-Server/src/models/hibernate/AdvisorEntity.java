package models.hibernate;



import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Advisor")
@Table(name = "advisors")
public class AdvisorEntity extends PersonEntity implements Serializable {
    public AdvisorEntity(String firstName, String lastName, long phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public AdvisorEntity() {
        super();
    }


}
