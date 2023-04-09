package views;

import controller.Client;
import models.Advisor;
import models.Person;
import models.Student;
import models.Supervisor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginView extends JFrame implements ActionListener {
 
    JPanel container = new JPanel();
    JComboBox userType = new JComboBox<>(new String[]{"Student", "Supervisor", "Advisor"});
    JLabel userLabel = new JLabel("ID NUMBER");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton signinButton = new JButton("SIGN IN");
    JButton changePasswordButton = new JButton("Change Password");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JPanel systemDetailsPanel = new JPanel();
    JLabel systemDetailsLabel = new JLabel("Complaint and Query Management System");
    JLabel systemDetailsLabel2 = new JLabel("Version 1.0");
    JLabel systemDetailsLabel3 = new JLabel("© 2021");
    JLabel systemDetailsLabel4 = new JLabel("All Rights Reserved");


    public LoginView() {
        this.setTitle("Complaint and Query Management System");


        try {
            BufferedImage logo = ImageIO.read(new File("src/views/utech-logo.png"));
            Image logoPic = logo.getScaledInstance(80, 80, Image.SCALE_SMOOTH);


            JLabel logoLabel = new JLabel(new ImageIcon(logoPic));
            logoLabel.setBounds(150, 50, 80, 80);
            container.add(logoLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
        this.setBackground(Color.WHITE);
        this.setBounds(10, 10, 400, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToPanel();
        addActionEvent();
 
    }
    public void setLayoutManager() {
        this.setLayout(null);
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        userType.setBounds(50, 150, 250, 30);
        userLabel.setBounds(50, 220, 100, 30);
        passwordLabel.setBounds(50, 250, 100, 30);
        userTextField.setBounds(150, 220, 150, 30);
        passwordField.setBounds(150, 250, 150, 30);
        showPassword.setBounds(150, 300, 150, 30);
		signinButton.setBounds(50, 350, 100, 30);
        changePasswordButton.setBounds(200, 350, 100, 30);
        systemDetailsLabel2.setBounds(50,445,100,30);
        systemDetailsLabel3.setBounds(50,455,100,30);
        systemDetailsLabel4.setBounds(50,465,100,30);

    }
 
    public void addComponentsToPanel() {
        container.add(userType);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(signinButton);
        container.add(changePasswordButton);
        container.add(systemDetailsLabel2);
        container.add(systemDetailsLabel3);
        container.add(systemDetailsLabel4);
        container.setSize(500, 600);
        systemDetailsPanel.setSize(500, 400);
        this.add(container);
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