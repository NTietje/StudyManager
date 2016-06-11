package lib;

import java.awt.Dimension;

import javax.swing.JButton;

public class SemesterButton extends JButton{

	public SemesterButton(String title) {
		super(title);
		setPreferredSize(new Dimension(200, 70));
		setMaximumSize(new Dimension(200, 70));
		setFont(this.getFont().deriveFont(16.0f));
	}
	
}
