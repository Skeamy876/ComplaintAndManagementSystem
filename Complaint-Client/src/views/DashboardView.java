package views;

import controller.Client;
import models.Student;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;


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
        accountDetails = new JMenu("Acount");
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
            desktop.add(new QueryComplaintFrameView(client));
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
