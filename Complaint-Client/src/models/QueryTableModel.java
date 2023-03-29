package models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QueryTableModel extends AbstractTableModel {
    private List<Query> queries;
    private String [] columns = {"Category", "Details", "Date", "Status"};

    public QueryTableModel(List<Query> queries) {
        this.queries = queries;
    }

    @Override
    public int getRowCount() {
        return queries.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Query query = queries.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return query.getCategory();
            case 1:
                return query.getQueryDetail();
            case 2:
                return query.getQueryDate();
            case 3:
                return query.getStatus();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {

        return columns[column];
    }
}

