package org.example.Model;

import controller.Client;
import models.Advisor;
import models.Person;
import models.Student;
import models.Supervisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginView extends JFrame implements ActionListener {
 
    Container container = getContentPane();
    JComboBox userType = new JComboBox<>(new String[]{"Student", "Supervisor", "Advisor"});
    JLabel userLabel = new JLabel("ID NUMBER");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton signinButton = new JButton("SIGN IN");
    JButton changePasswordButton = new JButton("Change Password");
    JCheckBox showPassword = new JCheckBox("Show Password");

    
    
    private static final String USERNAME = "admin";
    private static String PASSWORD = "password123";

    
    public LoginView() {
        this.setTitle("views.Login Form");
        this.setVisible(true);
        this.setBounds(10, 10, 370, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
     // set the background color to a mix of sky blue and green
        this.getContentPane().setBackground(new Color(166, 216, 208));

        
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        userType.setBounds(50, 50, 250, 30);
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
		signinButton.setBounds(50, 300, 100, 30);
        changePasswordButton.setBounds(200, 300, 100, 30);
 
 
    }
 
    public void addComponentsToContainer() {
        container.add(userType);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(signinButton);
        container.add(changePasswordButton);
    }
 
    public void addActionEvent() {
        signinButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        showPassword.addActionListener(this);
    }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }


        }

        if (e.getSource() == signinButton) {
            String userText;
            String pwdText;
            String userTypeText;
            userText = userTextField.getText().trim();
            pwdText = passwordField.getText().trim();
            userTypeText = userType.getSelectedItem().toString();
            Student student;
            Advisor advisor;
            Supervisor supervisor;
            Person person;
            Client client = new Client(new Person());
            ObjectOutputStream objOs = client.getObjOs();
            ObjectInputStream objIs = client.getObjIs();


            if (userTypeText.equals("Student")) {
                student = new Student();
                student.setIdNumber(Long.parseLong(userText));
                student.setPassword(pwdText);
                client = new Client(student);
                try {
                    objOs.writeObject("Authenticate");
                    objOs.writeObject(client.getStudent());
                    student = (Student) objIs.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                client = new Client(student);
                if (student != null) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    Dashboard dashboardView = new Dashboard(client);
                    dashboardView.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid ID Number or Password");
                }

            } else if (userTypeText.equals("Supervisor")) {
                supervisor = new Supervisor();
                supervisor.setIdNumber(Long.parseLong(userText));
                supervisor.setPassword(pwdText);
                client = new Client(supervisor);

                try {
                    objOs.writeObject("Authenticate");
                    objOs.writeObject(client.getSupervisor());
                    supervisor = (Supervisor) objIs.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Classpath: " + System.getProperty("java.class.path"));
                    System.out.println("Context classloader: " + Thread.currentThread().getContextClassLoader());
                    new RuntimeException(ex);
                }
                client = new Client(supervisor);
                if (supervisor != null) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    Dashboard dashboardView = new Dashboard(client);
                    dashboardView.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid ID Number or Password");
                }

            } else if (userTypeText.equals("Advisor")){
                advisor = new Advisor();
                advisor.setIdNumber(Long.parseLong(userText));
                advisor.setPassword(pwdText);
                client = new Client(advisor);
                try {
                    objOs.writeObject("Authenticate");
                    objOs.writeObject(client.getAdvisor());
                    advisor = (Advisor) objIs.readObject();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                client = new Client(advisor);
                if (advisor != null) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    Dashboard dashboardView = new Dashboard(client);
                    dashboardView.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid ID Number or Password");
                }
            }



        }

    }






}