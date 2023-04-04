package viewtesr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewQueryGUI extends JFrame implements ActionListener {

    JLabel idLabel, nameLabel, queryLabel, statusLabel;
    JTextField idTextField;
    JTextArea queryTextArea;
    JButton viewButton;

    public ViewQueryGUI() {
        setTitle("Student Query Viewer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        idLabel = new JLabel("Enter Student ID:");
        nameLabel = new JLabel("");
        queryLabel = new JLabel("Student Query:");
        statusLabel = new JLabel("");

        idTextField = new JTextField();
        queryTextArea = new JTextArea();
        queryTextArea.setEditable(false);

        viewButton = new JButton("View Query");
        viewButton.addActionListener(this);

        add(idLabel);
        add(idTextField);
        add(nameLabel);
        add(new JLabel(""));
        add(queryLabel);
        add(queryTextArea);
        add(statusLabel);
        add(new JLabel(""));
        add(viewButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewButton) {
            String id = idTextField.getText().trim();
            // Retrieve student query from database or file using ID
            // Replace the following lines with your code to retrieve data
            String name = "John Doe";
            String query = "I have a question about my grade.";
            String status = "Pending";
            
            nameLabel.setText("Name: " + name);
            queryTextArea.setText(query);
            statusLabel.setText("Status: " + status);
        }
    }

    public static void main(String[] args) {
        new ViewQueryGUI();
    }
}
