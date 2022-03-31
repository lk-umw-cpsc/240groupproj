package code.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import code.BackgroundDaemon;
import code.schedule.ScheduledReminder;
import code.ui.fonts.FontManager;

public class AddReminderFrame extends JFrame implements WindowListener {

    private static final Pattern datePattern = Pattern.compile("^([1-9]|1[0-2])/([1-9]|[12]\\d|3[01])/(\\d\\d\\d\\d)$");
    private static final Pattern timePattern = Pattern.compile("^(1[012]|[1-9]):([0-5]\\d)(AM|am|PM|pm)$");

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField dateField;
    private JTextField timeField;

    private JCheckBox repeatCheckbox;
    private JTextField repeatField;
    private Box repeatLayer;
    private JButton addSaveButton;

    private Box badNameLayer;
    private Box badDateLayer;
    private Box badTimeLayer;
    private Box timeIsInThePastLayer;
    private Box badRepeatLayer;

    private BackgroundDaemon daemon;

    private boolean editMode;
    private ScheduledReminder editTarget;
        
    public AddReminderFrame(BackgroundDaemon daemon) {
        super("Create new reminder");
        this.daemon = daemon;
        setResizable(false);
    }
    
    public void build() {
        addWindowListener(this);

        FontManager fm = FontManager.getInstance();
        Font regularFont = fm.getRegularFont();
        Font lightFont = fm.getLightFont();

        JLabel label;

        Box horizontallyPaddedPanel = Box.createHorizontalBox();
        Box layerPanel = Box.createVerticalBox();

        horizontallyPaddedPanel.add(Box.createHorizontalStrut(UIConstants.PADDING));
        horizontallyPaddedPanel.add(layerPanel);
        horizontallyPaddedPanel.add(Box.createHorizontalStrut(UIConstants.PADDING));
        
        layerPanel.add(Box.createVerticalStrut(UIConstants.PADDING));
        // Create another box whose children are displayed left to right
        Box layer = Box.createHorizontalBox();
            label = new JLabel("Name:");
            label.setFont(regularFont);
            layer.add(label);
            // Add horizontal glue after the label, left-aligning the label
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);
            nameField = new JTextField(24);
            nameField.setFont(lightFont);
        layerPanel.add(nameField);

        badNameLayer = Box.createHorizontalBox();
            JLabel errorLabel = new JLabel("Please enter a name");
            errorLabel.setFont(regularFont);
            errorLabel.setForeground(Color.RED);
            badNameLayer.add(errorLabel);
            badNameLayer.add(Box.createHorizontalGlue());
            badNameLayer.setVisible(false);
        layerPanel.add(badNameLayer);

        layer = Box.createHorizontalBox();
            label = new JLabel("Description (optional):");
            label.setFont(regularFont);
            layer.add(label);
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

            descriptionField = new JTextField(24);
            descriptionField.setFont(lightFont);
        layerPanel.add(descriptionField);

        layer = Box.createHorizontalBox();
            label = new JLabel("Date:");
            label.setFont(regularFont);
            layer.add(label);
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

        layer = Box.createHorizontalBox();
            dateField = new JTextField(24);
            dateField.setFont(lightFont);
            layer.add(dateField);

            JButton calendarButton = new JButton("...");
            calendarButton.addActionListener(this::showCalendarPressed);
            // layer.add(calendarButton);
        layerPanel.add(layer);

        badDateLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a valid date");
            errorLabel.setFont(regularFont);
            errorLabel.setForeground(Color.RED);
            badDateLayer.add(errorLabel);
            badDateLayer.add(Box.createHorizontalGlue());
            badDateLayer.setVisible(false);
        layerPanel.add(badDateLayer);

        layer = Box.createHorizontalBox();
            label = new JLabel("Time (optional):");
            label.setFont(regularFont);
            layer.add(label);
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

            timeField = new JTextField(24);
            timeField.setFont(lightFont);
        layerPanel.add(timeField);

        badTimeLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a valid time (e.g. 6:00AM)");
            errorLabel.setFont(regularFont);
            errorLabel.setForeground(Color.RED);
            badTimeLayer.add(errorLabel);
            badTimeLayer.add(Box.createHorizontalGlue());
            badTimeLayer.setVisible(false);
        layerPanel.add(badTimeLayer);

        timeIsInThePastLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a future date & time");
            errorLabel.setFont(regularFont);
            errorLabel.setForeground(Color.RED);
            timeIsInThePastLayer.add(errorLabel);
            timeIsInThePastLayer.add(Box.createHorizontalGlue());
            timeIsInThePastLayer.setVisible(false);
        layerPanel.add(timeIsInThePastLayer);

