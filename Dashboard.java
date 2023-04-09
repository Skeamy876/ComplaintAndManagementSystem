package views;

import java.awt.*; 
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import controller.Client;
import models.Advisor;
import models.Complaint;
import models.Query;
import models.Student;
import views.internatViews.*;
import javax.swing.*;


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
	private JButton notificationButton,searchButton;
	private JLabel profilePictureLabel,searchLabel;
	private String[] searchCategories = {"MISSING_GRADES", "NO_FINANCIAL_STATUS_UPDATE", "BARRED_FROM_EXAMS", "INCORRECT_ACADEMIC_RECORD", "NO_TIMETABLE", "STAFF_MISCONDUCT"};
	private JComboBox<String> searchBox;
	private JList<String> activeUsersList;
	private ObjectInputStream objIs;
	private ObjectOutputStream objOs;
	private final Client client;

	public Dashboard(Client client) {
		this.client = client;
		this.initializeCommonComponents();
		if (client.getStudent() != null) {
			this.renderStudentComponents();
		}
		if (client.getSupervisor() != null) {
			this.renderSupervisorComponents();
		}
		if (client.getAdvisor() != null) {
			this.renderAdvisorComponents();
		}
		this.setWindowProperties();
	}

	private void initializeCommonComponents(){
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Logout");
		exitMenuItem = new JMenuItem("Exit");

		exitMenuItem.addActionListener(e -> System.exit(0));
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		desktop = new JDesktopPane();
		setContentPane(desktop);
		ImageIcon profilePictureIcon = new ImageIcon("src/views/user.png");
		Image profilePictureImage = profilePictureIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon profilePicture = new ImageIcon(profilePictureImage);
		profilePictureLabel = new JLabel();
		profilePictureLabel.setIcon(profilePicture);
		desktop.add(profilePictureLabel);
		profilePictureLabel.setBounds(750, 20, 50, 40);

		desktop.setBackground(new Color(255, 255, 255));
		/*desktop.setBackground(new Color(245, 245, 245));
		ImageIcon backgroundImage = new ImageIcon("src/views/background.png");
		JLabel backgroundLabel = new JLabel(backgroundImage);
		backgroundLabel.setBounds(0, 0, 900, 600);*/
//		desktop.add(backgroundLabel);


		//active users list
		if (client.getStudent() != null || client.getAdvisor() != null) {
			activeUsersList = new JList<>();
			objIs = client.getObjIs();
			objOs = client.getObjOs();
			try {
				objOs.writeObject("onlineUsersStudentToAdvisor");
				objOs.flush();
				List<Student> activeStudents = (List<Student>) objIs.readObject();
				List<Advisor> activeAdvisors = (List<Advisor>) objIs.readObject();
				String response = (String) objIs.readObject();
				if (response.equals("successful")){
					activeStudents.stream().forEach(student-> System.out.println(student.getFirstName()));
					activeAdvisors.stream().forEach(advisor-> System.out.println(advisor.getFirstName()));
					activeUsersList.setListData(activeStudents.stream().map(student-> student.getFirstName()).toArray(String[]::new));
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			activeUsersList.setBounds(600, 120, 200, 400);
			desktop.add(activeUsersList);
		}

		//notifications button
		notificationButton = new JButton();
		ImageIcon notifIcon = new ImageIcon("src/views/bell-ring.png");
		Image notifImage = notifIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		notificationButton.setIcon(new ImageIcon(notifImage));
		desktop.add(notificationButton);
		notificationButton.setBounds(570, 20, 50, 40);
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		if(client.getStudent() != null ||  client.getAdvisor() != null){
			//chat button
			chatButton = new JButton();
			ImageIcon chatIcon = new ImageIcon("src/views/chat.png");
			Image chatImage = chatIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
			chatButton.setIcon(new ImageIcon(chatImage));
			desktop.add(chatButton);
			chatButton.setBounds(640, 20, 50, 40);
			chatButton.addActionListener(e -> {
				desktop.add(new LiveChatGUI(client));
			});
		}


	}

	private void renderStudentComponents() {
		setTitle("Student Dashboard");
		// Create the welcome label
		welcomeLabel = new JLabel("Welcome, " + client.getStudent().getFirstName() + "!");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		desktop.add(welcomeLabel);
		welcomeLabel.setBounds(20, 20, 300, 30);

		// Create the ID label
		idLabel = new JLabel("ID: " + client.getStudent().getIdNumber());
		desktop.add(idLabel);
		idLabel.setBounds(600, 60, 200, 20);
		idLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Create the name label
		nameLabel = new JLabel("Name: " + client.getStudent().getFirstName() + client.getStudent().getLastName());
		desktop.add(nameLabel);
		nameLabel.setBounds(600, 80, 200, 20);
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Create the buttons
		createButton = new JButton("Create Query/Complaint");
		createButton.setBackground(new Color(0,255,127)); //green
		desktop.add(createButton);
		createButton.setBounds(20, 120, 200, 30);
		createButton.addActionListener(e -> {
			desktop.add(new AddQueryComplaintView(client));
		});

		viewButton = new JButton("View Query/Complaint");
		createButton.setBackground(new Color(0,191,255)); //blue
		desktop.add(viewButton);
		viewButton.setBounds(20, 160, 200, 30);
		viewButton.addActionListener(e -> {
			List<Query> queries = new ArrayList<>();
			List<Complaint> complaints = new ArrayList<>();
			ObjectInputStream objIs = client.getObjIs();
			ObjectOutputStream objOs = client.getObjOs();
			Student student = client.getStudent();
			String response = " ";
			try {
				objOs.writeObject("AllStudentQueriesAndComplaints");
				objOs.writeObject(student.getIdNumber());
				queries = (ArrayList<Query>) objIs.readObject();
				complaints = (ArrayList<Complaint>) objIs.readObject();
				response = (String) objIs.readObject();
				if (response.equals("successful")) {
					System.out.println("Queries received successfully");
				} else {
					System.out.println("Queries not received successfully");
				}
			} catch (IOException ex) {
				System.err.println("Unexpected error : "+ ex.getMessage());
			} catch (ClassNotFoundException ex) {
				System.err.println("Unexpected error : "+ ex.getMessage());
			}
			QueriesComplaintsTableView queriesComplaintsTableView = new QueriesComplaintsTableView(queries, complaints);
			queriesComplaintsTableView.setClient(client);
			desktop.add(queriesComplaintsTableView);
		});
	}

	private void renderSupervisorComponents() {
		setTitle("Student Services Supervisor Dashboard");

		// Create the welcome label
		welcomeLabel = new JLabel("Welcome, " + client.getSupervisor().getFirstName() + "!");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		desktop.add(welcomeLabel);
		welcomeLabel.setBounds(20, 20, 300, 30);

		// Create the ID label
		idLabel = new JLabel("ID: " + client.getSupervisor().getIdNumber());
		desktop.add(idLabel);
		idLabel.setBounds(600, 60, 200, 20);
		idLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Create the name label
		nameLabel = new JLabel("Name: " + client.getSupervisor().getFirstName() + client.getSupervisor().getLastName());
		desktop.add(nameLabel);
		nameLabel.setBounds(600, 80, 200, 20);
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);

		//Search area
		searchLabel = new JLabel("Search by Category: ");
		searchBox = new JComboBox<String>(searchCategories);
		searchButton = new JButton("Search");
		desktop.add(searchLabel);
		desktop.add(searchBox);
		desktop.add(searchButton);
		searchLabel.setBounds(300, 80, 200, 30);
		searchBox.setBounds(300, 120, 200, 30);
		searchButton.setBounds(300, 150, 200, 30);
			searchButton.addActionListener(e -> {
			desktop.add(new SearchView(client, searchBox.getSelectedItem().toString()));
		});

		viewButton = new JButton("View Resolved Complaints/Queries");
		createButton.setBackground(new Color(0,255,127)); //green
		desktop.add(viewButton);
		viewButton.setBounds(20, 160, 200, 30);
		viewButton.addActionListener(e -> {
			desktop.add(new ViewUnresolvedAndUnresolvedQC(client));

		});

		viewButton = new JButton("Assign Complaints/Queries");
		createButton.setBackground(new Color(0,191,255)); //blue
		desktop.add(viewButton);
		viewButton.setBounds(20, 200, 200, 30);
		viewButton.addActionListener(e -> {
			desktop.add(new ViewUnresolvedAndUnresolvedQC(client));
		});

		viewButton = new JButton("View All Complaints/Queries");
		desktop.add(viewButton);
		viewButton.setBounds(20, 240, 200, 30);
		viewButton.addActionListener(e ->  {
			desktop.add(new ViewAllComplaintsAndQueries(client));
		});

	}

	private void renderAdvisorComponents() {
		setTitle("Student Dashboard");
		// Create the welcome label
		welcomeLabel = new JLabel("Welcome, " + client.getAdvisor().getFirstName() + "!");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		desktop.add(welcomeLabel);
		welcomeLabel.setBounds(20, 20, 300, 30);

		// Create the ID label
		idLabel = new JLabel("ID: " + client.getAdvisor().getIdNumber());
		desktop.add(idLabel);
		idLabel.setBounds(600, 60, 200, 20);
		idLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Create the name label
		nameLabel = new JLabel("Name: " + client.getAdvisor().getFirstName() + client.getAdvisor().getLastName());
		desktop.add(nameLabel);
		nameLabel.setBounds(600, 80, 200, 20);
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Create the buttons
		viewButton = new JButton("View Assigned Query/Complaint");
		createButton.setBackground(new Color(0,255,127)); //green
		desktop.add(viewButton);
		viewButton.setBounds(20, 120, 200, 30);
		viewButton.addActionListener(e -> {
			desktop.add(new ViewAssignedComplaintsAndQueries(client));
		});
	}

	private void setWindowProperties() {
		setResizable(true);
		setVisible(true);
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

}  

