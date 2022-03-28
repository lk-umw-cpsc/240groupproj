package code;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import code.schedule.ScheduleIO;
import code.schedule.ScheduledEvent;
import code.schedule.ScheduledReminder;
import code.ui.AddReminderFrame;
import code.ui.MonthViewFrame;
import code.ui.ReminderManagerFrame;
import code.ui.systemtray.SystemTrayManager;

/**
 * A single BackgroundDaemon exists for the application. This BackgroundDaemon
 * is responsible for starting the application, creating UI components, maintaining
 *  the user's lists of events and reminders, and loading/saving application data.
 * 
 * Every 60 seconds, on the minute, the BackgroundDaemon's run method checks
 * to see if any reminders have expired. If they have, a notification is shown.
 * If the reminder is NOT set to repeat, the reminder is removed from the list
 * and discarded. If it is, the reminder is updated to be due in X days,
 * where X is the reminder's day between repetitions.
 * 
 * Upon program start, ScheduleIO.loadSchedule is called to load the user's
 * list of events/reminders. Whenever the schedule/reminder data is changed
 * within the program, and when the program exits, the user's lists are
 * saved using ScheduleIO.saveSchedule.
 */
public class BackgroundDaemon implements Runnable {

    private Thread thread;

    private Semaphore readySignal;

    private volatile boolean running;

    private List<ScheduledReminder> reminders;
    private List<ScheduledEvent> events;

    private AddReminderFrame addReminderFrame;
    private ReminderManagerFrame reminderManagerFrame;
    private MonthViewFrame monthViewFrame;

    private SystemTrayManager trayManager;

    // Guards critical section: reminders and events
    private Lock lock = new ReentrantLock();

    public BackgroundDaemon() {
        reminders = new ArrayList<>();
        events = new ArrayList<>();

        readySignal = new Semaphore(0);

        // Load data structures from file
        ScheduleIO.loadSchedule(reminders, events);
        Collections.sort(reminders);
        // Collections.sort(events);

        // build UI on Swing event thread
        SwingUtilities.invokeLater(this::buildGUI);
    }

    private void buildGUI () {
        // dayViewFrame = new DayViewFrame(this);
        // weekViewFrame = new WeekViewFrame(this);
        monthViewFrame = new MonthViewFrame(this);
        
        addReminderFrame = new AddReminderFrame(this);

        addReminderFrame.build();

        reminderManagerFrame = new ReminderManagerFrame(this);
        // addEventFrame = new AddEventFrame(this);
        trayManager = new SystemTrayManager(this, addReminderFrame, reminderManagerFrame, monthViewFrame);
        
        // Signal to run() that it can start
        readySignal.release();
    }

    /**
     * Obtains this BackgroundDaemon's Lock
     * 
     * Anything accessing the BackgroundDaemon's data structures
     * should be locking and unlocking this lock.
     * @return The lock for this BackgroundDaemon's data structures
     */
    public Lock getLock() {
        return lock;
    }

    /**
     * Gets a list of the user's scheduled reminders.
     * 
     * Lock must be locked when accessing the returned data structure.
     * See getLock().
     * @return A List<ScheduledReminder> containing user's scheduled reminders
     */
    public List<ScheduledReminder> getReminders() {
        return reminders;
    }

    /**
     * Gets a list of the user's scheduled events.
     * 
     * Lock must be locked when accessing the returned data structure.
     * See getLock().
     * @return A List<ScheduledEvent> containing user's scheduled events.
     */
    public List<ScheduledEvent> getEvents() {
        return events;
    }

    public void cancel(ScheduledReminder r) {
        lock.lock();
        if (reminders.contains(r)) {
            reminders.remove(r);
            if (reminderManagerFrame.isVisible()) {
                SwingUtilities.invokeLater(reminderManagerFrame::updateList);
            }
        }
        lock.unlock();
    }

    public void cancel(ScheduledEvent e) {
        lock.lock();
        if (events.contains(e)) {
            events.remove(e);
            if (reminderManagerFrame.isVisible()) {
                SwingUtilities.invokeLater(reminderManagerFrame::updateList);
            }
        }
        lock.unlock();
    }

    public void add(ScheduledReminder r) {
        lock.lock();
        reminders.add(r);
        Collections.sort(reminders);
        if (reminderManagerFrame.isVisible()) {
            SwingUtilities.invokeLater(reminderManagerFrame::updateList);
        }
        lock.unlock();
    }

    public void add(ScheduledEvent e) {
        lock.lock();
        events.add(e);
        // Collections.sort(events);
        if (reminderManagerFrame.isVisible()) {
            SwingUtilities.invokeLater(reminderManagerFrame::updateList);
        }
        lock.unlock();
    }

    /**
     * Method called by SystemTrayManager when Exit option is pressed
     */
    public void exit() {
        running = false;
        thread.interrupt(); // wake run() from its long sleep
    }
    
    public void run() {
        // Wait until buildGUI finishes
        try {
            readySignal.acquire();
        } catch (InterruptedException e1) {}

        thread = Thread.currentThread();
        running = true;

        LocalDateTime previousDate = LocalDateTime.now();
        // String timeString = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(previousDate);
        
        while (running) {
            lock.lock(); //ensure the following actions are atomic
            
            LocalDateTime currentDate = LocalDateTime.now();
            boolean changed = false;

            for (int i = 0; i < reminders.size(); i++) {
                ScheduledReminder r = reminders.get(i);
                if (r.isDue()) {
                    trayManager.showNotification(r.getName(), r.getDescription());
                    System.out.println(r.getName() + " expired!");
                    if (r.repeats()) {
                        r.repeat();
                        System.out.println("Reminder will repeat at " + r.getWhenDue());
                    } else {
                        reminders.remove(i);
                        i--; // don't skip next one!
                    }
                    changed = true;
                }
            }

            if (changed) {
                ScheduleIO.saveSchedule(reminders, events);
                if (reminderManagerFrame.isVisible()) {
                    SwingUtilities.invokeLater(reminderManagerFrame::updateList);
                }
            }

            if (previousDate.getDayOfMonth() != currentDate.getDayOfMonth()) {
                trayManager.dayChanged();
                if (reminderManagerFrame.isVisible()) {
                    SwingUtilities.invokeLater(reminderManagerFrame::updateList);
                }
            }
            
            previousDate = currentDate;
            
            lock.unlock();

            try {
                long minuteOverflow = System.currentTimeMillis() % 60000;
                Thread.sleep(60000 - minuteOverflow); // rest until the next minute
            } catch (InterruptedException e) {}
        }

        lock.lock();
        ScheduleIO.saveSchedule(reminders, events);
        lock.unlock();
        System.exit(0);
    }

}