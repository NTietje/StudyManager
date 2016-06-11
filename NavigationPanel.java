//package lib;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel{
	
	private JButton returnBtn;
	private JButton deletButton;
	private JLabel navigationLabel;
	
	public NavigationPanel() {
		returnBtn = new JButton("<<");
		deletButton = new JButton("Delet");
		navigationLabel = new JLabel("");
		navigationLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
		leftPanel.add(returnBtn);
		leftPanel.add(navigationLabel);
		
		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);
		add(deletButton, BorderLayout.EAST);
		
	}
	
	public void setRemoveText(String text) {
		deletButton.setText(text);
	}
	
	public JButton getDeletButton() {
		return deletButton;
	}
	
	public JButton getReturnButton() {
		return returnBtn;
	}
	
	public void setText(String text) {
		navigationLabel.setText(text);
	}
	
	public String getText() {
		return navigationLabel.getText();
	}

}
