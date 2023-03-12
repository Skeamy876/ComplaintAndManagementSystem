package views;

import models.Complaint;
import models.Query;
import models.Student;

import javax.swing.*;
import java.awt.*;


public class Dashboard extends JFrame{
    private JDesktopPane desktop;
    private JMenu accountDetails ;
    private JMenuBar menuBar;
    private JButton createQueryOrComplaintBtn;
    private JButton viewQueryOrComplaintBtn;

    public Dashboard(){
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
        accountDetails = new JMenu("Acoount");
        createQueryOrComplaintBtn = new JButton("Create Query Or Complaint");
        viewQueryOrComplaintBtn = new JButton("View Query or Complaint");

        createQueryOrComplaintBtn.setSize(300,80);
        createQueryOrComplaintBtn.setLayout(new FlowLayout(FlowLayout.CENTER));

        viewQueryOrComplaintBtn.setSize(300,80);
        viewQueryOrComplaintBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));

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
            Query query = null;
            Complaint complaint = null;
            Student student = null;
            String action = "";
            desktop.add(new Request(query, complaint, student, action));


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


    public static void main(String[] args) {
        new Dashboard();
    }

}
