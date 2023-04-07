package views.internatViews;

import controller.Client;
import models.Complaint;
import models.ComplaintTableModel;
import models.Query;
import models.QueryTableModel;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ViewAllComplaintsAndQueries extends JInternalFrame{
    private JTable tableQuery, tableComplaint;
    private JScrollPane scrollPaneQuery, scrollPaneComplaint;
    private List<Query> queries;
    private List<Complaint> complaints;
    private Client client;
    public ViewAllComplaintsAndQueries(Client client) {
        super("All Queries and Complaints",true,true,true,true);
        this.client = client;
        try{
            ObjectOutputStream objOs = client.getObjOs();
            ObjectInputStream objIs = client.getObjIs();
            objOs.writeObject("allQueries/Complaints");
            queries = (List<Query>) objIs.readObject();
            complaints = (List<Complaint>) objIs.readObject();
            String response = (String) objIs.readObject();

            if (response.equals("successful")) {
                System.out.println("Success");
            } else {
                System.out.println("Failed");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }

        this.queries = queries;
        QueryTableModel queryTableModel = new QueryTableModel(queries);
        this.tableQuery = new JTable(queryTableModel);
        tableQuery.setCellSelectionEnabled(true);

        this.complaints = complaints;
        ComplaintTableModel complaintTableModel = new ComplaintTableModel(complaints);
        this.tableComplaint = new JTable(complaintTableModel);
        tableComplaint.setCellSelectionEnabled(true);

        tableQuery.setBounds(0,30,450,50);
        tableComplaint.setBounds(0,100,450,50);

        this.setSize(500,700);
        this.setVisible(true);
        this.add(tableQuery);
        this.add(tableComplaint);

    }
}
