package models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ComplaintTableModel extends AbstractTableModel {

    private List<Complaint> complaints;
    private String [] columns = {"Category", "Details", "Date", "Status","ComplaintId"};

    public ComplaintTableModel(List<Complaint> complaints) {
        this.complaints = complaints;
    }


    @Override
    public int getRowCount() {
        return complaints.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {


        Complaint complaint = complaints.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return complaint.getCategory();
            case 1:
                return complaint.getComplaintDetail();
            case 2:
                return complaint.getComplaintDate();
            case 3:
                return complaint.getStatus();
            case 4:
                return complaint.getComplaintId();
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        return columns[column];

    }


}
