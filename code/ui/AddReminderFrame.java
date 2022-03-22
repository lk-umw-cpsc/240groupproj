package code.ui;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

            dateField = new JTextField(24);
        layerPanel.add(dateField);

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
            pack();
        } else {
            repeatCheckbox.setSelected(false);
            repeatField.setText("");
            repeatLayer.setVisible(false);
            pack();
        }
        dateField.setText(now.getMonthValue() + "/" + now.getDayOfMonth() + "/" + now.getYear());
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

        if (!(time.isEmpty() || timePattern.matcher(time).matches())) {
            formContainsErrors = true;
            badTimeLayer.setVisible(true);
        } else {
            badTimeLayer.setVisible(false);
        }

        if (!datePattern.matcher(date).matches()) {
            formContainsErrors = true;
            badDateLayer.setVisible(true);
        } else {
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
        pack();
        if (formContainsErrors) {
            return;
        }
        System.out.println("Valid form submission:");
        System.out.println(name);
        System.out.println(description);
        System.out.println(date);
        if (time.isEmpty())
            System.out.println("No time given");
        else
            System.out.println(time);
        if (days > 0)
            System.out.println("Repeating every " + days + " days");

        // ScheduledReminder r = new ScheduledReminder(name, description, time, date);

        // Needs fixing
        // daemon.add(new ScheduledReminder(name, description, time, date));
        setVisible(false);
    }

}