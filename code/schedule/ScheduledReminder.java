package code.schedule;

import java.time.LocalDateTime;

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
    private String name;
    private String description; 
    private LocalDateTime dueDateTime;
    private int daysBetweenRepetitions;

    public ScheduledReminder(String name, String date, int reps) {
        this.name = name;
        dueDateTime = LocalDateTime.parse(date + "T06:00:00");
        description = "";
        
        daysBetweenRepetitions = reps;
    }

    public ScheduledReminder(String name, String description, String date, int reps) {
        this.name = name;
        this.description = description;
        dueDateTime = LocalDateTime.parse(date + "T06:00:00");
        
        daysBetweenRepetitions = reps;
    }

    public ScheduledReminder(String name, String description, LocalDateTime whenDue, int reps) {
        this.name = name;
        this.description = description;
        this.dueDateTime = whenDue;
        daysBetweenRepetitions = reps;
    }

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

    public void setWhenDue(String date) {
        dueDateTime = LocalDateTime.parse(date + "T6:00:00");
    }

    public void setWhenDue(String date, String time) {
        dueDateTime = LocalDateTime.parse(date + "T" + time);
    }


    public LocalDateTime getWhenDue() {
        return dueDateTime;
    }

    /**
     * Getter method for the repeated variable
     * @return repeatded variable
     */
    public boolean repeats() {
        return daysBetweenRepetitions > 0;
    }

    public void repeat() {
        dueDateTime = dueDateTime.plusDays(daysBetweenRepetitions);
    }

    public void setDaysBetweenRepetitions(int i) {
        daysBetweenRepetitions = i;
    }

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
    @Override
    public String toString() {
        return this.name + " " + this.description + " " + dueDateTime.toString() + " " + daysBetweenRepetitions;
    }
}
