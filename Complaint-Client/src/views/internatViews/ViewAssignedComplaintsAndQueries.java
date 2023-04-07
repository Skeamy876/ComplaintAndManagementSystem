package views.internatViews;

import controller.Client;
import models.Complaint;
import models.ComplaintTableModel;
import models.Query;
import models.QueryTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class ViewAssignedComplaintsAndQueries extends JInternalFrame {
    private JTable tableQuery, tableComplaint;
    private List<Query> queries;
    private List<Complaint> complaints;
    private Client client;
    public ViewAssignedComplaintsAndQueries(Client client) {
        super("View Assigned Complaints and Queries", true, true, true, true);

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


        ListSelectionModel cellSelectionModelQuery = tableQuery.getSelectionModel();
        cellSelectionModelQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel cellSelectionModelComplaint = tableComplaint.getSelectionModel();
        cellSelectionModelComplaint.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModelQuery.addListSelectionListener(e -> {
            int rowIndex = tableQuery.getSelectedRow();
            TableModel model = tableQuery.getModel();
            Object[] selectedData = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                selectedData[i] = model.getValueAt(rowIndex, i);
            }
            System.out.println("Selected: " + Arrays.toString(selectedData));
            new QueryComplaintAndStudent(selectedData,client);
            this.dispose();
        });

        cellSelectionModelComplaint.addListSelectionListener(e -> {
            int rowIndex = tableComplaint.getSelectedRow();
            TableModel model = tableComplaint.getModel();
            Object[] selectedData = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                selectedData[i] = model.getValueAt(rowIndex, i);
            }
            System.out.println("Selected: " + Arrays.toString(selectedData));
            new QueryComplaintAndStudent(selectedData,client);
            this.dispose();
        });

        this.setSize(500,700);
        this.setVisible(true);
        this.add(tableQuery);
        this.add(tableComplaint);
        this.setSize(500, 500);
        this.setVisible(true);
    }
}
