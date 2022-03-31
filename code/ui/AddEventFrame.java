package code.ui;

import java.awt.Font;
import java.time.LocalDateTime;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.ui.fonts.FontManager;

public class AddEventFrame extends JFrame {

    private BackgroundDaemon daemon;

    private static final int PADDING = 8;

    private JTextField nameField;
    
    public AddEventFrame(BackgroundDaemon daemon) {
        super("Add Event");
        this.daemon = daemon;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        Box rowContainer = Box.createVerticalBox();
        rowContainer.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
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
            nameField = new JTextField(16);
            nameField.setFont(lightFont);
            row.add(nameField);
        rowContainer.add(row);

        pack(); 
    }

    public void appear(LocalDateTime dt) {
        nameField.setText("");

        setVisible(true);
    }

}
