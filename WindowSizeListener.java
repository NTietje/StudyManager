package lib;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowSizeListener extends ComponentAdapter{
	
	private UniplanerGUI gui;
	private StudyManager manager;
	
	public WindowSizeListener(UniplanerGUI gui, StudyManager manager) {
		this.gui = gui;
		this.manager = manager;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		super.componentResized(e);
		int height = gui.getHeight();
		int width = gui.getWidth();
		this.manager.setHeight(height);
		this.manager.setWidth(width);
	}
	
}
