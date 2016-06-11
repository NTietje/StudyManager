package lib;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * DocumentPanel displays the documents of a course and allows the user to import, export and delete files.
 * The DocumentPanel just updates itself for every new selected course (CourseButton).
 * @author Katharina, Nina
 *
 */
public class DocumentPanel {
	private DefaultListModel <File> listModel;
	private Course course;
	private JPanel containerPanel;
	
	public DocumentPanel(JPanel containerPanel, Color color) {
		this.containerPanel = containerPanel;
		listModel = new DefaultListModel<>();
		JList<File> list = new JList<>(listModel);
		
		//panel displaying list of documents and buttons
		containerPanel.setBackground(color);
		containerPanel.setLayout(new BorderLayout());
		containerPanel.setPreferredSize(new Dimension(440, 500));
		containerPanel.setMaximumSize(new Dimension(2000, 2000));
		containerPanel.setMinimumSize(new Dimension(100, 100));
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		
		JButton openBtn = new JButton("Open");
		JButton importBtn = new JButton("Import");
		JButton exportBtn = new JButton("Export");
		JButton deleteBtn = new JButton("Delete");
		openBtn.setEnabled(false);
		exportBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		
		//displaying list in vertical order and several columns
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new FileListRenderer());
		
		containerPanel.add(list, BorderLayout.CENTER);
		containerPanel.add(btnPanel, BorderLayout.SOUTH);
		btnPanel.add(openBtn);
		btnPanel.add(importBtn);
		btnPanel.add(exportBtn);
		btnPanel.add(deleteBtn);
		
		
		/**
		 * enables button if user has selected a file, else disable it
		 */
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() != null){
					openBtn.setEnabled(true);
					exportBtn.setEnabled(true);
					deleteBtn.setEnabled(true);
				}
				else {
					openBtn.setEnabled(false);
					exportBtn.setEnabled(false);
					deleteBtn.setEnabled(false);
				}
				
			}
			
		});
		
		/**
		 * opens selected file with an application associated with the file type
		 */
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				File file = list.getSelectedValue();
				if (Desktop.isDesktopSupported()){
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.open(file);
					}
					catch (IOException ex){
						JOptionPane.showMessageDialog(containerPanel, "The selected file couldn't be opened.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Sorry - your operating system doesn't support this function.\n"+
							"You can find the file here " + file.getAbsolutePath());
				}
			}
		});
		
		/**
		 * opens dialog allowing user to select file to import and add document to course
		 */
		importBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChooser = new JFileChooser();
				//@Nina: statt Tester.this AktuelleKlasse.this
				int opt = fileChooser.showDialog(null, "Import");
				if (opt == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					//@Nina: wieder statt Tester.this AktuelleKlasse.this (um auf Variable course zugreifen zu koennen)
					DocumentPanel.this.course.addDocument(file);
					updateList();
					
				}
			}
		});
		
		/**
		 * displays dialog allowing user to export document to another file
		 */
		exportBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChooser = new JFileChooser();
				int opt = fileChooser.showDialog(null, "Export");
				if (opt == JFileChooser.APPROVE_OPTION){
					File fileSource = list.getSelectedValue();
					Path sourcePath = Paths.get(fileSource.getAbsolutePath());
					File fileTarget = fileChooser.getSelectedFile();
					Path targetPath = Paths.get(fileTarget.getAbsolutePath());
					try {
						Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
					}
					catch (IOException ex){
						JOptionPane.showMessageDialog(containerPanel, "Unfortunately the file couldn't be exported.");
					}
				}
			}
		});
		
		/**
		 * displays dialog asking user to confirm delete action
		 */
		deleteBtn.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				File file = list.getSelectedValue();
				int opt = JOptionPane.showConfirmDialog(containerPanel, "Do you really want to delete \"" + file.getName() + "\"?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION){
					DocumentPanel.this.course.removeDocument(file);
					updateList();
				}
			}
		});
		
	}
	
	/**
	 * clear listmodel and update it with list of documents of current course
	 */
	public void updateList() {
		listModel.removeAllElements();
		ArrayList<File> documents = course.getDocuments();
		for (int i = 0; i < documents.size(); i++){
			listModel.addElement(documents.get(i));
		}
	}
	
	/**
	 * set the actually selected course, set the JLabel (name of the course) into the panel and update the list of documents 
	 */
	public void setCourse(Course course, NavigationPanel northPanel) {
		this.course = course;
		containerPanel.add(northPanel, BorderLayout.NORTH);
		updateList();
	}
	


}