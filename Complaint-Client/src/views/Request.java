package views;

import models.Category;
import models.Complaint;
import models.Query;
import models.Student;

import javax.swing.*;
import java.awt.*;


public class Request extends JInternalFrame {
    private JLabel title;
    private JLabel typeQueryOrComplaintLabel;
    private JLabel categoryLabel;
    private JLabel queryOrComplaintDetailsLabel;
    private JPanel titlePanel, typeQueryOrComplaintPanel, categoryPanel, queryOrComplaintDetailsPanel, submitBtnPanel;
    private String QueryOrComplaint []= {"Query", "Complaint"};
    private JComboBox typeQueryOrComplaintComboBox;
    private JComboBox categoryNameComboBox;
    private String categoryNames [] = {String.valueOf(Category.CategoryEnum.MISSING_GRADES), String.valueOf(Category.CategoryEnum.INCORRECT_ACADEMIC_RECORD), String.valueOf(Category.CategoryEnum.NO_TIMETABLE), String.valueOf(Category.CategoryEnum.BARRED_FROM_EXAMS), String.valueOf(Category.CategoryEnum.NO_FINANCIAL_STATUS_UPDATE),String.valueOf(Category.CategoryEnum.STAFF_MISCONDUCT)};
    private JTextArea queryOrComplaintDetailsTextArea;
    private JButton submitBtn;


    public Request(Query query, Complaint complaint, Student student, String action) {
        super("Create Query Or Complaint", true, true, true, true);
        this.initializeComponents();
        this.addComponentsToPanels();
        this.addComponentsToWindow();
        this.setWindowProperties();
    }


    private void initializeComponents(){
        title = new JLabel("Create Complaint Or Query: ");
        typeQueryOrComplaintLabel = new JLabel("Query Or Complaint: ");
        categoryLabel = new JLabel("Category: ");
        queryOrComplaintDetailsLabel = new JLabel("Query Or Complaint Details: ");
        typeQueryOrComplaintComboBox = new JComboBox(QueryOrComplaint);
        categoryNameComboBox = new JComboBox(categoryNames);
        queryOrComplaintDetailsTextArea = new JTextArea(10, 10);
        submitBtn = new JButton("Submit");
        submitBtn.setSize(100, 50);



    }
    private void addComponentsToPanels(){
        titlePanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        titlePanel.setSize(new Dimension(900, 50));
        titlePanel.add(title);

        typeQueryOrComplaintPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        typeQueryOrComplaintLabel.setSize(new Dimension(200, 50));
        typeQueryOrComplaintComboBox.setSize(new Dimension(200, 50));
        typeQueryOrComplaintPanel.add(typeQueryOrComplaintLabel);
        typeQueryOrComplaintPanel.add(typeQueryOrComplaintComboBox);

        categoryPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        categoryLabel.setSize(new Dimension(200, 50));
        categoryNameComboBox.setSize(new Dimension(200, 50));
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryNameComboBox);

        queryOrComplaintDetailsPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        queryOrComplaintDetailsPanel.setSize(250,250);
        queryOrComplaintDetailsTextArea.setSize(250,250);
        queryOrComplaintDetailsPanel.add(queryOrComplaintDetailsLabel);
        queryOrComplaintDetailsPanel.add(queryOrComplaintDetailsTextArea);

        submitBtnPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        submitBtnPanel.setSize(new Dimension(900, 50));
        submitBtnPanel.add(submitBtn);

    }
    private void addComponentsToWindow() {
       this.add(titlePanel);
       this.add(typeQueryOrComplaintPanel);
       this.add(categoryPanel);
       this.add(queryOrComplaintDetailsPanel);
       this.add(submitBtnPanel);
    }

    private void setWindowProperties() {
        this.setTitle("Create Complaint Or Query");
        this.setSize(900, 600);
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setResizable(false);
        this.setClosable(true);
        this.setVisible(true);
    }





}
