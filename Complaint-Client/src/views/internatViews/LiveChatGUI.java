package views.internatViews;

import controller.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class  LiveChatGUI extends JInternalFrame implements ActionListener {
    private JTextField textField;
    private JTextArea chatArea;
    private JLabel statusLabel;
    private boolean isOnline = true;
    private Client client;

    public LiveChatGUI(Client client) {
        // Set up the JFrame
        super("Live Chat", true, true, true, true);
        this.client = client;
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the input field and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        textField = new JTextField();
        inputPanel.add(textField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Create the status label
        statusLabel = new JLabel("Online");
        statusLabel.setForeground(Color.GREEN);
        add(statusLabel, BorderLayout.NORTH);
        
     /*// Get the current time and format it
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
         String timestamp = LocalDateTime.now().format(formatter);

        // Create the message components
        JPanel messagePanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(username + ":");
        ImageIcon icon = new ImageIcon("person.png");
        JLabel iconLabel = new JLabel(icon);
        String message = null;
		JLabel messageLabel = new JLabel(message);
        JLabel timestampLabel = new JLabel(timestamp);
        // Add the components to the message panel
        messagePanel.add(iconLabel, BorderLayout.WEST);
        messagePanel.add(nameLabel, BorderLayout.CENTER);
        messagePanel.add(messageLabel, BorderLayout.SOUTH);
        messagePanel.add(timestampLabel, BorderLayout.NORTH);
        // Add the message panel to the chat area
        chatArea.add(messagePanel);*/

        // Show the JFrame
        setVisible(true);
    }

    // Event listener for the send button
    public void actionPerformed(ActionEvent e) {
        String message = textField.getText();
        if (message.trim().equals("")) {
            return;
        }
        if (!isOnline) {
            chatArea.append("System: You can't send messages while offline.\n");
            return;
        }
        chatArea.append(client.getStudent().getFirstName() + ": " + message + "\n");
        textField.setText("");
    }
}