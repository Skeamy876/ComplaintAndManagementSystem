package views.internatViews;

import controller.Client;
import models.Advisor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssignStudentAdvisor extends JInternalFrame {
    private Client client;
    private Object[] selectedData;
    private List<Advisor> advisors;
    private JTextArea textArea;
    private JButton assignButton;
    private JLabel label = new JLabel("Assign Student Advisor");
    private JComboBox advisorList;
    private JPanel panel;


    public AssignStudentAdvisor(Client client,Object[] selectedData) {
        super("Assign Student Advisor",true,true,true,true);
        this.client = client;
        this.selectedData = selectedData;
        this.textArea = new JTextArea();
        this.textArea.setText(Arrays.toString(selectedData));
        this.textArea.setEditable(false);
        this.assignButton = new JButton("Assign");
        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(4,1));
        this.panel.add(label);
        this.panel.add(textArea);
        advisors = this.fetchAdvisorsFromServer();
        if (advisors == null) {
            advisorList = new JComboBox(new String[]{"No Advisor Available"});
        } else {

            List<String> advisorsToDisplay = advisors.stream()
                    .map(a ->+ a.getIdNumber() + "     :     " + a.getFirstName())
                    .collect(Collectors.toList());
            advisorList = new JComboBox(advisorsToDisplay.toArray());
        }
        this.panel.add(advisorList);
        this.panel.add(assignButton);
        this.add(panel);
        this.assignAdvisorToStudent();
        this.setSize(500,500);
        this.setVisible(true);

    }


    private List<Advisor> fetchAdvisorsFromServer(){
        ObjectInputStream objIs = client.getObjIs();
        ObjectOutputStream objOs = client.getObjOs();
        try {
            objOs.writeObject("fetchAdvisors");
            String response = (String) objIs.readObject();
            if (response.equals("successful")) {
                List<Advisor> advisorList = (List<Advisor>) objIs.readObject();
                advisorList.forEach(System.out::println);
                return advisorList;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void assignAdvisorToStudent(){
        assignButton.addActionListener(e -> {
            String selectedAdvisor = (String)advisorList.getSelectedItem();

            char firstChar = selectedAdvisor.charAt(0);
            long advisorId = (long) Character.getNumericValue(firstChar);
            long queryComplaintId = (long) selectedData[4];
            ObjectOutputStream objOs = client.getObjOs();
            ObjectInputStream objIs = client.getObjIs();

            try {
                objOs.writeObject("assignAdvisor");
                objOs.writeObject(advisorId);
                objOs.writeObject(queryComplaintId);
                String response = (String) objIs.readObject();
                if (response.equals("successful")) {
                    JOptionPane.showMessageDialog(this, "Advisor Assigned Successfully");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Assign Advisor");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

    }


}
