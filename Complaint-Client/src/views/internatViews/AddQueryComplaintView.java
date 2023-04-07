package views.internatViews;

import controller.Client;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import static java.awt.Font.DIALOG;


public class AddQueryComplaintView extends JInternalFrame {
    private JLabel title;
    private JLabel typeQueryOrComplaintLabel;
    private JLabel categoryLabel;
    private JLabel queryOrComplaintDetailsLabel;
    private JPanel titlePanel, mainInnerPanel, submitBtnPanel;
    private String QueryOrComplaint []= {"Query", "Complaint"};
    private JComboBox typeQueryOrComplaintComboBox;
    private JComboBox categoryNameComboBox;
    private final String categoryNames [] = {String.valueOf(Category.MISSING_GRADES), String.valueOf(Category.INCORRECT_ACADEMIC_RECORD), String.valueOf(Category.NO_TIMETABLE), String.valueOf(Category.BARRED_FROM_EXAMS), String.valueOf(Category.NO_FINANCIAL_STATUS_UPDATE),String.valueOf(Category.STAFF_MISCONDUCT)};
    private JTextArea queryOrComplaintDetailsTextArea;
    private JButton submitBtn;
    private final Client client;


    public AddQueryComplaintView(Client client) {
        super("Create Query Or Complaint", true, true, true, true);
        this.client = client;
        this.initializeComponents();
        this.addComponentsToPanels();
        this.addComponentsToWindow();
        this.registerActions();
        this.setWindowProperties();
    }


    private void initializeComponents(){
        title = new JLabel("New Query Or Complaint");
        Font font = new Font( DIALOG, Font.PLAIN, 15);
        typeQueryOrComplaintLabel = new JLabel("Query Or Complaint ");
        categoryLabel = new JLabel("Category ");
        queryOrComplaintDetailsLabel = new JLabel("Query Or Complaint Details ");
        typeQueryOrComplaintLabel.setFont(font);
        categoryLabel.setFont(font);
        queryOrComplaintDetailsLabel.setFont(font);
        typeQueryOrComplaintComboBox = new JComboBox(QueryOrComplaint);
        categoryNameComboBox = new JComboBox(categoryNames);
        queryOrComplaintDetailsTextArea = new JTextArea(10, 10);
        submitBtn = new JButton("Submit");



    }
    private void addComponentsToPanels(){
        this.setLayout(new BorderLayout());
        titlePanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        titlePanel.setSize(200, 100);
        Font font = new Font("Verdana", Font.BOLD, 25);
        title.setFont(font);
        titlePanel.add(title);

        mainInnerPanel = new JPanel( new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 50, 20, 50);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.ipadx = 0;
        gbc.ipady = 0;

        mainInnerPanel.add(typeQueryOrComplaintLabel, gbc);
        mainInnerPanel.add(typeQueryOrComplaintComboBox, gbc);

        gbc.gridy = 1;
        mainInnerPanel.add(categoryLabel, gbc);
        mainInnerPanel.add(categoryNameComboBox, gbc);

        gbc.gridy = 2;
        mainInnerPanel.add(queryOrComplaintDetailsLabel, gbc);
        mainInnerPanel.add(queryOrComplaintDetailsTextArea, gbc);

        submitBtnPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        submitBtnPanel.setPreferredSize(new Dimension(400, 60));
        submitBtn.setPreferredSize(new Dimension(100, 40));
        submitBtnPanel.add(submitBtn);

    }
    private void addComponentsToWindow() {
         this.add(titlePanel, BorderLayout.NORTH);
         this.add(mainInnerPanel, BorderLayout.CENTER);
         this.add(submitBtnPanel, BorderLayout.SOUTH);
    }

    private void registerActions() {
        submitBtn.addActionListener(e -> {
                String queryOrComplaint = (String) typeQueryOrComplaintComboBox.getSelectedItem();
                String category = (String) categoryNameComboBox.getSelectedItem();
                String queryOrComplaintDetails = queryOrComplaintDetailsTextArea.getText();
                ObjectInputStream objIs = client.getObjIs();
                ObjectOutputStream objOs = client.getObjOs();
                Student student = client.getStudent();

                if(queryOrComplaint.equals("Query")){
                    Query query = new Query();
                    query.setCategory(category);
                    query.setQueryDetail(queryOrComplaintDetails);
                    query.setQueryDate(new Timestamp(System.currentTimeMillis()));
                    query.setStatus(String.valueOf(Status.OPEN));

                    try {
                        client.sendRequest("Add Query");
                        student.removeQuery(query);
                        student.addQuery(query);
                        objOs.writeObject(student);

                        for (Query q: student.getQueries()){
                            System.out.println(q.getCategory());
                        }
                        String response = (String) objIs.readObject();
                        if (response.equals("successful")){
                            JOptionPane.showMessageDialog(this, "Query Submitted Successfully","Query Submit Status",JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(this, "Query Submission Failed","Query Submit Status",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                       throw new RuntimeException(ex);
                    }
                }else if(queryOrComplaint.equals("Complaint")){
                    Complaint complaint = new Complaint();
                    complaint.setCategory(category);
                    complaint.setComplaintDetail(queryOrComplaintDetails);
                    complaint.setComplaintDate(new Timestamp(System.currentTimeMillis()));
                    complaint.setStatus(String.valueOf(Status.OPEN));

                    try {
                        client.sendRequest("Add Complaint");
                        student.removeComplaint(complaint);
                        student.addComplaint(complaint);
                        objOs.writeObject(student);
                        String response = (String) objIs.readObject();
                        if (response.equals("successful")){
                            JOptionPane.showMessageDialog(this, "Complaint Submitted Successfully","Query Submit Status",JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(this, "Complaint Submission Failed","Query Submit Status",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        });
    }

    private void setWindowProperties() {
        this.setTitle("Create Complaint Or Query");
        this.setSize(900, 600);
        this.setResizable(false);
        this.setClosable(true);
        this.setVisible(true);
    }






}
