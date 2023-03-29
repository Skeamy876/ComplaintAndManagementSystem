package views.internatViews;

import controller.Client;
import models.Response;

import javax.swing.*;
import java.awt.*;

public class QueryComplaintView extends JFrame {
    private JPanel titlePanel, mainInnerPanel, submitBtnPanel, statusPanel;
    private JLabel title,detailsOfQueryComplaint,status;
    private Object [] tableRecord;
    private Client client;


    public QueryComplaintView(Object [] tableRecord, Client client){
        this.client = client;
        this.tableRecord = tableRecord;
        this.initializeComponents();
        this.addComponenetsToPanel();
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
        titlePanel = new JPanel();
        mainInnerPanel = new JPanel();
        statusPanel = new JPanel();
    }

    private void addComponenetsToPanel() {
        titlePanel.add(title);
        mainInnerPanel.add(detailsOfQueryComplaint);
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

    private void setWindowProperties() {
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }



}
