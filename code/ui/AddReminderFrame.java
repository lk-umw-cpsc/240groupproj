package code.ui;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
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

public class AddReminderFrame extends JFrame {

    private static final Pattern datePattern = Pattern.compile("^([1-9]|1[0-2])/([1-9]|[12]\\d|3[01])/(\\d\\d\\d\\d)$");
    private static final Pattern timePattern = Pattern.compile("^(1[012]|[1-9]):([0-5]\\d)(AM|am|PM|pm)$");

    private static final int PADDING = 16;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField dateField;
    private JTextField timeField;

    private JCheckBox repeatCheckbox;
    private JTextField repeatField;
    private Box repeatLayer;

    private Box badNameLayer;
    private Box badDateLayer;
    private Box badTimeLayer;
    private Box timeIsInThePastLayer;
    private Box badRepeatLayer;

    private BackgroundDaemon daemon;
        
    public AddReminderFrame(BackgroundDaemon daemon) {
        super("Create new reminder");
        this.daemon = daemon;
        setResizable(false);
    }
    
    public void build() {
        Box horizontallyPaddedPanel = Box.createHorizontalBox();
        Box layerPanel = Box.createVerticalBox();

        horizontallyPaddedPanel.add(Box.createHorizontalStrut(PADDING));
        horizontallyPaddedPanel.add(layerPanel);
        horizontallyPaddedPanel.add(Box.createHorizontalStrut(PADDING));
        
        layerPanel.add(Box.createVerticalStrut(PADDING));
        // Create another box whose children are displayed left to right
        Box layer = Box.createHorizontalBox();

            layer.add(new JLabel("Name:"));
            // Add horizontal glue after the label, left-aligning the label
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);
            nameField = new JTextField(24);
        layerPanel.add(nameField);

        badNameLayer = Box.createHorizontalBox();
            JLabel errorLabel = new JLabel("Please enter a name");
            errorLabel.setForeground(Color.RED);
            badNameLayer.add(errorLabel);
            badNameLayer.add(Box.createHorizontalGlue());
            badNameLayer.setVisible(false);
        layerPanel.add(badNameLayer);

        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Description (optional):"));
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

            descriptionField = new JTextField(24);
        layerPanel.add(descriptionField);

        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Date:"));
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

        layer = Box.createHorizontalBox();
            dateField = new JTextField(24);
            layer.add(dateField);

            JButton calendarButton = new JButton("...");
            calendarButton.addActionListener(this::showCalendarPressed);
            // layer.add(calendarButton);
        layerPanel.add(layer);

        badDateLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a valid date");
            errorLabel.setForeground(Color.RED);
            badDateLayer.add(errorLabel);
            badDateLayer.add(Box.createHorizontalGlue());
            badDateLayer.setVisible(false);
        layerPanel.add(badDateLayer);

        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Time (optional):"));
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

            timeField = new JTextField(24);
        layerPanel.add(timeField);

        badTimeLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a valid time (e.g. 6:00AM)");
            errorLabel.setForeground(Color.RED);
            badTimeLayer.add(errorLabel);
            badTimeLayer.add(Box.createHorizontalGlue());
            badTimeLayer.setVisible(false);
        layerPanel.add(badTimeLayer);

        timeIsInThePastLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a future date & time");
            errorLabel.setForeground(Color.RED);
            timeIsInThePastLayer.add(errorLabel);
            timeIsInThePastLayer.add(Box.createHorizontalGlue());
            timeIsInThePastLayer.setVisible(false);
        layerPanel.add(timeIsInThePastLayer);

        layer = Box.createHorizontalBox();
            repeatCheckbox = new JCheckBox("Repeat");
            repeatCheckbox.addActionListener(this::repeatCheckboxChanged);
            layer.add(repeatCheckbox);
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

        repeatLayer = Box.createHorizontalBox();
            repeatLayer.add(new JLabel("Every "));
            repeatField = new JTextField(2);
            repeatField.setMaximumSize(repeatField.getPreferredSize());
            repeatLayer.add(repeatField);
            repeatLayer.add(new JLabel(" days"));
            repeatLayer.add(Box.createHorizontalGlue());
        repeatLayer.setVisible(false);
        layerPanel.add(repeatLayer);

        badRepeatLayer = Box.createHorizontalBox();
            errorLabel = new JLabel("Please enter a positive number");
            errorLabel.setForeground(Color.RED);
            badRepeatLayer.add(errorLabel);
            badRepeatLayer.add(Box.createHorizontalGlue());
            badRepeatLayer.setVisible(false);
        layerPanel.add(badRepeatLayer);

        layer = Box.createHorizontalBox();
            JButton addButton = new JButton("Add Reminder");
            addButton.addActionListener(this::addReminderPressed);
            Dimension d = addButton.getPreferredSize();
            addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.height));
        layer.add(addButton);
        layerPanel.add(layer);

        layerPanel.add(Box.createVerticalStrut(PADDING));

        add(horizontallyPaddedPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear(int daysBetweenRepeats) {
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
        descriptionField.setText("");
        dateField.setText(now.getMonthValue() + "/" + now.getDayOfMonth() + "/" + now.getYear());
        timeField.setText("");
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
        LocalDateTime scheduledDateTime;
        if (hour < 0) {
            // If the date give is today's date
            if (now.getYear() == year && now.getMonthValue() == month &&
                    now.getDayOfMonth() == day) {
                LocalDateTime oneHourInTheFuture = now.plusHours(1);
                // If one hour in the future falls after 9:00PM,
                // Set time to 8AM tomorrow
                if (oneHourInTheFuture.getHour() >= 21) {
                    LocalDateTime tomorrow = now.plusDays(1);
                    scheduledDateTime = LocalDateTime.of(tomorrow.getYear(),
                            tomorrow.getMonthValue(),
                            tomorrow.getDayOfMonth(), 8, 0);
                } else if (oneHourInTheFuture.getHour() < 8) {
                    // If reminder would be in the early AM, set hour to 8AM
                    scheduledDateTime = LocalDateTime.of(oneHourInTheFuture.getYear(),
                            oneHourInTheFuture.getMonthValue(),
                            oneHourInTheFuture.getDayOfMonth(), 8, 0);
                } else {
                    // one hour ahead was okay
                    scheduledDateTime = now;
                }
            } else {
                // Otherwise, set time to 8AM that day.
                scheduledDateTime = LocalDateTime.of(year, month, day, 8, 0);
            }
        } else {
            scheduledDateTime = LocalDateTime.of(year, month, day, hour, minute);
        }
        if (scheduledDateTime.compareTo(now) <= 0) {
            timeIsInThePastLayer.setVisible(true);
            pack();
            return;
        } else {
            timeIsInThePastLayer.setVisible(false);
        }
        pack();
        System.out.println("Valid form submission:");
        System.out.println(name);
        System.out.println(description);
        System.out.println(scheduledDateTime);
        if (time.isEmpty())
            System.out.println("No time given");
        else
            System.out.println(time);
        if (days > 0)
            System.out.println("Repeating every " + days + " days");

        ScheduledReminder r = new ScheduledReminder(name, description, scheduledDateTime, days);

        // Needs fixing
        daemon.add(r);
        setVisible(false);
    }

}