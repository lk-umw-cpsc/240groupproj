package code.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import code.schedule.Birthday;
//import code.schedule.DateTimeFormatter;
import code.ui.fonts.FontManager;

public class AddBirthdayFrame extends JFrame{
    private Box rowContainer;
    private Box nameRow;
    private Box nameInsRow;
    private Box dateRow;
    private Box dateInsRow;
    private Box badBDayNameRow;
    private Box badDobRow;
    private Box saveRow;

    private JButton saveButton;

    private JTextField nameField;
    private JTextField dobField;

    LocalDate today = LocalDate.now();
    String formattedDate = today.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));


    private static final Pattern datePattern = Pattern.compile("^([1-9]|1[0-2])/([1-9]|[12]\\d|3[01])/(\\d\\d\\d\\d)$");

    public AddBirthdayFrame() {
        rowContainer = Box.createVerticalBox();
        rowContainer.setBorder(new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, UIConstants.PADDING, UIConstants.PADDING));
        Font regFont = FontManager.getInstance().getRegularFont(); //Everything else
        Font lightFont = FontManager.getInstance().getLightFont(); //TextFields
        setTitle("Add a Birthday");


        nameRow = Box.createHorizontalBox();
            JLabel label = new JLabel("Insert person's name: ");
            label.setFont(regFont); //Uses reg font for labels
            nameRow.add(label);
            nameRow.add(Box.createHorizontalGlue());
        rowContainer.add(nameRow);

        nameInsRow = Box.createHorizontalBox();
            nameInsRow.add(nameField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS));
            nameField.addActionListener(this::formSubmitted);
            nameField.setFont(lightFont);
        rowContainer.add(nameInsRow);

        dateRow = Box.createHorizontalBox();
            label = new JLabel("Insert their Date of Birth");
            label.setFont(regFont);
            dateRow.add(label);
            dateRow.add(Box.createHorizontalGlue());
        rowContainer.add(dateRow);
        
        dateInsRow = Box.createHorizontalBox();
            dateInsRow.add(dobField = new JTextField(UIConstants.JTEXTFIELD_DEFAULT_COLUMNS));
            dobField.addActionListener(this::formSubmitted);
            dobField.setFont(lightFont);
            dobField.setText(formattedDate);
        rowContainer.add(dateInsRow);

        badBDayNameRow = Box.createHorizontalBox();
            label = new JLabel("Please insert a name");
            label.setFont(regFont);
            label.setForeground(Color.RED);
            badBDayNameRow.add(label);
            badBDayNameRow.add(Box.createHorizontalGlue());
            badBDayNameRow.setVisible(false);
        rowContainer.add(badBDayNameRow);

        badDobRow = Box.createHorizontalBox();
            label = new JLabel("Please enter a valid date");
            label.setFont(regFont);
            label.setForeground(Color.RED);
            badDobRow.add(label);
            badDobRow.add(Box.createHorizontalGlue());
            badDobRow.setVisible(false);
        rowContainer.add(badDobRow);

        saveRow = Box.createHorizontalBox();
            saveButton = new JButton("Add Birthday");
            saveButton.setFont(regFont);
            Dimension preferred = saveButton.getPreferredSize();
            saveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferred.height));
            saveButton.addActionListener(this::formSubmitted);
            saveRow.add(saveButton);
        rowContainer.add(saveRow);

        

        add(rowContainer);
        pack();
        setLocationRelativeTo(null);
    }

    private Birthday validateForm() {
        String name = nameField.getText();
        String dob = dobField.getText();

        LocalDate d = null;

        boolean errors = false;

        Matcher m = datePattern.matcher(dob);
        if (m.matches()) {
            int month = Integer.parseInt(m.group(1));
            int day = Integer.parseInt(m.group(2));
            int year = Integer.parseInt(m.group(3));
            d = LocalDate.of(year, month, day);
        }

        if (name.isEmpty()) {
            errors = true;
            badBDayNameRow.setVisible(true);
        } else {
            badBDayNameRow.setVisible(false);
        }

        if (d == null) {
            errors = true;
            badDobRow.setVisible(true);
        } else {
            badDobRow.setVisible(false);
        }

        if(errors) {
            pack();
            return null;
        }

        return new Birthday(name, d);
    }

    private void formSubmitted(ActionEvent e) {
        Birthday birthday = validateForm();

        if (validateForm() != null) {
            return;
        }
        clearForm();
    }

    private void clearForm() {
        nameField.setText("");
        dobField.setText("");

        badBDayNameRow.setVisible(false);
        badDobRow.setVisible(true);

    }

    public static void main (String[] args) {
        AddBirthdayFrame abf = new AddBirthdayFrame();
        abf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        abf.setVisible(true);

    }
}
