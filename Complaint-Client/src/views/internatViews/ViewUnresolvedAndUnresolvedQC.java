package views.internatViews;

import controller.Client;
import models.Complaint;
import models.ComplaintTableModel;
import models.Query;
import models.QueryTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewUnresolvedAndUnresolvedQC extends JInternalFrame{
    private JTable tableQuery, tableComplaint, tableResolvedQuery, tableResolvedComplaint;
    private JScrollPane scrollPaneQueryResolved, scrollPaneComplaintResolved, scrollPaneQueryUnresolved, scrollPaneComplaintUnresolved;
    private List<models.Query> queries = new ArrayList<>();
    private List<models.Complaint> complaints = new ArrayList<>();
    private List<Query> unResolvedQueries = new ArrayList<>();
    private List<Query> resolvedQueries = new ArrayList<>();
    private List<Complaint> unResolvedComplaints = new ArrayList<>();
    private List<Complaint> resolvedComplaints = new ArrayList<>();
    private Client client;
    public ViewUnresolvedAndUnresolvedQC(Client client) {
        super("Queries/Complaints Status",true,true,true,true);
        this.client = client;
        try{
            ObjectOutputStream objOs = client.getObjOs();
            ObjectInputStream objIs = client.getObjIs();
            objOs.writeObject("allQueries/Complaints");
            this.queries = (List<Query>) objIs.readObject();
            this.complaints = (List<Complaint>) objIs.readObject();
            String response = (String) objIs.readObject();
            if (response.equals("successful")) {
                System.out.println("Success");
            } else {
                System.out.println("Failed");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (ClassCastException e){
            System.err.println(e.getMessage());
        }


        for (Query query : queries) {
            if (query.getStatus().equals("OPEN")) {
                unResolvedQueries.add(query);
            } else {
                resolvedQueries.add(query);
            }
        }
        for (Complaint complaint : complaints) {
            if (complaint.getStatus().equals("OPEN")) {
                unResolvedComplaints.add(complaint);
            } else {
                resolvedComplaints.add(complaint);
            }
        }

        this.add(new JLabel("Unresolved Queries/Complaints"));
        QueryTableModel queryTableModel = new QueryTableModel(unResolvedQueries);
        this.tableQuery = new JTable(queryTableModel);
        tableQuery.setCellSelectionEnabled(true);
        ComplaintTableModel complaintTableModel = new ComplaintTableModel(unResolvedComplaints);
        this.tableComplaint = new JTable(complaintTableModel);
        tableComplaint.setCellSelectionEnabled(true);

        scrollPaneQueryUnresolved = new JScrollPane(tableQuery);
        scrollPaneComplaintUnresolved = new JScrollPane(tableComplaint);


        this.getContentPane().add(new JLabel("Resolved Queries/Complaints"));
        QueryTableModel queryTableModel2 = new QueryTableModel(resolvedQueries);
        this.tableResolvedQuery = new JTable(queryTableModel2);
        tableResolvedQuery.setCellSelectionEnabled(true);
        ComplaintTableModel complaintTableModel2 = new ComplaintTableModel(resolvedComplaints);
        this.tableResolvedComplaint = new JTable(complaintTableModel2);
        tableResolvedComplaint.setCellSelectionEnabled(true);
        scrollPaneQueryResolved = new JScrollPane(tableResolvedQuery);
        scrollPaneComplaintResolved = new JScrollPane(tableResolvedComplaint);

        this.setLayout(new BorderLayout());

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneQueryUnresolved, scrollPaneComplaintUnresolved);
        leftSplitPane.setResizeWeight(0.5);
        this.add(leftSplitPane, BorderLayout.WEST);

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneQueryResolved, scrollPaneComplaintResolved);
        rightSplitPane.setResizeWeight(0.5);
        this.add(rightSplitPane, BorderLayout.EAST);

        this.setSize(500,700);
        this.setVisible(true);



        ListSelectionModel listSelectionModel = tableQuery.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel listSelectionModel2 = tableComplaint.getSelectionModel();
        listSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listSelectionModel.addListSelectionListener(e -> {
            int selectedRow = tableQuery.getSelectedRow();
            TableModel tableModel = tableQuery.getModel();
            Object[] selectedData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                selectedData[i] = tableModel.getValueAt(selectedRow, i);
            }
            System.out.println("Selected: " + Arrays.toString(selectedData));
            if (this.getDesktopPane() != null) {
                this.getDesktopPane().add(new AssignStudentAdvisor(client, selectedData));
            }
            this.dispose();
        });

        listSelectionModel2.addListSelectionListener(e -> {
            int selectedRow = tableComplaint.getSelectedRow();
            TableModel tableModel = tableComplaint.getModel();
            Object[] selectedData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                selectedData[i] = tableModel.getValueAt(selectedRow, i);
            }
            System.out.println("Selected: " + Arrays.toString(selectedData));
            if (this.getDesktopPane() != null) {
                this.getDesktopPane().add(new AssignStudentAdvisor(client, selectedData));
            }
            this.dispose();
        });
    }
}
