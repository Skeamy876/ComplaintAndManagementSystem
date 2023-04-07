package views.internatViews;

import controller.Client;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class QueryComplaintAndStudent extends JInternalFrame {
    private JPanel titlePanel, mainInnerPanel, submitBtnPanel, statusPanel;
    private JLabel title,detailsOfQueryComplaint,status;
    private JTextArea studentDetails;
    private Object[] selectedData;
    private Client client;
    public QueryComplaintAndStudent(Object[] selectedData, Client client) {
        super("Query/Complaint Details",true,true,true,true);
        this.client = client;
        this.selectedData = selectedData;
        this.initializeComponents();
        this.addComponenetsToPanel();
        this.setLayoutForPanel();
        this.addPanelsToWindow();
        this.setWindowProperties();
    }
    private void initializeComponents() {
        title = new JLabel(selectedData[0].toString());
        detailsOfQueryComplaint = new JLabel(selectedData[1].toString());
        status = new JLabel(selectedData[3].toString());
        titlePanel = new JPanel();
        mainInnerPanel = new JPanel();
        statusPanel = new JPanel();
        studentDetails = new JTextArea();
    }
    private void addComponenetsToPanel() {
        titlePanel.add(title);
        mainInnerPanel.add(detailsOfQueryComplaint);
        mainInnerPanel.add(studentDetails);
        statusPanel.add(status);
    }
    private void setLayoutForPanel() {
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainInnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
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
            client.sendRequest("loadStudentDetailsForAdvisor");
            objOs.writeObject(Long.parseLong((String) selectedData[4]));
            studentDetails.setText(objIs.readObject().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setWindowProperties() {
        this.setSize(500,500);
        this.setVisible(true);
    }

}
