package code;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import code.schedule.ScheduleIO;
import code.schedule.ScheduledEvent;
import code.schedule.ScheduledReminder;
import code.ui.AddEventFrame;
import code.ui.AddReminderFrame;
import code.ui.DayViewFrame;
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
    private Map<LocalDate, List<ScheduledEvent>> eventsMap;

    private AddReminderFrame addReminderFrame;
    private AddEventFrame addEventFrame;
    private ReminderManagerFrame reminderManagerFrame;
    private MonthViewFrame monthViewFrame;
    private DayViewFrame dayViewFrame;

    private SystemTrayManager trayManager;

    // Guards critical section: reminders and events
    private Lock lock = new ReentrantLock();

    private static BackgroundDaemon instance;

    public static synchronized BackgroundDaemon getInstance() {
        if (instance == null) {
            instance = new BackgroundDaemon();
        }
        return instance;
    }

    private BackgroundDaemon() {
        reminders = new ArrayList<>();
        List<ScheduledEvent> events = new ArrayList<>();

        readySignal = new Semaphore(0);

        eventsMap = new HashMap<>();

        // events.add(new ScheduledEvent("I'm an event", LocalDate.now(), 
        //     LocalTime.of(8, 0), LocalTime.of(9, 0), "Somewhere"));

        // Load data structures from file
        ScheduleIO.loadSchedule(reminders, events);
        Collections.sort(reminders);

        for (ScheduledEvent e : events) {
            LocalDate d = e.getDate();
            if (eventsMap.containsKey(d)) {
                eventsMap.get(d).add(e);
            } else {
                List<ScheduledEvent> l = new ArrayList<>();
                l.add(e);
                eventsMap.put(d, l);
            }
        }

        for (LocalDate d : eventsMap.keySet()) {
            Collections.sort(eventsMap.get(d));
        }
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

        addEventFrame = new AddEventFrame(this);

        reminderManagerFrame = new ReminderManagerFrame(this);

        dayViewFrame = new DayViewFrame(this);
        // addEventFrame = new AddEventFrame(this);
        trayManager = new SystemTrayManager(this, addReminderFrame, addEventFrame, reminderManagerFrame, monthViewFrame);
        
        // Signal to run() that it can start
        readySignal.release();
    }

    public AddReminderFrame getReminderFrame() {
        return addReminderFrame;
    }

    public AddEventFrame getAddEventFrame() {
        return addEventFrame;
    }

    public ReminderManagerFrame getReminderManagerFrame() {
        return reminderManagerFrame;
    }

    public DayViewFrame getdDayViewFrame() {
        return dayViewFrame;
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
     * update me
     */
    public Map<LocalDate, List<ScheduledEvent>> getEvents() {
        return eventsMap;
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

    public void cancel(LocalDate d, ScheduledEvent e) {
        lock.lock();
        if (eventsMap.containsKey(d)) {
            List<ScheduledEvent> events = eventsMap.get(d);
            if (events.contains(e)) {
                events.remove(e);
                if (dayViewFrame.isVisible()) {
                    SwingUtilities.invokeLater(reminderManagerFrame::updateList);
                }
                if (monthViewFrame.isVisible()) {
                    // TO-DO: ...
                }
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

    public void add(LocalDate d, ScheduledEvent e) {
        lock.lock();
        List<ScheduledEvent> list;
        if (eventsMap.containsKey(d)) {
            list = eventsMap.get(d);
            list.add(e);
            Collections.sort(list);
        } else {
            list = new ArrayList<ScheduledEvent>();
            list.add(e);
            eventsMap.put(d, list);
        }
        if (monthViewFrame.isVisible()) {
            monthViewFrame.updateDay(d, list);
        }
        if (dayViewFrame.isVisible()) {
            dayViewFrame.update(d, list);
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
                        do {
                            r.repeat();
                        } while (r.isDue());
                        System.out.println("Reminder will repeat at " + r.getWhenDue());
                    } else {
                        reminders.remove(i);
                        i--; // don't skip next one!
                    }
                    changed = true;
                }
            }

            if (changed) {
                List<ScheduledEvent> allEvents = new ArrayList<>();
                for (List<ScheduledEvent> l : eventsMap.values()) {
                    allEvents.addAll(l);
                }
                ScheduleIO.saveSchedule(reminders, allEvents);
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
        List<ScheduledEvent> allEvents = new ArrayList<>();
        for (List<ScheduledEvent> l : eventsMap.values()) {
            allEvents.addAll(l);
        }
        ScheduleIO.saveSchedule(reminders, allEvents);
        lock.unlock();
        System.exit(0);
    }

}