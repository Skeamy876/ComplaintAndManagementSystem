package views.internatViews;

import controller.Client;
import models.Student;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class QueryComplaintAndStudent extends JInternalFrame {
    private JPanel titlePanel, mainInnerPanel, statusPanel;
    private JLabel title,detailsOfQueryComplaint,status;
    private JTextArea studentDetails;
    private JTextField responseTextField;
    private JButton submitBtn,closeBtn;
    private Student student;
    private Object[] selectedData;
    private Client client;
    public QueryComplaintAndStudent(Object[] selectedData, Client client) {
        super("Query/Complaint Details",true,true,true,true);
        this.client = client;
        this.selectedData = selectedData;
        this.initializeComponents();
        this.addComponentsToPanel();
        this.setLayoutForPanel();
        this.addPanelsToWindow();
        this.setWindowProperties();
        this.loadStudentDetails();
        this.addResponse();
        this.closeQueryComplaint();
    }
    private void initializeComponents() {
        title = new JLabel(selectedData[0].toString());
        detailsOfQueryComplaint = new JLabel(selectedData[1].toString());
        status = new JLabel(selectedData[3].toString());
        titlePanel = new JPanel();
        mainInnerPanel = new JPanel();
        statusPanel = new JPanel();
        responseTextField = new JTextField(5);
        studentDetails = new JTextArea(5,5);
        studentDetails.setEditable(false);
        submitBtn = new JButton("Submit");
        submitBtn.setBackground(Color.GREEN);
        submitBtn.setSize(10,10);
        closeBtn = new JButton("Close Query/Complaint");
        closeBtn.setBackground(Color.RED);
        closeBtn.setSize(10,10);
    }
    private void addComponentsToPanel() {
        titlePanel.add(title);
        mainInnerPanel.add(detailsOfQueryComplaint);
        mainInnerPanel.add(studentDetails);
        mainInnerPanel.add(responseTextField);
        mainInnerPanel.add(submitBtn);
        mainInnerPanel.add(closeBtn);
        statusPanel.add(status);
    }
    private void setLayoutForPanel() {
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainInnerPanel.setLayout(new GridLayout(2,1));
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    private void addPanelsToWindow() {
        this.setLayout(new BorderLayout());
        this.add(titlePanel,BorderLayout.NORTH);
        this.add(mainInnerPanel,BorderLayout.CENTER);
        this.add(statusPanel,BorderLayout.SOUTH);
    }
    private void loadStudentDetails() {
        ObjectInputStream objIs = client.getObjIs();
        ObjectOutputStream objOs = client.getObjOs();

        try{
            objOs.writeObject("viewStudentDetails");
            objOs.writeObject(selectedData[4]);
            student = (Student) objIs.readObject();
            String response = (String) objIs.readObject();
            if (response.equals("successful")) {
                System.out.println("Success");
                studentDetails.setText(student.toString());
            } else {
                System.out.println("Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setWindowProperties() {
        this.setSize(500,500);
        this.setVisible(true);
    }

    private void addResponse(){
        submitBtn.addActionListener(e -> {
            ObjectInputStream objIs = client.getObjIs();
            ObjectOutputStream objOs = client.getObjOs();
            try{
                objOs.writeObject("addResponsesForQuery/Complaint");
                objOs.writeObject(client.getAdvisor().getIdNumber());
                objOs.writeObject(selectedData[4]);
                objOs.writeObject(responseTextField.getText().trim());
                String response = (String) objIs.readObject();
                if (response.equals("successful")) {
                    System.out.println("Success");
                    JOptionPane.showMessageDialog(this,"Response added successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Failed");
                    JOptionPane.showMessageDialog(this,"Failed to add response","Failed",JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void closeQueryComplaint(){
        closeBtn.addActionListener(e -> {
            ObjectInputStream objIs = client.getObjIs();
            ObjectOutputStream objOs = client.getObjOs();
            try{
                objOs.writeObject("closeQuery/Complaint");
                objOs.writeObject(selectedData[4]);
                String response = (String) objIs.readObject();
                if (response.equals("successful")) {
                    System.out.println("Success");
                    JOptionPane.showMessageDialog(this,"Query/Complaint closed successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Failed");
                    JOptionPane.showMessageDialog(this,"Failed to close Query/Complaint","Failed",JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}
