package views.internatViews;

import controller.Client;
import models.Complaint;
import models.Query;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SearchView extends JInternalFrame {
    private List<Query> queries;
    private List<Complaint> complaints;
    public SearchView(Client client, String s) {
        super("Search By Category" +" "+s, true, true, true, true);
        ObjectInputStream objIs = client.getObjIs();
        ObjectOutputStream objOs = client.getObjOs();

        try{
            objOs.writeObject("getComplaints/QueriesByCategory");
            objOs.writeObject(s);
            queries = (List<Query>) objIs.readObject();
            complaints = (List<Complaint>) objIs.readObject();
            String response = (String) objIs.readObject();
            objOs.flush();
            if (response.equals("success")) {
                System.out.println("Success");
            } else {
                System.out.println("Failure");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        queries.forEach(System.out::println);
        complaints.forEach(System.out::println);

        this.setVisible(true);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());

        QueriesComplaintsTableView queriesComplaintsTableView = new QueriesComplaintsTableView(queries,complaints);
        this.add(queriesComplaintsTableView);
    }
}
