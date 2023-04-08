package views.internatViews;

import controller.Client;
import models.Complaint;
import models.ComplaintTableModel;
import models.Query;
import models.QueryTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class QueriesComplaintsTableView extends JInternalFrame  {
    private JTable tableQuery, tableComplaint;
    private JScrollPane scrollPaneQuery, scrollPaneComplaint;
    private List<Query> queries;
    private List<Complaint> complaints;
    private Client client;

    public QueriesComplaintsTableView(List<Query> queries , List<Complaint> complaints) {
        super("Queries and Complaints",true,true,true,true);
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



        this.setLayout(new BorderLayout());
        this.add(scrollPaneQuery, BorderLayout.NORTH);
        this.add(scrollPaneComplaint, BorderLayout.CENTER);
        this.setSize(500,700);
        this.setVisible(true);



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
            if (this.getDesktopPane() != null){
                this.getDesktopPane().add(new QueryComplaintView(selectedData,client));
            }
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

            if (this.getDesktopPane() != null){
                this.getDesktopPane().add(new QueryComplaintView(selectedData,client));
            }
            this.dispose();
        });
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}

