package code;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import code.schedule.DailyReminder;
import code.schedule.ScheduledEvent;
import code.schedule.ScheduledReminder;

/**
 * This class will handle determining whether any of the user's
 * reminders have "gone off"/are due and will display a notification
 * for that reminder.
 * 
 * This class will be the owner of all ui components, including the system
 * tray. To-do: move ui component instantation and creation to this class
 * 
 * This class will also generate a new ScheduledReminder for each of the 
 * user's DailyReminder at 12:00AM.
 */
public class BackgroundDaemon implements Runnable {

    private List<ScheduledReminder> reminders;
    private List<DailyReminder> dailyReminders;
    private List<ScheduledEvent> events;

    // Guards critical section: reminders, daily reminders, and events
    private Lock lock = new ReentrantLock();

    public BackgroundDaemon() {
        reminders = new ArrayList<>();
        dailyReminders = new ArrayList<>();
        events = new ArrayList<>();
    }

    public Lock getLock() {
        return lock;
    }

    public void cancel(DailyReminder dr) {
        lock.lock();
        dailyReminders.remove(dr);
        lock.unlock();
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

    public void add(DailyReminder dr) {
        lock.lock();
        dailyReminders.add(dr);
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
    
    public void run() {
        // To-do: implement me.
        // Date previousDate = ...
        while (true) {
            lock.lock(); //ensure the following actions are atomic
            /*
            for (ScheduledReminder r : reminders) {
                if (r.isDue() && !r.hasBeenDisplayed()) {
                    systemTrayManager.showNotification(r);
                }
            }
            Date currentDate = ...;
            if (!previousDate.equals(currentDate)) {
                for (DailyReminder dr : dailyReminders) {
                    reminders.add(dr.generate());
                }
            }
            previousDate = currentDate;
            */
            lock.unlock();

            try {
                Thread.sleep(60000); // rest for one minute
            } catch (InterruptedException e) {}
        }
    }

}
