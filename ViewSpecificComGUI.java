package advisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewSpecificComGUI extends JFrame implements ActionListener {

    private JTextField complaintIDField;
    private JTextArea studentDetailsTextArea;
    private JButton viewDetailsButton;

    public ViewSpecificComGUI() {
        setTitle("Complaint Details");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel complaintIDLabel = new JLabel("Complaint ID:");
        complaintIDField = new JTextField(10);

        studentDetailsTextArea = new JTextArea(10, 40);
        studentDetailsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(studentDetailsTextArea);

        viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(this);

        JPanel inputPanel = new JPanel();
        inputPanel.add(complaintIDLabel);
        inputPanel.add(complaintIDField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewDetailsButton);

        getContentPane().add(BorderLayout.NORTH, inputPanel);
        getContentPane().add(BorderLayout.CENTER, scrollPane);
        getContentPane().add(BorderLayout.SOUTH, buttonPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewDetailsButton) {
            // TODO: Add code to retrieve complaint details and student details and display them in the text area.
            String complaintID = complaintIDField.getText();
            // Retrieve complaint details and student details using the complaintID and display them in the text area.
            studentDetailsTextArea.setText("Student ID: 123\nName: John Doe\nEmail: john.doe@example.com\nContact: 123-456-7890\nIssue Type: Technical Issue\nIssue Details: Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        }
    }

    public static void main(String[] args) {
        ViewSpecificComGUI gui = new ViewSpecificComGUI();
        gui.setVisible(true);
    }
}
