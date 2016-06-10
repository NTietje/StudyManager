import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

/**
 * FileListRenderer determines how files should be displayed in the list (system specific icon + file name).
 * @author katharina
 * @version 1.0
 *
 */
public class FileListRenderer extends JLabel implements ListCellRenderer <File> {
	
	public FileListRenderer() {
		setOpaque(true);
	}

	/**
	 * sets text, icon and background color to represent individual item in the list
	 */
	@Override
	public Component getListCellRendererComponent(JList <? extends File> list, File value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		setText(value.getName());
		//icon displaying file type
		setIcon(FileSystemView.getFileSystemView().getSystemIcon(value));
		
		//change background color if item is selected
		if (isSelected){
			setBackground(new Color(240,240,240));
		}
		else {
			setBackground(Color.WHITE);
		}
		return this;
	}

}
