package code.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import code.schedule.ScheduledReminder;

public class ReminderUIEntry implements MouseListener {

    private static ImageIcon xIcon;
    static {
        try {
            BufferedImage x = ImageIO.read(new File("Images/x.png"));
            xIcon = new ImageIcon(x);
        } catch(IOException e) {}
    }

    private ScheduledReminder reminder;
    private ReminderManagerFrame parent;
    private Box container;

    public ReminderUIEntry(ReminderManagerFrame parent, ScheduledReminder r) {
        this.parent = parent;
        reminder = r;
        container = Box.createHorizontalBox();
        Box xButtonBox = Box.createVerticalBox();

        JLabel xButton = new JLabel(xIcon);
        xButton.setBorder(new EmptyBorder(24, 8, 24, 8));
        xButton.addMouseListener(this);
        xButtonBox.add(Box.createVerticalGlue());
        xButtonBox.add(xButton);
        xButtonBox.add(Box.createVerticalGlue());
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, xButton.getHeight()));
        container.add(xButtonBox);

        Box infoBox = Box.createVerticalBox();
        // infoBox.add(Box.createVerticalGlue());
        JLabel nameLabel = new JLabel(r.getName());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        infoBox.add(nameLabel);
        String desc = r.getDescription();
        if (!desc.isBlank()) {
            infoBox.add(Box.createVerticalStrut(8));
            infoBox.add(new JLabel(desc));
        }
        // infoBox.add(Box.createVerticalGlue());

        container.add(infoBox);

        container.add(Box.createHorizontalGlue());

        Box timeDateBox = Box.createVerticalBox();
        Box layer = Box.createHorizontalBox();
        layer.add(Box.createHorizontalGlue());
        layer.add(new JLabel(r.getWhenDue().toString()));
        timeDateBox.add(layer);
        if (r.repeats()) {
            String dayOrDays = "day";
            int days = r.getDaysBetweenRepetitions();
            if (days > 1) {
                dayOrDays = " " + days + " days";
            }
            timeDateBox.add(Box.createVerticalStrut(8));
            layer = Box.createHorizontalBox();
            layer.add(Box.createHorizontalGlue());
            layer.add(new JLabel("Repeats every" + dayOrDays));
            timeDateBox.add(layer);
        }

        container.add(timeDateBox);

        container.add(Box.createHorizontalStrut(8));

        container.setBorder(LineBorder.createBlackLineBorder());
        container.setOpaque(true);
        container.setBackground(Color.WHITE);
    }

    public ScheduledReminder getReminder() {
        return reminder;
    }

    public Component getContainer() {
        return container;
    }

    private void cancel() {
        parent.cancelReminder(reminder);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        cancel();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}