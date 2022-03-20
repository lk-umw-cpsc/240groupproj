package code.schedule;

/**
 * Base class for reminders. Anything that should prompt the user
 * with a notification should either BE A or HAVE A ScheduledReminder.
 * 
 * Implementation assigned to Jayden
 * 
 * TO-DO: Any subclass appearing in the calendar needs to eventually extend
 * this class.
 * 
 * A ScheduledReminder has:
 * name (mandatory) - String
 * date (mandatory) - type to be determined
 * a time (optional) - type to be determined
 * location (optional) - String
 * description (optional) - String; any extra details are displayed here.
 * 
 * notificationShown - boolean; determines whether this reminder's notification has
 * been displayed.
 * 
 * getters/setters for the above fields
 * an isDue() method that returns true if this reminder is due
 */
public class ScheduledReminder {
    
}
