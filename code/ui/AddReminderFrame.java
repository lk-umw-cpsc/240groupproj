package code.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import code.BackgroundDaemon;
import code.schedule.ScheduledReminder;

public class AddReminderFrame extends JFrame {

    private static final int PADDING = 16;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField dateField;
    private JTextField timeField;

    private JCheckBox repeatCheckbox;
    private JTextField repeatField;
    private Box repeatLayer;

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

        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Time (optional):"));
            layer.add(Box.createHorizontalGlue());
        layerPanel.add(layer);

            timeField = new JTextField(24);
        layerPanel.add(timeField);

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
            // String timeString = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(now);
            // timeField.setText(timeString);
            repeatCheckbox.setSelected(false);
            repeatField.setText("");
            repeatLayer.setVisible(false);
            pack();
        }
        String dateString = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(now);
        dateField.setText(dateString);
        setVisible(true);
    }

    private void repeatCheckboxChanged(ActionEvent e) {
        if (repeatCheckbox.isSelected()) {
            repeatField.setText("1");
            repeatLayer.setVisible(true);
            pack();
        } else {
            repeatField.setText("");
            repeatLayer.setVisible(false);
            pack();
        }
    }

    private void addReminderPressed(ActionEvent e) {
        // to-do: form validation
        String name = nameField.getText();
        String description = descriptionField.getText();
        String time = timeField.getText();
        String date = dateField.getText();
        // Needs fixing
        // daemon.add(new ScheduledReminder(name, description, time, date));
        setVisible(false);
    }

}
