package code.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.schedule.DateTimeFormatter;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class AddEventFrame extends JFrame {

    private final BackgroundDaemon daemon;

    private JTextField nameField;
    private JTextField locationField;
    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;

    private JButton addSaveButton;

    private ScheduledEvent editTarget;
    
    public AddEventFrame(BackgroundDaemon daemon) {
        super("Add Event");
        this.daemon = daemon;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

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

    private boolean validateForm() {
        return false;
    }

    private void formSubmitted(ActionEvent e) {
        if (!validateForm())
            return;
        if (editTarget != null) {
            editTarget.setDescription(nameField.getText());
            // editTarget.setLocation(locationField.getText())
            // ... editTarget.setStartTime();
        } else {

        }
    }

    public void appearForEdit(Component owner, ScheduledEvent editTarget) {
        if (editTarget != null) {

        }
        this.editTarget = editTarget;
    }

    public void appear(Component owner, LocalDate d) {
        if (editTarget != null) {
            // ???
        }
        nameField.setText("");
        locationField.setText("");
        dateField.setText(DateTimeFormatter.toSlashString(d));
        startTimeField.setText("8:00AM");
        endTimeField.setText("9:00AM");

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
        // implement me
    }

    private void clearForm() {
        nameField.setText("");
        locationField.setText("");
        dateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
    }

}
