package views;

import controller.Client;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginView extends JFrame implements ActionListener {
 
    Container container = getContentPane();
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
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
		signinButton.setBounds(50, 300, 100, 30);
        changePasswordButton.setBounds(200, 300, 100, 30);
 
 
    }
 
    public void addComponentsToContainer() {
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
        //Coding Part of LOGIN button
        if (e.getSource() == signinButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();

            Student student = new Student();
            student.setIdNumber(Long.parseLong(userText));
            student.setPassword(pwdText);
            Client client = new Client(student);

            ObjectOutputStream objOs = client.getObjOs();
            ObjectInputStream objIs = client.getObjIs();

            boolean flag;

            Category test = new Category();
            test.setId(222222);
            test.setCategoryName(Category.CategoryEnum.MISSING_GRADES);

            Supervisor supervisor = new Supervisor();
            supervisor.setIdNumber(333333);
            supervisor.setFirstName("Tester");


            try {
                objOs.writeObject("Authenticate");
                objOs.writeObject(student);
                flag = (boolean) objIs.readObject();
                objOs.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            if (flag) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                DashboardView dashboardView = new DashboardView(client);
                dashboardView.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID Number or Password");
            }

        }


       //Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
 
 
        }
    }




 
}
 
