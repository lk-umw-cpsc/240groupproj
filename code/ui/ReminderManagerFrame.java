package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import code.BackgroundDaemon;

public class ReminderManagerFrame extends JFrame {

    private BackgroundDaemon daemon;

    public ReminderManagerFrame(BackgroundDaemon daemon) {
        super("Your Reminders");
        this.daemon = daemon;

        Box verticalContainer = Box.createVerticalBox();
        // JScrollPane scrollPane = new JScrollPane(verticalContainer);
        verticalContainer.setPreferredSize(new Dimension(600, 400));
        // scrollPane.setPreferredSize(new Dimension(600, 400));

        // scrollPane.add(verticalContainer);

        Box entryContainer = Box.createVerticalBox();
        Box layer = Box.createHorizontalBox();
            // layer.setMinimumSize(new Dimension(0, 50));
            JLabel nameLabel = new JLabel("Take your medication");
            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
            layer.add(nameLabel);

            layer.add(Box.createHorizontalGlue());

            layer.add(new JLabel("Today at 5:00PM"));

            layer.setBorder(new EmptyBorder(16, 16, 4, 16));
        entryContainer.add(layer);
        layer = Box.createHorizontalBox();
            nameLabel = new JLabel("Description goes here.");
            layer.add(nameLabel);

            layer.add(Box.createHorizontalGlue());

            layer.setBorder(new EmptyBorder(4, 16, 16, 16));
        entryContainer.add(layer);
        entryContainer.setBorder(LineBorder.createBlackLineBorder());
        entryContainer.setOpaque(true);
        entryContainer.setBackground(Color.WHITE);
        verticalContainer.add(entryContainer);
        verticalContainer.add(Box.createVerticalGlue());

        //verticalContainer.add(Box.createVerticalGlue());
        add(verticalContainer);
        pack();
        setLocationRelativeTo(null);
    }
    
}
