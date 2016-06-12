import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UniplanerGUI extends JFrame{
	
	private StudyManager manager;
	private ArrayList<SemesterButton> semesterButtonList;
	private ArrayList<CourseButton> courseButtonList;
	private ArrayList<JButton> eventButtonList;
	private ArrayList<Event> events;
	private HashMap<JButton, Event> eventMap = new HashMap<>();
	private HashMap<SemesterButton, Semester> semMap = new HashMap<>();
	private HashMap<CourseButton, Course> courseMap = new HashMap<>();
	private JTextArea howTo;
	private JPanel courseAddPanel, semesterAddPanel, semesterHost, semesterHostIn, courseHostIn, northPanel, eventPanel;
	private NavigationPanel semNorthPanel, courseNorthPanel, docNorthPanel;
	private boolean load = false;

	private int maxx = 2000;
	private int maxy = 2000;
	private int prefx = 500;
	private  int prefy = 400;
	
	//Event Objects
	private JButton addEventBtn;
	
	//go forward Objects
	private JLabel courseLabel;
	private Semester currentSem;
	private Course currentCourse;
	private JButton currentSemButton, currentCourseButton;
	
	//DocumentPanel Objects
	private Color mainPanelColor = Color.WHITE;
	private DocumentPanel documentPanel;
	
	//North Panel and go back Objects
	private boolean inCourse, inSemester, inOverView;

	
	
	public UniplanerGUI(StudyManager manager) {
		this.manager = manager;
		int hostX = 800;
		int hostY = 600;
		
		//event Objects
		eventButtonList = new ArrayList<>();
		events = new ArrayList<>();
		
		//Navigation
		currentSemButton = new JButton();
		currentCourseButton = new JButton();
		inCourse = false;
		inSemester = false;
		inOverView = true;
		docNorthPanel = new NavigationPanel();
		courseNorthPanel = new NavigationPanel();
		semNorthPanel = new NavigationPanel();
		
		docNorthPanel.getDeletButton().addActionListener(new DeletButtonListener());
		courseNorthPanel.getDeletButton().addActionListener(new DeletButtonListener());
		semNorthPanel.getDeletButton().setVisible(false);
		
		docNorthPanel.getReturnButton().addActionListener(new ReturnButtonListener());
		courseNorthPanel.getReturnButton().addActionListener(new ReturnButtonListener());
		semNorthPanel.getReturnButton().addActionListener(new ReturnButtonListener());
		
		semNorthPanel.setText("Semesters");
		
		
		//StateLoader wenn gestartet wird StudyManager laden
		File file = new File(".UniPlaner.ser");
		if (file.exists()) {
			this.manager = StateLoader.loadState(file);
			System.out.println("manager geladen");
			System.out.println("managerBreite " + this.manager.width);
			System.out.println("managerHoehe " + this.manager.height);
			load = true;
		}
		else {
			this.manager = new StudyManager();
		}
		
		if(load) {
			hostX = this.manager.getWidth();
			hostY = this.manager.getHeight();
		}
		
		//allgemeine Einstellungen
		int minx = 100;
		int miny = 100;
		int x = 150;
		int y = 30;
		
		//Farbtheme 			#############################################
		Color dateColor = new Color(240, 240, 240); 
		Color headerColor = new Color(200, 200, 200);
		//Color fondColor = Color.BLACK;
	
		//Panel Dokumente		#############################################
		courseHostIn = new JPanel();
		courseHostIn.setBackground(mainPanelColor);
		courseHostIn.setLayout(new BorderLayout());
		courseHostIn.setPreferredSize(new Dimension(prefx, prefy));
		courseHostIn.setMaximumSize(new Dimension(maxx, maxy));
		documentPanel = new DocumentPanel(courseHostIn, headerColor, prefx, prefy, maxx, maxy);
		
		
		//Panel Kurse			##############################################
		courseAddPanel = new JPanel();
		JPanel semesterPanelIn = new JPanel();
		semesterHostIn = new JPanel();
		courseLabel = new JLabel("");
		
		//semesterLabelIn.setForeground(fondColor);
		courseLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		
		semesterPanelIn.setLayout(new BorderLayout());
		semesterPanelIn.setBackground(mainPanelColor);
		
		
		semesterHostIn.setLayout(new BorderLayout());
		//semesterHost.setBorder(BorderFactory.createLineBorder(mainPanelColor));
		semesterHostIn.setPreferredSize(new Dimension(prefx, prefy));
		semesterHostIn.setMaximumSize(new Dimension(maxx, maxy));
		semesterHostIn.setMinimumSize(new Dimension(minx, miny));
		semesterHostIn.setBackground(headerColor);
		semesterHostIn.add(semesterPanelIn, BorderLayout.CENTER);
		
			//Kurse hinzufügen
			courseButtonList = new ArrayList<>();
			JTextField courseName = new JTextField("New Course");
			JButton addCourseBtn = new JButton("Add");
			JPanel addCourse = new JPanel();
			JPanel addSemesterHostIn = new JPanel();
			
			courseName.setMinimumSize(new Dimension(x, y));
			//semesterName.setMaximumSize(new Dimension(x, y));
			courseName.setPreferredSize(new Dimension(x, y));
		
			addSemesterHostIn.setLayout(new BoxLayout(addSemesterHostIn, BoxLayout.Y_AXIS));
			addSemesterHostIn.add(addCourse);
			addSemesterHostIn.setBackground(mainPanelColor);
			
			addCourse.add(courseName);
			addCourse.add(addCourseBtn);
			addCourse.setBackground(mainPanelColor);
			
			//semesterAddPanel.setLayout(new BoxLayout(semesterAddPanel, BoxLayout.Y_AXIS));
			courseAddPanel.setBackground(mainPanelColor);
			courseAddPanel.setLayout(new FlowLayout());
			semesterPanelIn.add(addSemesterHostIn, BorderLayout.NORTH);
			semesterPanelIn.add(courseAddPanel, BorderLayout.CENTER);
			
			addCourseBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = courseName.getText();
					courseName.setText("");
					if (name.contains("/")) {
						name = name.replace('/', '\\');
						System.out.println(name);
					}
					StudyManager manager = UniplanerGUI.this.manager;
					if (name != null) {
						try {
								Course course = new Course(currentSem, name);
								CourseButton newBtn = new CourseButton(name);
								currentSem.addCourse(course);
								manager.getSemester(name);
								courseButtonList.add(newBtn);
								courseAddPanel.add(newBtn);
								courseMap.put(newBtn, course);
								courseAddPanel.validate();
								newBtn.addActionListener(new CourseButtonListener());
								System.out.println("Kurs erstellt");
						} 
						catch (Exception e1) {
							JOptionPane.showMessageDialog(getContentPane(), "Course already exists!", "Message", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			
			courseName.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					courseName.setText("");
				}
			});
		
		//Panel Semester 		#############################################
		semesterAddPanel = new JPanel();
		howTo = new JTextArea("Welcome!\nFirst step: Add a Semester to create courses and events.\n"
				+ "Second step: Create courses and import files.");
		JPanel semesterPanel = new JPanel();
		semesterHost = new JPanel();
		
		//JScrollPane semesterPane = new JScrollPane(semesterAddPanel);
		//semesterPane.setBorder(BorderFactory.createEmptyBorder());
		
		howTo.setPreferredSize(new Dimension(400, 50));
		//howTo.setForeground(fondColor);
		howTo.setBorder(null);
		howTo.setOpaque(false);
		howTo.setEditable(false);
		howTo.setLineWrap(true);
		howTo.setWrapStyleWord(true);
		howTo.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
		
		semesterPanel.setLayout(new BorderLayout());
		semesterPanel.setBackground(mainPanelColor);
	
		semesterHost.setLayout(new BorderLayout());
		//semesterHost.setBorder(BorderFactory.createLineBorder(mainPanelColor));
		semesterHost.setPreferredSize(new Dimension(prefx, prefy));
		semesterHost.setMaximumSize(new Dimension(maxx, maxy));
		semesterHost.setMinimumSize(new Dimension(minx, miny));
		semesterHost.setBackground(headerColor);
		semesterHost.add(semNorthPanel, BorderLayout.NORTH);
		semesterHost.add(semesterPanel, BorderLayout.CENTER);
		
			
			//Semester hinzufügen
			semesterButtonList = new ArrayList<>();
			JTextField semesterName = new JTextField("New Semester");
			JButton addSemBtn = new JButton("Add");
			JPanel addSemester = new JPanel();
			JPanel addSemesterHost = new JPanel();
		
			semesterName.setMinimumSize(new Dimension(x, y));
			//semesterName.setMaximumSize(new Dimension(x, y));
			semesterName.setPreferredSize(new Dimension(x, y));
		
			addSemesterHost.setLayout(new BoxLayout(addSemesterHost, BoxLayout.Y_AXIS));
			addSemesterHost.add(addSemester);
			addSemesterHost.add(howTo);
			addSemesterHost.setBackground(mainPanelColor);
			
			addSemester.add(semesterName);
			addSemester.add(addSemBtn);
			addSemester.setBackground(mainPanelColor);
			
			//semesterAddPanel.setLayout(new BoxLayout(semesterAddPanel, BoxLayout.Y_AXIS));
			semesterAddPanel.setBackground(mainPanelColor);
			semesterAddPanel.setLayout(new FlowLayout());
			semesterPanel.add(addSemesterHost, BorderLayout.NORTH);
			semesterPanel.add(semesterAddPanel, BorderLayout.CENTER);
			
			
			addSemBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = semesterName.getText();
					semesterName.setText("");
					if (name.contains("/")) {
						name = name.replace('/', '\\');
						System.out.println(name);
					}
					StudyManager manager = UniplanerGUI.this.manager;
					if (name != null) {
						try {
								Semester semester = new Semester(name);
								SemesterButton newBtn = new SemesterButton(name);
								manager.addSemester(semester);
								semesterButtonList.add(newBtn);
								newBtn.addActionListener(new SemButtonListener());
								updateSemesters();
								
						} 
						catch (Exception e1) {
							JOptionPane.showMessageDialog(getContentPane(), "Semester already exists!", "Message", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			
			semesterName.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					semesterName.setText("");
				}
			});
			
		//Panel Termine 		#############################################
		eventPanel = new JPanel();
		JScrollPane datePane = new JScrollPane(eventPanel);
		
		JPanel editPanel = new JPanel();
		addEventBtn = new JButton();
		addEventBtn.addActionListener(new AddEventListener());
		addEventBtn.setVisible(false);
		
		JPanel dateHost = new JPanel();
		JLabel dateLabel = new JLabel("Events");
		
		JPanel southDatePanel = new JPanel();
		southDatePanel.setLayout(new BorderLayout());
		southDatePanel.add(addEventBtn, BorderLayout.EAST);
		
		datePane.setBorder(BorderFactory.createEmptyBorder());
		datePane.setBackground(dateColor);
		
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.X_AXIS));
		//addDate.setAlignmentX(Component.RIGHT_ALIGNMENT);
		editPanel.add(southDatePanel);
		
		//dateLabel.setForeground(fondColor);
		dateLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		
		//datePanel.setBorder(BorderFactory.createLineBorder(mainPanelColor));
		eventPanel.setBackground(dateColor);
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		
		dateHost.setLayout(new BorderLayout());
		dateHost.setPreferredSize(new Dimension(250, 500));
		dateHost.setMaximumSize(new Dimension(1100, 1500));
		dateHost.setMinimumSize(new Dimension(200, 200));
		dateHost.setBackground(headerColor);
		dateHost.add(dateLabel, BorderLayout.NORTH);
		dateHost.add(datePane, BorderLayout.CENTER);
		dateHost.add(editPanel, BorderLayout.SOUTH);
		
		//Events beim Start laden
		loadEvents();
		
		//Uniplaner Hauptfenster #############################################
		JPanel host = new JPanel();
		
		this.setTitle("StudyManager");
		this.setLayout(new BorderLayout());
		getContentPane().setBackground(mainPanelColor);
		
		host.setBackground(headerColor);
		host.setLayout(new BoxLayout(host, BoxLayout.X_AXIS));
		host.add(dateHost);
		
		semesterHostIn.setVisible(false);
		courseHostIn.setVisible(false);
		host.add(courseHostIn);
		host.add(semesterHostIn);
		host.add(semesterHost);
		add(host, BorderLayout.CENTER);
		
		//Fenster anzeigen, Einstellungen		#############################################
		updateSemesters();
		addWindowListener(new SaveManagerListener(this.manager));
		addComponentListener(new WindowSizeListener(UniplanerGUI.this, this.manager));
		setVisible(true);
		setPreferredSize(new Dimension(hostX, hostY));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	private void updateSemesters() {
		
		for (int i = 0; i < manager.getSemesters().size(); i++) {
			if (load) {
				semesterButtonList.add(new SemesterButton(manager.getSemesters().get(i).getTitle()));
				semesterButtonList.get(i).addActionListener(new SemButtonListener());
			}
			semesterAddPanel.add(semesterButtonList.get(i));
			semesterAddPanel.validate();
			semMap.put(semesterButtonList.get(i), manager.getSemesters().get(i));
			if (howTo.isVisible()) {
				howTo.setVisible(false);
			}
		}
		load = false;
		System.out.println(manager.getSemesters().size());
		System.out.println(semesterButtonList.size());
	}
	
	public void loadEvents() {
		eventButtonList.clear();
		eventMap.clear();
		eventPanel.removeAll();
		events.clear();
		System.out.println("Terminpanel leeren");
		System.out.println("Semesteranzahl: " + manager.getSemesters().size());
		for (Semester s : manager.getSemesters()) {
			System.out.println("Events im Semester: " + s.getEvents().size());
			for (Event eS : s.getEvents()) {
				events.add(eS);
				System.out.println("SemesterEvent hinzugefügt bei GUI: " + eS.toString());
			}
			for (Course c : s.getCourses()) {
				for (Event eC : c.getEvents()) {
					events.add(eC);
					System.out.println("KursEvent hinzugefügt bei GUI: " + eC.toString());
				}
			}
		}
		Collections.sort(events);
		
		for (Event e : events) {
			JButton newBtn = new JButton(e.toString());
			newBtn.addActionListener(new EventListener());
			eventButtonList.add(newBtn);
			eventMap.put(newBtn, e);
			eventPanel.add(newBtn);
		}
		eventPanel.validate();
		eventPanel.repaint();
	}
	
	public void deletEvent(Event event) {
		System.out.println("im delet: bisherige EventAnzahl: " + events.size() );
		events.remove(event);
		System.out.println("im delet: neue EventAnzahl: " + events.size() );
	}
	
	private void loadCourses() {
		courseButtonList.clear();
		courseMap.clear();
		courseAddPanel.removeAll();
		System.out.println("Kurse: " + currentSem.getCourses().size());
		for (int i = 0; i < currentSem.getCourses().size(); i++) {
				String name = currentSem.getCourses().get(i).getTitle();
				courseButtonList.add(new CourseButton(name));
				courseAddPanel.add(courseButtonList.get(i));
				System.out.println("Button Anzahl: " + courseButtonList.size());
				courseMap.put(courseButtonList.get(i), currentSem.getCourses().get(i));
				System.out.println("Kurse für " +  currentSem.getTitle() + " geladen.");
				courseButtonList.get(i).addActionListener(new CourseButtonListener());
				System.out.println("KursListener für Button " + courseButtonList.get(i).getText() + " und Kurs " + courseMap.get(courseButtonList.get(i)).getTitle() + " erstellt.");	
		}
	}
	
	private class CourseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentCourseButton = (JButton) e.getSource();
			courseHostIn.setVisible(true);
			semesterHostIn.setVisible(false);
			currentCourse = courseMap.get(e.getSource());
			
			docNorthPanel.setText(courseNorthPanel.getText() + " > " + currentCourse.getTitle());
			documentPanel.setCourse(currentCourse, docNorthPanel);
			docNorthPanel.getDeletButton().setText("Delete " + currentCourse.getTitle());
			
			addEventBtn.setText("Add Event To " + currentCourse.getTitle());
			
			inSemester = false;
			inCourse = true;
		}
	}
	
	private class SemButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentSemButton = (JButton) e.getSource();
			semesterHost.setVisible(false);
			semesterHostIn.setVisible(true);
			currentSem = semMap.get(e.getSource());
			
			courseNorthPanel.setText("Semesters > " + currentSem.getTitle());
			semesterHostIn.add(courseNorthPanel, BorderLayout.NORTH);
			courseNorthPanel.getDeletButton().setText("Delete " + currentSem.getTitle());
			loadCourses();
			
			addEventBtn.setVisible(true);
			addEventBtn.setText("Add Event To " +  currentSem.getTitle());
			
			inOverView = false;
			inCourse = false;
			inSemester = true;
		}
	}
	
	private class DeletButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (inCourse) {
				courseMap.remove(currentCourse);
				courseAddPanel.remove(currentCourseButton);
				courseButtonList.remove(currentCourseButton);
				currentSem.removeCourse(currentCourse);
				semesterHostIn.setVisible(true);
				courseHostIn.setVisible(false);
				inSemester = true;
				inCourse = false;
			}
			else if (inSemester) {
				semMap.remove(currentSem);
				semesterAddPanel.remove(currentSemButton);
				semesterButtonList.remove(currentSemButton);
				manager.removeSemester(currentSem);
				semesterHost.setVisible(true);
				semesterHostIn.setVisible(false);
				inOverView = true;
				inSemester = false;
			}
		}
	}
	
	
	private class ReturnButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (inCourse) {
				addEventBtn.setText("Add Event To " + currentSem.getTitle());
				semesterHostIn.setVisible(true);
				courseHostIn.setVisible(false);
				inSemester = true;
				inCourse = false;
			}
			else if (inSemester) {
				addEventBtn.setVisible(false);
				semesterHost.setVisible(true);
				semesterHostIn.setVisible(false);
				inOverView = true;
				inSemester = false;
			}
			else if (inOverView) {
				semNorthPanel.getReturnButton().setEnabled(false);
			}
		}
	}
	
	private class EventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new EventEditor(eventMap.get(e.getSource()).getStudyUnit(), eventMap.get(e.getSource()), UniplanerGUI.this);
		}
	}
	
	private class AddEventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (inCourse) {
				new EventCreator(currentCourse, UniplanerGUI.this);
			}
			else if (inSemester) {
				new EventCreator(currentSem, UniplanerGUI.this);
			}
		}
	}
	
	

}
