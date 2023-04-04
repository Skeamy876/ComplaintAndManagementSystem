package advisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewComListGUI extends JFrame implements ActionListener {

    private JTextArea complaintsTextArea;
    private JButton viewComplaintsButton;

    public ViewComListGUI() {
        setTitle("Complaints List");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        complaintsTextArea = new JTextArea(10, 40);
        complaintsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(complaintsTextArea);

        viewComplaintsButton = new JButton("View Complaints");
        viewComplaintsButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(viewComplaintsButton);

        getContentPane().add(BorderLayout.CENTER, scrollPane);
        getContentPane().add(BorderLayout.SOUTH, panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewComplaintsButton) {
            // TODO: Add code to retrieve list of complaints/queries and display them in the text area.
        }
    }

    public static void main(String[] args) {
    	ViewComListGUI gui = new ViewComListGUI();
        gui.setVisible(true);
    }
}
