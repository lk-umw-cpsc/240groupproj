package code;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import code.schedule.DailyReminder;
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

    // Guards critical section: reminders and dailyReminders
    private Lock lock = new ReentrantLock();

    public BackgroundDaemon() {
        reminders = new ArrayList<>();
        dailyReminders = new ArrayList<>();
    }

    public Lock getLock() {
        return lock;
    }
    
    public void run() {
        // To-do: implement me.
        // Date previousDate = ...
        while (true) {
            lock.lock(); //ensure the following actions are atomic
            /*
            for (ScheduledReminder r : reminders) {
                if (r.isDue()) {
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
