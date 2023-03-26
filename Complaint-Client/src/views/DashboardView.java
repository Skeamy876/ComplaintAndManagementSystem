package views;

import controller.Client;
import models.Complaint;
import models.Query;
import models.Student;
import views.internatViews.QueryComplaintFrameView;
import views.internatViews.studentQueriesComplaintsView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DashboardView extends JFrame{
    private JDesktopPane desktop;
    private JMenu accountDetails ;
    private JMenuBar menuBar;
    private JButton createQueryOrComplaintBtn;
    private JButton viewQueryOrComplaintBtn;
    private final Client client;

    public DashboardView(Client client){
        this.client = client;
        this.initializeComponents();
        this.addMenusToMenuBar();
        this.addComponentsToDesktopFrame();
        this.addComponentsToWindow();
        this.setWindowProperties();
        this.registerActions();
    }

    private void initializeComponents(){
        this.setTitle("Complaint-Query Management System");
        desktop= new JDesktopPane();
        menuBar = new JMenuBar();
        accountDetails = new JMenu("Account");
        createQueryOrComplaintBtn = new JButton("Create Query Or Complaint");
        viewQueryOrComplaintBtn = new JButton("View Query or Complaint");


        createQueryOrComplaintBtn.setBounds(0,0,300,80);

        viewQueryOrComplaintBtn.setBounds(0,100,300,80);

    }

    private void addMenusToMenuBar(){
        menuBar.add(accountDetails);
    }
    private void addComponentsToDesktopFrame(){
        desktop.add(menuBar);
        desktop.add(createQueryOrComplaintBtn);
        desktop.add(viewQueryOrComplaintBtn);
    }

    private void addComponentsToWindow(){
        this.add(desktop);
    }

    private void registerActions(){
            createQueryOrComplaintBtn.addActionListener(e -> {
            desktop.add(new QueryComplaintFrameView(client));
        });

            viewQueryOrComplaintBtn.addActionListener(e -> {
                List<Query> queries;
                List<Complaint> complaints;
                ObjectInputStream objIs = client.getObjIs();
                ObjectOutputStream objOs = client.getObjOs();
                Student student = client.getStudent();
                String response = " ";
                try {
                    objOs.writeObject("AllStudentQueriesAndComplaints");
                    objOs.writeObject(student.getIdNumber());
                    queries = (ArrayList<Query>) objIs.readObject();
                    complaints = (ArrayList<Complaint>) objIs.readObject();
                    response = (String) objIs.readObject();
                    if(response.equals("successful")){
                        System.out.println("Queries received successfully");
                    }
                    else{
                        System.out.println("Queries not received successfully");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                desktop.add(new studentQueriesComplaintsView(queries,complaints));
            });
    }

    private void setWindowProperties(){
        this.setJMenuBar(menuBar);
        this.setSize(1020,700);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}
