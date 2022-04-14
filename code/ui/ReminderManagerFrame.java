package code.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.locks.Lock;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.schedule.ScheduledReminder;
import code.ui.fonts.FontManager;

public class ReminderManagerFrame extends JFrame {

    private BackgroundDaemon daemon;

    private Box reminderContainer;

    public ReminderManagerFrame(BackgroundDaemon daemon) {
        super("Your Reminders");
        this.daemon = daemon;

        // reminderContainer = Box.createVerticalBox();
        // reminderContainer = new JPanel();
        reminderContainer = Box.createVerticalBox();
        // reminderContainer.setLayout(new BoxLayout(reminderContainer, BoxLayout.Y_AXIS));
        // reminderContainer.setPreferredSize(new Dimension(600, 400));

        reminderContainer.add(Box.createVerticalGlue());
        reminderContainer.setOpaque(true);
        reminderContainer.setBackground(new Color(239, 239, 239));

        JScrollPane scrollPane = new JScrollPane(reminderContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(UIConstants.JSCROLLPANE_VERTICAL_SCROLLBAR_INCREMENT);
        
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
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
            Box emptyRemindersBox = Box.createHorizontalBox();

            emptyRemindersBox.add(Box.createHorizontalGlue());
            JLabel label;
            emptyRemindersBox.add(label = new JLabel("You don't currently have any set reminders!"));
            label.setFont(FontManager.getInstance().getRegularFont());
            emptyRemindersBox.add(Box.createHorizontalGlue());

            reminderContainer.add(Box.createVerticalGlue());
            reminderContainer.add(emptyRemindersBox);
            reminderContainer.add(Box.createVerticalGlue());
            reminderContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            emptyRemindersBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        } else {
            for (ScheduledReminder r : reminders) {
                reminderContainer.add(new ReminderUIEntry(this, r).getContainer());
            }
        }
        reminderContainer.add(Box.createVerticalGlue());

        l.unlock();
        pack();
        reminderContainer.revalidate();
        reminderContainer.repaint();
    }
    
    public void cancelReminder(ScheduledReminder r) {
        daemon.cancel(r);
    }

}