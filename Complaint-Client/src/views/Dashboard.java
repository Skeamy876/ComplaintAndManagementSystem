package views;
	

import java.awt.*; 
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import javax.swing.ImageIcon;

import controller.Client;
import models.Complaint;
import models.Query;
import models.Student;
import views.internatViews.AddQueryComplaintView;
import views.internatViews.StudentQueriesComplaintsTableView;

import javax.swing.*;


//student dashboard

public class Dashboard extends JFrame {
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem exitMenuItem;
  private JDesktopPane desktop;
  private JLabel welcomeLabel;
  private JLabel idLabel;
  private JLabel nameLabel;
  private JButton createButton;
  private JButton viewButton;
  private JButton chatButton;
  private JButton notificationButton;
  private JLabel profilePictureLabel;
  private final Client client;

  public Dashboard(Client client,String name, String id) {
	  
	  this.client = client;
  	
      // Set up the window
      setTitle("Student Dashboard");
      setResizable(true);
      setVisible(true);
      setSize(900, 600);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      

      // Create the menu bar
      menuBar = new JMenuBar();
      fileMenu = new JMenu("Exit Dashboard");
      exitMenuItem = new JMenuItem("Exit");

      exitMenuItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              System.exit(0);
          }
      });

      fileMenu.add(exitMenuItem);
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);

      // Create the desktop pane
      desktop = new JDesktopPane();
      setContentPane(desktop);
      
      //profile picture
      ImageIcon profilePictureIcon = new ImageIcon("src/views/user.png");
      Image profilePictureImage = profilePictureIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
      ImageIcon profilePicture = new ImageIcon(profilePictureImage);
      profilePictureLabel = new JLabel();
      profilePictureLabel.setIcon(profilePicture);
      desktop.add(profilePictureLabel);
      profilePictureLabel.setBounds(750, 20, 50, 40);
      

      // Create the welcome label
      welcomeLabel = new JLabel("Welcome, " + name + "!");
      welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
      desktop.add(welcomeLabel);
      welcomeLabel.setBounds(20, 20, 300, 30);

      // Create the ID label
      idLabel = new JLabel("ID: " + id);
      desktop.add(idLabel);
      idLabel.setBounds(600, 60, 200, 20);
      idLabel.setHorizontalAlignment(JLabel.RIGHT);

      // Create the name label
      nameLabel = new JLabel("Name: " + name);
      desktop.add(nameLabel);
      nameLabel.setBounds(600, 80, 200, 20);
      nameLabel.setHorizontalAlignment(JLabel.RIGHT);

      // Create the buttons
      createButton = new JButton("Create Query/Complaint");
      desktop.add(createButton);
      createButton.setBounds(20, 120, 200, 30);
      createButton.addActionListener(e -> {
          desktop.add(new AddQueryComplaintView(client));
      });

      viewButton = new JButton("View Query/Complaint");
      desktop.add(viewButton);
      viewButton.setBounds(20, 160, 200, 30);
      viewButton.addActionListener(e -> {
          List<Query> queries;
          List<Complaint> complaints;
          ObjectInputStream objIs = client.getObjIs();
          ObjectOutputStream objOs = client.getObjOs();
          Student student =(Student) client.getPerson();
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
          StudentQueriesComplaintsTableView studentQueriesComplaintsTableView = new StudentQueriesComplaintsTableView(queries,complaints);
          studentQueriesComplaintsTableView.setClient(client);
          desktop.add(studentQueriesComplaintsTableView);
      });
      
      //chat button
      chatButton = new JButton();
      ImageIcon chatIcon = new ImageIcon("src/views/chat.png");
      Image chatImage = chatIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
      chatButton.setIcon(new ImageIcon(chatImage));
      desktop.add(chatButton);
      chatButton.setBounds(640, 20, 50, 40);
      chatButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Code to handle chat button click
          }
      });
      
      //notifications button
      notificationButton = new JButton();
      ImageIcon notifIcon = new ImageIcon("src/views/bell-ring.png");
      Image notifImage = notifIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
      notificationButton.setIcon(new ImageIcon(notifImage));
      desktop.add(notificationButton);
      notificationButton.setBounds(570, 20, 50, 40);
      notificationButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // Code to handle chat button click
          }
      });
     
      // Set desktop background color and add image
     desktop.setBackground(new Color(245, 245, 245));
      ImageIcon backgroundImage = new ImageIcon("src/views/background.png");
      JLabel backgroundLabel = new JLabel(backgroundImage);
      backgroundLabel.setBounds(0, 0, 900, 600);
      desktop.add(backgroundLabel); 

      // Display the window
      setVisible(true);
  }
/*
  public static void main(String[] args) {
      // Replace with actual student name and ID
      String name = "John Smith";
      String id = "123456789";

      Dashboard dashboard = new Dashboard(name, id);
  }  */
}  

