
	import java.awt.BorderLayout;
import java.awt.Desktop;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

	/**
	 * DocumentPanel displays the documents of a course and allows the user to import, export and delete files.
	 * 
	 * @author katharina
	 *
	 */
	public class DocumentPanel {
		private DefaultListModel <File> listModel;
		private Course course;
		
		public DocumentPanel(JPanel containerPanel, Course course) {
			this.course = course;
			
			//panel displaying list of documents and buttons
			JPanel topPanel = new JPanel();
			JPanel documentPanel = new JPanel();
			JPanel btnPanel = new JPanel();
			documentPanel.setLayout(new BorderLayout());
			btnPanel.setLayout(new FlowLayout());
			
			
			//getting list of documents of current course
			ArrayList<File> documents = course.getDocuments();
			listModel = new DefaultListModel<>();
			for (int i = 0; i < documents.size(); i++) {
				listModel.addElement(documents.get(i));
			}
			
			//displaying list in vertical order and several columns
			JList<File> list = new JList<>(listModel);
			list.setLayoutOrientation(JList.VERTICAL_WRAP);
			list.setVisibleRowCount(-1);
			list.setCellRenderer(new FileListRenderer());
			
			JButton openBtn = new JButton("Open");
			JButton importBtn = new JButton("Import");
			JButton exportBtn = new JButton("Export");
			JButton deleteBtn = new JButton("Delete");
			openBtn.setEnabled(false);
			exportBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			
			topPanel.add(list, BorderLayout.CENTER);
			topPanel.add(btnPanel, BorderLayout.SOUTH);
			btnPanel.add(openBtn);
			btnPanel.add(importBtn);
			btnPanel.add(exportBtn);
			btnPanel.add(deleteBtn);
			
			containerPanel.add(topPanel);
			
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
		private void updateList() {
			listModel.removeAllElements();
			ArrayList<File> documents = course.getDocuments();
			for (int i = 0; i < documents.size(); i++){
				listModel.addElement(documents.get(i));
			}
		}
		


}
