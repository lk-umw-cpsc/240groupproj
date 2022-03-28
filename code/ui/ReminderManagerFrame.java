package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.locks.Lock;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import code.BackgroundDaemon;
import code.schedule.ScheduledReminder;

public class ReminderManagerFrame extends JFrame {

    private BackgroundDaemon daemon;

    private JPanel reminderContainer;

    public ReminderManagerFrame(BackgroundDaemon daemon) {
        super("Your Reminders");
        this.daemon = daemon;

        // reminderContainer = Box.createVerticalBox();
        reminderContainer = new JPanel();
        reminderContainer.setLayout(new BoxLayout(reminderContainer, BoxLayout.Y_AXIS));
        // reminderContainer.setPreferredSize(new Dimension(600, 400));

        reminderContainer.add(Box.createVerticalGlue());
        reminderContainer.setOpaque(true);
        reminderContainer.setBackground(new Color(239, 239, 239));

        JScrollPane scrollPane = new JScrollPane(reminderContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        scrollPane.setBorder(null);
        add(scrollPane);
        pack();
        setLocationRelativeTo(null);
    }

    public void editReminder(ScheduledReminder r) {
        daemon.getReminderFrame().appear(r);
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
        reminderContainer.revalidate();
        reminderContainer.repaint();
    }
    
    public void cancelReminder(ScheduledReminder r) {
        daemon.cancel(r);
    }

}