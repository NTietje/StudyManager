import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.*;

import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;


/**
 * EventCreator creates an event based on the user's input and adds the event to the StudyUnit specified in the arguments of the constructor.
 *
 * @author katharina
 */
public class EventCreator extends JFrame {
    private JTextField titleField;
    private String titleString;
    /*private JTextField locationField;
    private String locationString;*/
    private JDateChooser dateChooser;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JTextArea descrArea;
    private JComboBox reminderBox;
    private String[] reminderTimes = {"Minutes before", "Hours before", "Days before", "Weeks before"};
    private JTextField reminderField;
    private StudyUnit unit;

    public EventCreator(StudyUnit unit) {

        this.unit = unit;

        JPanel reminderPanel = new JPanel();
        JPanel btnPanel = new JPanel();
        JPanel timeLabelPanel = new JPanel();
        JPanel remLabelPanel = new JPanel();
        JPanel descrLabelPanel = new JPanel();
        JPanel datePanel = new JPanel();

        titleField = new JTextField(20);
        titleString = "Title of event";
        titleField.setText(titleString);
        Font font = new Font(null, Font.BOLD, 13);
        titleField.setFont(font);


        /*locationField = new JTextField(20);
        locationString = "Location";
        locationField.setText(locationString);*/

        reminderField = new JTextField(3);

        JLabel timeLabel = new JLabel ("Date and time");
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd.MM.yyyy");
        dateChooser.setDate(new java.util.Date());
        JLabel reminderLabel = new JLabel ("Reminder");
        JLabel descrLabel = new JLabel ("Description");
        reminderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        descrLabel.setHorizontalAlignment(SwingConstants.LEFT);

        SpinnerNumberModel hourModel = new SpinnerNumberModel(00, 00, 23, 1);
        hourSpinner = new JSpinner(hourModel);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel (00,00,59, 1);
        minuteSpinner = new JSpinner(minuteModel);
        JLabel clockLabel = new JLabel (" : ");

        reminderBox = new JComboBox(reminderTimes);
        reminderBox.setSelectedIndex(-1);

        descrArea = new JTextArea(4,10);
        descrArea.setLineWrap(true);
        JScrollPane descrScroll = new JScrollPane(descrArea);

        JButton confirmBtn = new JButton("Confirm");
        JButton cancelBtn = new JButton("Cancel");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        add(titleField);
        //add(locationField);
        add(timeLabelPanel);
        add(datePanel);
        add(remLabelPanel);
        add(reminderPanel);
        add(descrLabelPanel);
        add(descrScroll);
        add(btnPanel);

        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.LINE_AXIS));
        datePanel.add(dateChooser);
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(hourSpinner);
        datePanel.add(clockLabel);
        datePanel.add(minuteSpinner);

        remLabelPanel.setLayout(new BorderLayout());
        remLabelPanel.add(reminderLabel, BorderLayout.WEST);

        reminderPanel.setLayout(new BorderLayout());
        reminderPanel.add(reminderField, BorderLayout.WEST);
        reminderPanel.add(reminderBox, BorderLayout.CENTER);

        descrLabelPanel.setLayout(new BorderLayout());
        descrLabelPanel.add(descrLabel, BorderLayout.WEST);

        timeLabelPanel.setLayout(new BorderLayout());
        timeLabelPanel.add(timeLabel, BorderLayout.WEST);

        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(confirmBtn);
        btnPanel.add(cancelBtn);

        //remove initial text if field gains focus and replace initial text if it loses focus
        titleField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e){
                if (titleField.getText().equals(titleString)) {
                    titleField.setText("");
                    titleField.setForeground(Color.BLACK);
                }
            }

            public void focusLost (FocusEvent e){
                if (titleField.getText().equals("")) {
                    titleField.setText(EventCreator.this.titleString);
                }
            }
        });

        /*locationField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e){
                if (locationField.getText().equals(locationString)) {
                    locationField.setText("");
                }
            }

            public void focusLost (FocusEvent e){
                if (locationField.getText().equals("")) {
                    locationField.setText(locationString);
                }
            }

        });*/

        //check whether input consists only of integers and indicate errors by changing the font color
        reminderField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                if (!reminderField.getText().matches("\\d*")) {
                    reminderField.setForeground(Color.RED);
                }
                else {
                    reminderField.setForeground(Color.BLACK);
                }

            }

            public void removeUpdate(DocumentEvent e){
                if (!reminderField.getText().matches("\\d*")) {
                    reminderField.setForeground(Color.RED);
                }
                else {
                    reminderField.setForeground(Color.BLACK);
                }

            }

            public void insertUpdate(DocumentEvent e){
                if (!reminderField.getText().matches("\\d*")) {
                    reminderField.setForeground(Color.RED);
                }
            }

        });

        //put focus on dateChooser when window gains focus
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e){
                dateChooser.requestFocusInWindow();
            }
        });

        //check input and create event
        confirmBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean minute = false;
                boolean hour = false;
                boolean day = false;
                boolean week = false;
                int remindTime = 0;

                //obligatory field title is empty or initial value hasn't been overwritten
                if (titleField.getText().equals("") || titleField.getText().equals(titleString)){
                    titleField.setText("Please enter a title");
                    titleField.setForeground(Color.RED);
                }
                //obligatory field date is empty
                else if (dateChooser.getDate() == null){
                    dateChooser.setDate(new java.util.Date());
                    dateChooser.setForeground(Color.RED);
                    System.out.println("Datum fehlt");
                }
                else {
                    //get title from field
                    String title = titleField.getText();
                    //get location
                    String location = "";
                    /*if (!locationField.getText().equals("") || locationField.getText().equals(locationString)) {
                        location = locationField.getText();
                    }*/

                    //get date and time
                    java.util.Date date = dateChooser.getDate();
                    GregorianCalendar gregCal = new GregorianCalendar();
                    gregCal.setTime(date);
                    gregCal.set(GregorianCalendar.HOUR_OF_DAY, (int) hourSpinner.getValue());
                    gregCal.set(GregorianCalendar.MINUTE, (int) minuteSpinner.getValue());

                    //get description
                    String description = descrArea.getText();

                    //get reminder time
                    if (reminderBox.getSelectedItem() == null) {

                    }
                    else if (reminderBox.getSelectedItem().equals(reminderTimes[0])) {
                        minute = true;
                    }
                    else if (reminderBox.getSelectedItem().equals(reminderTimes[1])){
                        hour = true;
                    }
                    else if (reminderBox.getSelectedItem().equals(reminderTimes[2])){
                        day = true;
                    }
                    else {
                        week = true;
                    }

                    if (!reminderField.getText().equals("")){
                        remindTime = Integer.valueOf(reminderField.getText());
                    }

                    //create event
                    Event event = new Event(title, description, gregCal);
                    event.setReminder(remindTime, minute, hour, day, week);

                    try {
                        Semester semester = (Semester) EventCreator.this.unit;
                        semester.addEvent(event);

                    }
                    catch (ClassCastException ex ){
                        Course course = (Course) EventCreator.this.unit;
                        course.addEvent(event);
                    }


                    //close window
                    dispose();


                }
            }

        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        setSize(250,300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }


}
