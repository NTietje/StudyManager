//package lib;

import java.awt.Dimension;

import javax.swing.JButton;

public class CourseButton extends JButton{
	
	public CourseButton(String title) {
		super(title);
		setPreferredSize(new Dimension(140, 70));
		setMaximumSize(new Dimension(120, 70));
		setFont(this.getFont().deriveFont(16.0f));
	}

}
