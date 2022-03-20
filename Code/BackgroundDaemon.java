package code;

import java.util.ArrayList;
import java.util.List;

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

    public BackgroundDaemon() {
        reminders = new ArrayList<>();
        dailyReminders = new ArrayList<>();
    }
    
    public void run() {
        // To-do: implement me.
        // Date previousDate = ...
        while (true) {
            /*
            for (ScheduledReminder r : reminders) {
                if (r.isDue()) {
                    systemTrayManager.showNotification();
                }
            }
            Date currentDate = ...;
            if (!previousDate.equals(currentDate)) {
                for (DailyReminder dr : dailyReminders) {
                    reminders.add(dr.generate());
                }
            }
            previousDate = currentDate;

            Thread.sleep(60000); // one minute
             */
        }
    }

}
