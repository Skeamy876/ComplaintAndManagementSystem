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
import java.util.Arrays;
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
        scrollPaneQuery = new JScrollPane(tableQuery);

        this.complaints = complaints;
        ComplaintTableModel complaintTableModel = new ComplaintTableModel(complaints);
        this.tableComplaint = new JTable(complaintTableModel);
        tableComplaint.setCellSelectionEnabled(true);
        scrollPaneComplaint = new JScrollPane(tableComplaint);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPaneQuery,scrollPaneComplaint);
        splitPane.setResizeWeight(0.5);
        this.add(splitPane);
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
                this.getDesktopPane().add(new QueryComplaintView(selectedData,client));
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
                this.getDesktopPane().add(new QueryComplaintView(selectedData,client));
            }
            this.dispose();
        });

    }
}
