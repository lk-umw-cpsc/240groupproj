package code.schedule;

/**
 * Base class for reminders. Anything that should prompt the user
 * with a notification should either BE A or HAVE A ScheduledReminder.
 * 
 * Implementation assigned to Jayden
 * 
 * A ScheduledReminder has:
 * name (mandatory) - String
 * date (mandatory) - type to be determined
 * a time (optional) - type to be determined. if not provided, defaults to 12:00AM midnight
 * description (optional) - String; any extra details are displayed here.
 * 
 * getters/setters for the above fields
 * an isDue() method that returns true if this reminder is due
 */
public class ScheduledReminder {
    
}
