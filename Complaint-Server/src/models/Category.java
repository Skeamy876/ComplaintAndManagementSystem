package models;

import factories.SessionBuilderFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;

@Entity(name = "categories")
@Table(name = "categories")
public class Category {
    @Id
    private long Id;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private CategoryEnum categoryName;

    public Category(long id, CategoryEnum categoryName) {
        Id = id;
        this.categoryName = categoryName;
    }

    public enum CategoryEnum{
        MISSING_GRADES,
        NO_FINANCIAL_STATUS_UPDATE,
        BARRED_FROM_EXAMS,
        INCORRECT_ACADEMIC_RECORD,
        NO_TIMETABLE,
        STAFF_MISCONDUCT
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public CategoryEnum getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }
    public void CreateCategory(){
        Session session = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(this);
        transaction.commit();
        session.close();
    }

    public void deleteComplaint(){
        Session session  = SessionBuilderFactory
                .getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.getTransaction();
        Complaint Complaint = (Complaint) session.get(Complaint.class,this.Id);
        session.delete(Complaint);
        transaction.commit();
        session.close();
    }
}
