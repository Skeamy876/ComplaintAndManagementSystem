package views.internatViews;

import controller.Client;
import models.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class QueryComplaintView extends JInternalFrame {
    private JPanel titlePanel, mainInnerPanel, submitBtnPanel, statusPanel;
    private JLabel title,detailsOfQueryComplaint,status, id;
    private JTextArea responseTextArea;
    List<Response> responses;
    private Object [] tableRecord;
    private Client client;


    public QueryComplaintView(Object [] tableRecord, Client client){
        super("Query/Complaint Details",true,true,true,true);
        this.client = client;
        this.tableRecord = tableRecord;
        this.initializeComponents();
        this.addComponentsToPanel();
        this.setLayoutForPanel();
        this.addPanelsToWindow();
        this.setWindowProperties();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    private void initializeComponents() {
        title = new JLabel(tableRecord[0].toString());
        detailsOfQueryComplaint = new JLabel(tableRecord[1].toString());
        status = new JLabel(tableRecord[3].toString());
        id = new JLabel(tableRecord[4].toString());
        responseTextArea = new JTextArea(10,20);
        responseTextArea.setEditable(false);
        titlePanel = new JPanel();
        mainInnerPanel = new JPanel();
        statusPanel = new JPanel();
    }

    private void addComponentsToPanel() {
        titlePanel.add(title);
        mainInnerPanel.add(detailsOfQueryComplaint);
        mainInnerPanel.add(responseTextArea);
        statusPanel.add(status);
        statusPanel.add(id);
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

    private void setWindowProperties() {
        this.setSize(400,500);
        this.setVisible(true);
    }

    private void requestActions(){
        ObjectInputStream objIs = client.getObjIs();
        ObjectOutputStream objOs = client.getObjOs();

        try{
            objOs.writeObject("getResponsesForQuery/Complaint");
            objOs.writeObject(id.getText().trim());
            responses = (List<Response>) objIs.readObject();
            String response = (String) objIs.readObject();

            if (response.equals("successful")) {
                for (Response response1 : responses) {
                    responseTextArea.append(response1.toString());
                }
                System.out.println("Responses received");
            }
            else{
                System.out.println("Responses not received");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
