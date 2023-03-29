package views.internatViews;

import controller.Client;
import models.Complaint;
import models.ComplaintTableModel;
import models.Query;
import models.QueryTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class StudentQueriesComplaintsTableView extends JInternalFrame  {
    private JTable tableQuery, tableComplaint;
    private JScrollPane scrollPaneQuery, scrollPaneComplaint;
    private List<Query> queries;
    private List<Complaint> complaints;
    private Client client;

    public StudentQueriesComplaintsTableView(List<Query> queries , List<Complaint> complaints) {
        super("Queries and Complaints",true,true,true,true);
        this.queries = queries;
        QueryTableModel queryTableModel = new QueryTableModel(queries);
        this.tableQuery = new JTable(queryTableModel);
        tableQuery.setCellSelectionEnabled(true);


        this.complaints = complaints;
        ComplaintTableModel complaintTableModel = new ComplaintTableModel(complaints);
        this.tableComplaint = new JTable(complaintTableModel);
        tableComplaint.setCellSelectionEnabled(true);




        scrollPaneQuery = new JScrollPane(tableQuery);
        scrollPaneQuery.setBounds(0,0,500,100);
        scrollPaneQuery.add(tableComplaint);

//        scrollPaneComplaint = new JScrollPane(tableComplaint);
//        scrollPaneComplaint.add(new JLabel("Complaints"));
//        scrollPaneComplaint.setBounds(0,100,500,100);

        this.setSize(500,700);
        this.setVisible(true);
        this.add(scrollPaneQuery);
       // this.add(scrollPaneComplaint);


        ListSelectionModel cellSelectionModel = tableQuery.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

cellSelectionModel.addListSelectionListener(e -> {
            int rowIndex = tableQuery.getSelectedRow();
            TableModel model = tableQuery.getModel();
            Object[] selectedData = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                selectedData[i] = model.getValueAt(rowIndex, i);
            }
            System.out.println("Selected: " + Arrays.toString(selectedData));
            new QueryComplaintView(selectedData,client);
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

