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
                reminderContainer.add(new ReminderUIEntry(this, r).getContainer());
            }
        }
        reminderContainer.add(Box.createVerticalGlue());

        l.unlock();
        reminderContainer.repaint();
        reminderContainer.revalidate();
    }
    
    public void cancelReminder(ScheduledReminder r) {
        daemon.cancel(r);
    }
    
}
