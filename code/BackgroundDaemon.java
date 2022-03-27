package code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import code.schedule.ScheduleIO;
import code.schedule.ScheduledEvent;
import code.schedule.ScheduledReminder;
import code.ui.AddReminderFrame;
import code.ui.ReminderManagerFrame;
import code.ui.systemtray.SystemTrayManager;

/**
 * This class will handle determining whether any of the user's
 * reminders have "gone off"/are due and will display a notification
 * for that reminder.
 * 
 * This class will be the owner of all ui components, including the system
 * tray. To-do: move ui component instantation and creation to this class
 * 
 * This class will also generate a new ScheduledReminder for each of the 
 * user's DailyReminders at 12:00AM.
 */
public class BackgroundDaemon implements Runnable {

    private Thread thread;

    private Semaphore readySignal;

    private volatile boolean running;

    private static List<ScheduledReminder> reminders;
    private static List<ScheduledEvent> events;

    private AddReminderFrame addReminderFrame;
    private ReminderManagerFrame reminderManagerFrame;

    private SystemTrayManager trayManager;

    // Guards critical section: reminders and events
    private Lock lock = new ReentrantLock();

    public BackgroundDaemon() throws FileNotFoundException {
        reminders = new ArrayList<>();
        events = new ArrayList<>();

        readySignal = new Semaphore(0);

        // Load data structures from file
        //ScheduleIO.loadSchedules();
        ScheduleIO.loadSchedules(events, reminders);

        // build UI on Swing event thread
        SwingUtilities.invokeLater(this::buildGUI);
    }

    private void buildGUI () {
        // dayViewFrame = new DayViewFrame(this);
        // weekViewFrame = new WeekViewFrame(this);
        // monthViewFrame = new MonthViewFrame(this);
        
        addReminderFrame = new AddReminderFrame(this);

        addReminderFrame.build();
        reminderManagerFrame = new ReminderManagerFrame(this);
        // addEventFrame = new AddEventFrame(this);
        trayManager = new SystemTrayManager(this, addReminderFrame, reminderManagerFrame);
        
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
        reminders.remove(r);
        lock.unlock();
    }

    public void cancel(ScheduledEvent e) {
        lock.lock();
        events.remove(e);
        lock.unlock();
    }

    public void add(ScheduledReminder r) {
        lock.lock();
        reminders.add(r);
        lock.unlock();
    }

    public void add(ScheduledEvent e) {
        lock.lock();
        events.add(e);
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

            //I moved this up here because date should change before any notifications
            //
            if (previousDate.getDayOfMonth() != currentDate.getDayOfMonth()) 
            {
                trayManager.dayChanged();
            }
            
            previousDate = currentDate;

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

            if (changed) 
            {
                try 
                {
                    ScheduleIO.saveSchedules(events, reminders);
                } catch (IOException e) 
                {
                    
                }
                if (reminderManagerFrame.isVisible()) {
                    SwingUtilities.invokeLater(reminderManagerFrame::updateList);
                }
            }


            
            
            lock.unlock();

            try {
                long minuteOverflow = System.currentTimeMillis() % 60000;
                Thread.sleep(60000 - minuteOverflow); // rest until the next minute
            } catch (InterruptedException e) {}
        }

        try 
        {
            ScheduleIO.saveSchedules(events, reminders);
        } catch (IOException e) 
        {
            
        }
        System.exit(0);
    }

    public static void testLoadAndSave() throws IOException
    {
        reminders = new ArrayList<>();
        events = new ArrayList<>();
        ScheduleIO.loadSchedules(events, reminders);
        reminders.add(new ScheduledReminder("Reminder Tester", "Description for reminder tester", "2022-03-24", 1));
        events.add(new ScheduledEvent("Event Tester", "2022-03-24", "17:00", 3));
        ScheduleIO.saveSchedules(events, reminders);

    }

}