        layer = Box.createHorizontalBox();
            repeatCheckbox = new JCheckBox("Repeat");
            repeatCheckbox.setFont(regularFont);
            repeatCheckbox.addActionListener(this::repeatCheckboxChanged);
            layer.add(repeatCheckbox);
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

        repeatLayer = Box.createHorizontalBox();
            label = new JLabel("Every ");
            label.setFont(regularFont);
            repeatLayer.add(label);
            repeatField = new JTextField(2);
            repeatField.setFont(lightFont);
            repeatField.setMaximumSize(repeatField.getPreferredSize());
            repeatLayer.add(repeatField);
            label = new JLabel(" days");
            label.setFont(regularFont);
            repeatLayer.add(label);
            repeatLayer.add(Box.createHorizontalGlue());
        repeatLayer.setVisible(false);
        layerPanel.add(repeatLayer);

        badRepeatLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a positive number");
            errorLabel.setFont(regularFont);
            errorLabel.setForeground(Color.RED);
            badRepeatLayer.add(errorLabel);
            badRepeatLayer.add(Box.createHorizontalGlue());
            badRepeatLayer.setVisible(false);
        layerPanel.add(badRepeatLayer);

        layer = Box.createHorizontalBox();
            addSaveButton = new JButton("Add Reminder");
            addSaveButton.setFont(regularFont);
            addSaveButton.addActionListener(this::addReminderPressed);
            Dimension d = addSaveButton.getPreferredSize();
            addSaveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.height));
        layer.add(addSaveButton);
        layerPanel.add(layer);

        layerPanel.add(Box.createVerticalStrut(UIConstants.PADDING));

        add(horizontallyPaddedPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear(ScheduledReminder r) {
        if (editMode) {
            cancelEditMode();
        }
        editTarget = r;
        Lock lock = daemon.getLock();
        lock.lock();
        daemon.getReminders().remove(r);
        lock.unlock();
        editMode = true;
        badDateLayer.setVisible(false);
        badNameLayer.setVisible(false);
        badTimeLayer.setVisible(false);
        badRepeatLayer.setVisible(false);
        timeIsInThePastLayer.setVisible(false);

        nameField.setText(r.getName());
        descriptionField.setText(r.getDescription());
        LocalDateTime dt = r.getWhenDue();
        dateField.setText(dt.getMonthValue() + "/" + dt.getDayOfMonth() + "/" + dt.getYear());
        int hour = dt.getHour();
        if (hour < 12) {
            if (hour == 0) {
                hour = 12;
            }
            timeField.setText(String.format("%d:%02dAM", hour, dt.getMinute()));
        } else {
            if (hour > 12) {
                hour = hour % 12;
            }
            timeField.setText(String.format("%d:%02dPM", hour, dt.getMinute()));
        }
        if (r.repeats()) {
            repeatField.setText(Integer.toString(r.getDaysBetweenRepetitions()));
            repeatCheckbox.setSelected(true);
            repeatLayer.setVisible(true);
        } else {
            repeatField.setText("");
            repeatCheckbox.setSelected(false);
            repeatLayer.setVisible(false);
        }
        daemon.getReminderManagerFrame().setEnabled(false);
        addSaveButton.setText("Update Reminder");
        nameField.requestFocus();
        pack();
        setVisible(true);
    }

    public void appear(LocalDateTime dt) {
        if (editMode) {
            cancelEditMode();
        }

        badDateLayer.setVisible(false);
        badNameLayer.setVisible(false);
        badTimeLayer.setVisible(false);
        badRepeatLayer.setVisible(false);
        timeIsInThePastLayer.setVisible(false);

        nameField.setText("");
        descriptionField.setText("");
        dateField.setText(dt.getMonthValue() + "/" + dt.getDayOfMonth() + "/" + dt.getYear());
        int hour = dt.getHour();
        if (hour < 12) {
            if (hour == 0) {
                hour = 12;
            }
            timeField.setText(String.format("%d:%02dAM", hour, dt.getMinute()));
        } else {
            if (hour > 12) {
                hour = hour % 12;
            }
            timeField.setText(String.format("%d:%02dPM", hour, dt.getMinute()));
        }
        repeatField.setText("");
        repeatCheckbox.setSelected(false);
        repeatLayer.setVisible(false);
        
        addSaveButton.setText("Add Reminder");
        nameField.requestFocus();
        pack();
        setVisible(true);
    }

    public void appear(int daysBetweenRepeats) {
        if (editMode) {
            cancelEditMode();
        }
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);

        if (daysBetweenRepeats > 0) {
            repeatCheckbox.setSelected(true);
            repeatField.setText(Integer.toString(daysBetweenRepeats));
            repeatLayer.setVisible(true);
        } else {
            repeatCheckbox.setSelected(false);
            repeatField.setText("");
            repeatLayer.setVisible(false);
        }
        badDateLayer.setVisible(false);
        badNameLayer.setVisible(false);
        badTimeLayer.setVisible(false);
        badRepeatLayer.setVisible(false);
        timeIsInThePastLayer.setVisible(false);

        nameField.setText("");
        descriptionField.setText("");
        dateField.setText(now.getMonthValue() + "/" + now.getDayOfMonth() + "/" + now.getYear());
        timeField.setText("");

        addSaveButton.setText("Add Reminder");
        nameField.requestFocus();
        pack();
        setVisible(true);
    }

    private void repeatCheckboxChanged(ActionEvent e) {
        if (repeatCheckbox.isSelected()) {
            repeatField.setText("1");
            repeatLayer.setVisible(true);
            badRepeatLayer.setVisible(false);
            pack();
        } else {
            repeatField.setText("");
            repeatLayer.setVisible(false);
            badRepeatLayer.setVisible(false);
            pack();
        }
    }

    private void showCalendarPressed(ActionEvent e) {
        // hook up with month view here
    }

    private void addReminderPressed(ActionEvent e) {
        // ^([1-9]|1[0-2])/([1-9]|[12]\d|3[01])/(\d\d\d\d)$
        // ^(1[012]|[1-9]):([0-5]\d)(AM|am|PM|pm)$
        String name = nameField.getText();
        String description = descriptionField.getText();
        String time = timeField.getText();
        String date = dateField.getText();

        boolean formContainsErrors = false;

        if (name.isEmpty()) {
            formContainsErrors = true;
            badNameLayer.setVisible(true);
        } else {
            badNameLayer.setVisible(false);
        }
        Matcher matcher;
        int hour = -1;
        int minute = -1;
        if (!time.isEmpty()) {
            matcher = timePattern.matcher(time);
            if (!matcher.matches()) {
                formContainsErrors = true;
                badTimeLayer.setVisible(true);
                timeIsInThePastLayer.setVisible(false);
            } else {
                hour = Integer.parseInt(matcher.group(1));
                minute = Integer.parseInt(matcher.group(2));
                String amPM = matcher.group(3);
                if (amPM.equalsIgnoreCase("pm")) {
                    if (hour < 12)
                        hour += 12;
                } else if (hour == 12) {
                    hour = 0;
                }
            }
        } else {
            badTimeLayer.setVisible(false);
        }

        int month = -1;
        int day = -1;
        int year = -1;
        matcher = datePattern.matcher(date);
        if (!matcher.matches()) {
            formContainsErrors = true;
            badDateLayer.setVisible(true);
        } else {
            month = Integer.parseInt(matcher.group(1));
            day = Integer.parseInt(matcher.group(2));
            year = Integer.parseInt(matcher.group(3));
            badDateLayer.setVisible(false);
        }
        int days = 0;
        boolean repeatError = false;
        if (repeatCheckbox.isSelected()) {
            try {
                days = Integer.parseUnsignedInt(repeatField.getText());
                if (days < 1) {
                    formContainsErrors = true;
                    repeatError = true;
                }
            } catch (NumberFormatException err) {
                formContainsErrors = true;
                repeatError = true;
            }
        }
        badRepeatLayer.setVisible(repeatError);
        LocalDateTime now = LocalDateTime.now();

        if (formContainsErrors) {
            pack();
            return;
        }
        LocalDateTime scheduledDateTime = LocalDateTime.of(year, month, day, hour, minute);
        if (scheduledDateTime.compareTo(now) <= 0) {
            timeIsInThePastLayer.setVisible(true);
            pack();
            return;
        } else {
            timeIsInThePastLayer.setVisible(false);
        }
        pack();

        ScheduledReminder r = new ScheduledReminder(name, description, scheduledDateTime, days);

        daemon.add(r);
        if (editMode) {
            daemon.getReminderManagerFrame().setEnabled(true);
            editMode = false;
            editTarget = null;
        }
        setVisible(false);
    }

    private void cancelEditMode() {
        daemon.getReminderManagerFrame().setEnabled(true);
        daemon.add(editTarget);
        editMode = false;
        editTarget = null;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (editMode) {
            cancelEditMode();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

}