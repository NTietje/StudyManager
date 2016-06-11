package lib;

import javax.swing.SwingUtilities;

public class StudyController {
	
	private StudyManager manager = new StudyManager();
	
	public StudyController() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				UniplanerGUI gui = new UniplanerGUI(manager);
				
			}
		});
	}
	
	public static void main(String[] args) {
		new StudyController();
	}
	

}
