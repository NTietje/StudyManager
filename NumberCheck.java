import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * NumberCheck monitors the input in a JTextField. If the user enters a character that is not a digit the text colour changes to red.
 * @author katharina
 */
public class NumberCheck implements DocumentListener {
    private JTextField textField;

    public NumberCheck(JTextField textField){
        this.textField = textField;
    }

    public void changedUpdate(DocumentEvent e) {
        checkInputForNumbers();

    }

    public void removeUpdate(DocumentEvent e){
        checkInputForNumbers();

    }

    public void insertUpdate(DocumentEvent e){
       checkInputForNumbers();
    }

    /**
     * checks whether the input consists only of digits and changes the text colour accordingly
     */
    private void checkInputForNumbers() {
        if (!textField.getText().matches("\\d*")) {
            textField.setForeground(Color.RED);
        }
        else {
            textField.setForeground(Color.BLACK);
        }

    }


}
