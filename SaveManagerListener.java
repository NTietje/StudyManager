//package lib;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SaveManagerListener extends WindowAdapter {
	
	private StudyManager manager = new StudyManager();
	
	public SaveManagerListener(StudyManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		StateSaver.saveState(manager);
		System.out.println("manager gespeichert");
	}
	

	
}

