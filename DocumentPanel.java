
	import java.awt.BorderLayout;
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
			
			JButton importBtn = new JButton("Importieren");
			JButton exportBtn = new JButton("Exportieren");
			JButton deleteBtn = new JButton("Löschen");
			exportBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			
			topPanel.add(list, BorderLayout.CENTER);
			topPanel.add(btnPanel, BorderLayout.SOUTH);
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
						exportBtn.setEnabled(true);
						deleteBtn.setEnabled(true);
					}
					else {
						exportBtn.setEnabled(false);
						deleteBtn.setEnabled(false);
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
					int opt = fileChooser.showDialog(null, "Importieren");
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
					int opt = fileChooser.showDialog(null, "Exportieren");
					if (opt == JFileChooser.APPROVE_OPTION){
						File fileSource = list.getSelectedValue();
						Path sourcePath = Paths.get(fileSource.getAbsolutePath());
						File fileTarget = fileChooser.getSelectedFile();
						Path targetPath = Paths.get(fileTarget.getAbsolutePath());
						try {
							Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
						}
						catch (IOException ex){
							JOptionPane.showMessageDialog(null, "Die Datei konnte leider nicht exportiert werden.");
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
					int opt = JOptionPane.showConfirmDialog(null, "Willst du die Datei \"" + file.getName() + "\" wirklich löschen?", "Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
