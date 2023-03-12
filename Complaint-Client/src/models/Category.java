package models;


public class Category {

    private long Id;

    private CategoryEnum categoryName;

    public Category(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    public Category() {
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

}
