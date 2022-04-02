package code.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.schedule.DateTimeFormatter;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class AddEventFrame extends JFrame implements WindowListener {

    private static final Pattern datePattern = Pattern.compile("^([1-9]|1[0-2])/([1-9]|[12]\\d|3[01])/(\\d\\d\\d\\d)$");
    private static final Pattern timePattern = Pattern.compile("^(1[012]|[1-9]):([0-5]\\d)(AM|am|PM|pm)$");

    private final BackgroundDaemon daemon;

    private JTextField nameField;
    private JTextField locationField;
    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;

    private JCheckBox remindHourBeforeCheckBox;
    private JCheckBox remindDayBeforeCheckBox;
    private JCheckBox remindWeekBeforeCheckBox;
    private JCheckBox remindMonthBeforeCheckBox;

    private Box badNameRow;
    private Box badDateRow;
    private Box badStartTimeRow;
    private Box badEndTimeRow;

    private JButton addSaveButton;

    private ScheduledEvent editTarget;
    
    public AddEventFrame(BackgroundDaemon daemon) {
        super("Add Event");
        this.daemon = daemon;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        addWindowListener(this);

        Box rowContainer = Box.createVerticalBox();
        rowContainer.setBorder(new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, UIConstants.PADDING, UIConstants.PADDING));
        add(rowContainer);
        Box row;

        FontManager fm = FontManager.getInstance();
        Font lightFont = fm.getLightFont();
        Font regularFont = fm.getRegularFont();

        JLabel label;

        row = Box.createHorizontalBox();
            label = new JLabel("Name:");
            label.setFont(regularFont);
            row.add(label);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            nameField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS);
            nameField.setFont(lightFont);
            nameField.addActionListener(this::formSubmitted);
            row.add(nameField);
        rowContainer.add(row);

        badNameRow = Box.createHorizontalBox();
            label = new JLabel("Please enter a name");
            label.setFont(regularFont);
            label.setForeground(Color.RED);
            badNameRow.add(label);
            badNameRow.add(Box.createHorizontalGlue());
            badNameRow.setVisible(false);
        rowContainer.add(badNameRow);

        row = Box.createHorizontalBox();
            label = new JLabel("Location (Optional):");
            label.setFont(regularFont);
            row.add(label);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            locationField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS);
            locationField.setFont(lightFont);
            locationField.addActionListener(this::formSubmitted);
            row.add(locationField);
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            label = new JLabel("Date:");
            label.setFont(regularFont);
            row.add(label);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            dateField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS);
            dateField.addActionListener(this::formSubmitted);
            dateField.setFont(lightFont);
            row.add(dateField);
        rowContainer.add(row);

        badDateRow = Box.createHorizontalBox();
            label = new JLabel("Please enter a valid date");
            label.setFont(regularFont);
            label.setForeground(Color.RED);
            badDateRow.add(label);
            badDateRow.add(Box.createHorizontalGlue());
            badDateRow.setVisible(false);
        rowContainer.add(badDateRow);

        row = Box.createHorizontalBox();
            label = new JLabel("Start time:");
            label.setFont(regularFont);
            row.add(label);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            startTimeField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS);
            startTimeField.setFont(lightFont);
            startTimeField.addActionListener(this::formSubmitted);
            row.add(startTimeField);
        rowContainer.add(row);

        badStartTimeRow = Box.createHorizontalBox();
            label = new JLabel("Please enter a valid time (e.g. 8:00AM)");
            label.setFont(regularFont);
            label.setForeground(Color.RED);
            badStartTimeRow.add(label);
            badStartTimeRow.add(Box.createHorizontalGlue());
            badStartTimeRow.setVisible(false);
        rowContainer.add(badStartTimeRow);

        row = Box.createHorizontalBox();
            label = new JLabel("End time:");
            label.setFont(regularFont);
            row.add(label);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            endTimeField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS);
            endTimeField.setFont(lightFont);
            endTimeField.addActionListener(this::formSubmitted);
            row.add(endTimeField);
        rowContainer.add(row);

        badEndTimeRow = Box.createHorizontalBox();
            label = new JLabel("Please enter a valid time (e.g. 8:00AM)");
            label.setFont(regularFont);
            label.setForeground(Color.RED);
            badEndTimeRow.add(label);
            badEndTimeRow.add(Box.createHorizontalGlue());
            badEndTimeRow.setVisible(false);
        rowContainer.add(badEndTimeRow);

        row = Box.createHorizontalBox();
            label = new JLabel("Remind me...");
            label.setFont(regularFont);
            row.add(label);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            remindHourBeforeCheckBox = new JCheckBox("One hour before");
            remindHourBeforeCheckBox.setFont(regularFont);
            row.add(remindHourBeforeCheckBox);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            remindDayBeforeCheckBox = new JCheckBox("One day before");
            remindDayBeforeCheckBox.setFont(regularFont);
            row.add(remindDayBeforeCheckBox);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            remindWeekBeforeCheckBox = new JCheckBox("One week before");
            remindWeekBeforeCheckBox.setFont(regularFont);
            row.add(remindWeekBeforeCheckBox);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            remindMonthBeforeCheckBox= new JCheckBox("One month before");
            remindMonthBeforeCheckBox.setFont(regularFont);
            row.add(remindMonthBeforeCheckBox);
            row.add(Box.createHorizontalGlue());
        rowContainer.add(row);

        row = Box.createHorizontalBox();
            addSaveButton = new JButton("Add Event");
            addSaveButton.setFont(regularFont);
            Dimension preferred = addSaveButton.getPreferredSize();
            addSaveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferred.height));
            addSaveButton.addActionListener(this::formSubmitted);
            row.add(addSaveButton);
        rowContainer.add(row);

        pack(); 
    }

    private ScheduledEvent validateForm() {
        String name = nameField.getText();
        String location = locationField.getText();
        String dateString = dateField.getText();
        String startTimeString = startTimeField.getText();
        String endTimeString = endTimeField.getText();

        LocalDate d = null;
        LocalTime startTime = null;
        LocalTime endTime = null;

        Matcher m = datePattern.matcher(dateString);
        if (m.matches()) {
            int month = Integer.parseInt(m.group(1));
            int day = Integer.parseInt(m.group(2));
            int year = Integer.parseInt(m.group(3));
            d = LocalDate.of(year, month, day);
        }

        m = timePattern.matcher(startTimeString);
        if (m.matches()) {
            int hour = Integer.parseInt(m.group(1));
            int minute = Integer.parseInt(m.group(2));
            boolean pm = m.group(3).equalsIgnoreCase("pm");
            if (pm) {
                hour += 12;
            } else if (hour == 12) {
                hour = 0;
            }
            startTime = LocalTime.of(hour, minute, 0);
        }

        m = timePattern.matcher(endTimeString);
        if (m.matches()) {
            int hour = Integer.parseInt(m.group(1));
            int minute = Integer.parseInt(m.group(2));
            boolean pm = m.group(3).equalsIgnoreCase("pm");
            if (pm) {
                hour += 12;
            } else if (hour == 12) {
                hour = 0;
            }
            endTime = LocalTime.of(hour, minute, 0);
        }

        boolean errors = false;
        if (name.isEmpty()) {
            errors = true;
            badNameRow.setVisible(true);
        } else {
            badNameRow.setVisible(false);
        }
        
        if (d == null) {
            errors = true;
            badDateRow.setVisible(true);
        } else {
            badDateRow.setVisible(false);
        }

        if (startTime == null) {
            errors = true;
            badStartTimeRow.setVisible(true);
        } else {
            badStartTimeRow.setVisible(false);
        }

        if (endTime == null) {
            errors = true;
            badEndTimeRow.setVisible(true);
        } else {
            badEndTimeRow.setVisible(false);
        }

        if (errors) {
            pack();
            return null;
        }

        return new ScheduledEvent(name, d, startTime, endTime, location);
    }

    private void formSubmitted(ActionEvent e) {
        ScheduledEvent event = validateForm();
        if (validateForm() == null)
            return;
        if (editTarget != null) {
            daemon.cancel(editTarget.getDate(), editTarget);
            daemon.getDayViewFrame().setEnabled(true);
            editTarget = null;
        }
        setVisible(false);
        clearForm();
        daemon.add(event.getDate(), event);
    }

    public void appearForEdit(Component owner, ScheduledEvent editTarget) {
        if (this.editTarget != null) {
            cancelEdit();
        }
        daemon.getDayViewFrame().setEnabled(false);

        this.editTarget = editTarget;

        nameField.setText(editTarget.getName());
        locationField.setText(editTarget.getLocation());
        dateField.setText(DateTimeFormatter.toSlashString(editTarget.getDate()));
        startTimeField.setText(DateTimeFormatter.toAmPm(editTarget.getStartTime()));
        endTimeField.setText(DateTimeFormatter.toAmPm(editTarget.getEndTime()));

        setTitle("Edit Event");
        addSaveButton.setText("Update Event");
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public void appear(Component owner, LocalDate d) {
        if (editTarget != null) {
            cancelEdit();
        }
        nameField.setText("");
        locationField.setText("");
        dateField.setText(DateTimeFormatter.toSlashString(d));
        startTimeField.setText("8:00AM");
        endTimeField.setText("9:00AM");

        setTitle("Add Event");
        addSaveButton.setText("Add Event");

        nameField.requestFocus();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public void appear(Component owner, LocalDate d, LocalTime start, LocalTime end) {
        if (editTarget != null) {
            cancelEdit();
        }
        nameField.setText("");
        locationField.setText("");
        dateField.setText(DateTimeFormatter.toSlashString(d));
        startTimeField.setText(DateTimeFormatter.toAmPm(start));
        endTimeField.setText(DateTimeFormatter.toAmPm(end));

        setTitle("Add Event");
        addSaveButton.setText("Add Event");

        nameField.requestFocus();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    
    public void appear(LocalDateTime dt) {
        nameField.setText("");
        locationField.setText("");
        dateField.setText(DateTimeFormatter.toSlashString(dt));
        startTimeField.setText(DateTimeFormatter.toAmPm(dt));
        endTimeField.setText(DateTimeFormatter.toAmPm(dt.plusHours(1)));

        nameField.requestFocus();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cancelEdit() {
        editTarget = null;
        daemon.getDayViewFrame().setEnabled(true);
    }

    private void clearForm() {
        nameField.setText("");
        locationField.setText("");
        dateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");

        badNameRow.setVisible(false);
        badDateRow.setVisible(false);
        badStartTimeRow.setVisible(false);
        badEndTimeRow.setVisible(false);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (editTarget != null) {
            cancelEdit();
        }       
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

}
