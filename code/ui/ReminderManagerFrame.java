package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.concurrent.locks.Lock;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import code.BackgroundDaemon;
import code.schedule.ScheduledReminder;

public class ReminderManagerFrame extends JFrame {

    private static ImageIcon xIcon;
    static {
        try {
            BufferedImage x = ImageIO.read(new File("Images/x.png"));
            xIcon = new ImageIcon(x);
        } catch(IOException e) {}
    }

    private BackgroundDaemon daemon;

    private Box reminderContainer;

    public ReminderManagerFrame(BackgroundDaemon daemon) {
        super("Your Reminders");
        this.daemon = daemon;

        reminderContainer = Box.createVerticalBox();
        reminderContainer.setPreferredSize(new Dimension(600, 400));

        reminderContainer.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(reminderContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear() {
        updateList();
        setVisible(true);
    }

    public void updateList() {
        reminderContainer.removeAll();
        Lock l = daemon.getLock();
        l.lock();
        List<ScheduledReminder> reminders = daemon.getReminders();
        if (reminders.isEmpty()) {

        } else {
            for (ScheduledReminder r : reminders) {
                Box entryContainer = Box.createHorizontalBox();
                Box xButtonBox = Box.createVerticalBox();
    
                JLabel xButton = new JLabel(xIcon);
                xButton.setBorder(new EmptyBorder(24, 8, 24, 8));
                xButtonBox.add(Box.createVerticalGlue());
                xButtonBox.add(xButton);
                xButtonBox.add(Box.createVerticalGlue());
                entryContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, xButton.getHeight()));
                entryContainer.add(xButtonBox);

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

                entryContainer.add(infoBox);

                entryContainer.add(Box.createHorizontalGlue());

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

                entryContainer.add(timeDateBox);

                entryContainer.add(Box.createHorizontalStrut(8));

                entryContainer.setBorder(LineBorder.createBlackLineBorder());
                entryContainer.setOpaque(true);
                entryContainer.setBackground(Color.WHITE);
                reminderContainer.add(entryContainer);
            }
        }
        reminderContainer.add(Box.createVerticalGlue());

        l.unlock();
        reminderContainer.repaint();
        reminderContainer.revalidate();
    }
    
}
