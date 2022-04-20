package code.schedule;

import java.time.LocalDateTime;

/**
 * Base class for reminders. Anything that should prompt the user
 * with a notification should either BE A or HAVE A ScheduledReminder.
 * 
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
public class ScheduledReminder implements Comparable<ScheduledReminder> {
    private String name;
    private String description; 
    private LocalDateTime dueDateTime;
    private int daysBetweenRepetitions;

    /**
     * Default constructor for ScheduledReminder object
     * Defaults the dueDateTime to the date inputted at 6 am
     * @param name The name of the reminder
     * @param date The day the reminder is due
     * @param reps How many days inbetween each reminder 
     */
    public ScheduledReminder(String name, String date, int reps) {
        this.name = name;
        dueDateTime = LocalDateTime.parse(date + "T06:00:00");
        description = "";
        
        daysBetweenRepetitions = reps;
    }

    /**
     * Non-default constructor for ScheduledReminder object.
     * Again defaults the time to 6 am, but now adds a description
     * @param name The name of the reminder
     * @param description The description for the reminder
     * @param date The day the reminder is due
     * @param reps How many days inbetween each reminder 
     */
    public ScheduledReminder(String name, String description, String date, int reps) {
        this.name = name;
        this.description = description;
        dueDateTime = LocalDateTime.parse(date + "T06:00:00");
        
        daysBetweenRepetitions = reps;
    }

    /**
     * Non-default constructor for ScheduledReminder object.
     * Now has dueDateTime as fully custom to user as a LocalDateTime type, not defaulting to 6 am
     * @param name The name of the reminder
     * @param description The description for the reminder
     * @param whenDue The day and time the reminder is due
     * @param reps How many days inbetween each reminder 
     */
    public ScheduledReminder(String name, String description, LocalDateTime whenDue, int reps) {
        this.name = name;
        this.description = description;
        this.dueDateTime = whenDue;
        daysBetweenRepetitions = reps;
    }

    /**
     * Non-default constructor for ScheduledReminder object.
     * Also has fully customized due date/time, but now taken as strings
     * @param name The name of the reminder
     * @param description The description of the reminder
     * @param date The date of the reminder
     * @param time The time of the reminder
     * @param reps How many days between each reminder
     */
    public ScheduledReminder(String name, String description, String date, String time, int reps) {
        this.name = name;
        this.description = description;
        dueDateTime = LocalDateTime.parse(date + "T" + time);
        
        daysBetweenRepetitions = reps;
    }

    
    /**
     * Setter method for the name of the Reminder
     * @param name The name of the reminder entered by the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the name of the reminder
     * @return String of the name of the reminder
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter method for the description of the reminder
     * @param description The description of the reminder
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for the discription of the reminder
     * @return String form description of reminder
     */
    public String getDescription() {
        return this.description;
    }

    
    /** 
     * Setter method for the setWhenDue variable, defaulting to 6 am
     * @param date The date for the reminder
     */
    public void setWhenDue(String date) {
        dueDateTime = LocalDateTime.parse(date + "T6:00:00");
    }

    
    /** 
     * Stteer method for the setWhenDue variable, with date and time customized
     * @param date The date of the reminder
     * @param time The time for the reminder
     */
    public void setWhenDue(String date, String time) {
        dueDateTime = LocalDateTime.parse(date + "T" + time);
    }


    
    /** 
     * Getter method for the dueDateTime variable
     * @return LocalDateTime of the dueDateTime of the reminder
     */
    public LocalDateTime getWhenDue() {
        return dueDateTime;
    }

    /**
     * Getter method for the repeated variable
     * @return boolean, true if the daysBetweenReps variable is > 0, false if not
     */
    public boolean repeats() {
        return daysBetweenRepetitions > 0;
    }

    /**
     * Method updates the dueDateTime based on the daysBetweenRepetition variable, adding 
     * days to the dueDate
     */
    public void repeat() {
        dueDateTime = dueDateTime.plusDays(daysBetweenRepetitions);
    }

    
    /** 
     * Setter method for the daysBetweenRepetitions variable
     * @param i The number of days between repetitions
     */
    public void setDaysBetweenRepetitions(int i) {
        daysBetweenRepetitions = i;
    }

    
    /** 
     * Getter method for the daysBetweenRepetition variable
     * @return int How many days between each reminder
     */
    public int getDaysBetweenRepetitions() {
        return daysBetweenRepetitions;
    }
    
    /**
     * Method used to determine if the reminder is due or not
     * return depends on if both the due date and due time are after the current time and date
     * @return True is the reminder is due, false if it is not due
     */
    public boolean isDue() {
        //Use compare to method, rather than multiple instances of LocalDate or LocalTime

        LocalDateTime now = LocalDateTime.now();

        return now.compareTo(dueDateTime) >= 0;
    }
    
    /** 
     * Method used to print out the information associated with the reminder in a more readable format
     * @return String of the name, description, due date, and repetition interval in readable format
     */
    @Override
    public String toString() {
        return this.name + " " + this.description + " " + dueDateTime.toString() + " " + daysBetweenRepetitions;
    }

    
    /** 
     * Unused method, idea is to determine if 2 dueDates are the same
     * @param o A ScheduledReminder object used for comparison
     * @return int The difference between the 2 dueDateTime variables
     */
    @Override
    public int compareTo(ScheduledReminder o) {
        return dueDateTime.compareTo(o.dueDateTime);
    }
}
