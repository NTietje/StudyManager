import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * EventEditor allows the user to modify or delete an event.
 * @author katharina
 */
public class EventEditor extends JFrame {
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
    private Event event;
    private StudyUnit studyUnit;

    public EventEditor(StudyUnit studyUnit, Event event){
        this.event = event;
        this.studyUnit = studyUnit;

        GregorianCalendar gregEvent = event.getDate();
        //create components for user input
        JPanel reminderPanel = new JPanel();
        JPanel btnPanel = new JPanel();
        JPanel timeLabelPanel = new JPanel();
        JPanel remLabelPanel = new JPanel();
        JPanel descrLabelPanel = new JPanel();
        JPanel datePanel = new JPanel();

        titleField = new JTextField(20);
        titleString = event.getTitle();
        titleField.setText(titleString);
        Font font = new Font(null, Font.BOLD, 13);
        titleField.setFont(font);


        /*locationField = new JTextField(20);
        locationString = "Location";
        locationField.setText(locationString);*/


        JLabel timeLabel = new JLabel ("Date and time");
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd.MM.yyyy");
        dateChooser.setDate(event.getDate().getTime());
        JLabel reminderLabel = new JLabel ("Reminder");
        JLabel descrLabel = new JLabel ("Description");
        reminderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        descrLabel.setHorizontalAlignment(SwingConstants.LEFT);

        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 00, 23, 1);
        hourSpinner = new JSpinner(hourModel);
        hourSpinner.setValue(gregEvent.get(Calendar.HOUR_OF_DAY));
        SpinnerNumberModel minuteModel = new SpinnerNumberModel (0,00,59, 1);
        minuteSpinner = new JSpinner(minuteModel);
        minuteSpinner.setValue(gregEvent.get(Calendar.MINUTE));
        JLabel clockLabel = new JLabel (" : ");

        reminderField = new JTextField(3);

        reminderBox = new JComboBox(reminderTimes);

        //display remind date in reminderBox and reminderField
        boolean[] remindInterval = event.getRemindInterval();
        for (int i = 0; i< remindInterval.length; i++){
            if (remindInterval[i]) {
                reminderBox.setSelectedIndex(i);
                break;
            }
        }
        reminderField.setText(String.valueOf(event.getRemindTime()));


        descrArea = new JTextArea(4,10);
        descrArea.setLineWrap(true);
        descrArea.setText(event.getDescription());
        JScrollPane descrScroll = new JScrollPane(descrArea);

        JButton confirmBtn = new JButton("Confirm");
        JButton deleteBtn = new JButton("Delete");

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
        btnPanel.add(deleteBtn);

        //monitor input of reminderField
        reminderField.getDocument().addDocumentListener(new NumberCheck(reminderField));


        //check input and create event
        confirmBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean minute = false;
                boolean hour = false;
                boolean day = false;
                boolean week = false;
                int remindTime = 0;

                //obligatory field title is empty or initial value hasn't been overwritten
                if (titleField.getText().equals("")){
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
                    //update title
                    String title = titleField.getText();
                    EventEditor.this.event.setTitle(title);
                    //get location
                    String location = "";
                    /*if (!locationField.getText().equals("") || locationField.getText().equals(locationString)) {
                        location = locationField.getText();
                    }*/

                    //update date and time
                    java.util.Date date = dateChooser.getDate();
                    GregorianCalendar gregCal = new GregorianCalendar();
                    gregCal.setTime(date);
                    gregCal.set(GregorianCalendar.HOUR_OF_DAY, (int) hourSpinner.getValue());
                    gregCal.set(GregorianCalendar.MINUTE, (int) minuteSpinner.getValue());
                    EventEditor.this.event.setDate(gregCal);

                    //update description
                    String description = descrArea.getText();
                    EventEditor.this.event.setDescription(description);

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

                    //update reminder
                    EventEditor.this.event.setReminder(remindTime, minute, hour, day, week);


                    //close window
                    dispose();


                }
            }

        });

        //delete event and close frame
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                EventEditor.this.studyUnit.removeEvent(EventEditor.this.event);
                dispose();
            }
        });

        setVisible(true);
        setSize(250,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
