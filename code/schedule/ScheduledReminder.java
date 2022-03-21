package code.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

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
    
    private LocalDate date;
    private LocalTime time;

    private boolean repeated;
    private int daysbetweenReps;
    
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
     * Setter method for the date
     * @param date LocalDate variable of the day for the reminder to be triggered
     */
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    /**
     * Getter method for the date variable
     * @return LocalDate of the due date for the reminder
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Default setter for Time variable
     * Sets time to midnight, or 0:00
     */
    public void setTime() {
        time = LocalTime.of(0,0);
    }

    /**
     * Non-default setter for time variable
     * @param time User input of what time the reminder will be activated
     */
    public void setTime(String time) {
        this.time = LocalTime.parse(time);
    }

    /**
     * Getter method for the time variable
     * @return LocalTime of the time when the reminder should trigger
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Getter method for the repeated variable
     * @return repeatded variable
     */
    public boolean doesRepeat() {
        return repeated;
    }

    public void setRepetition() {
        repeated = false;
    }

    public void setRepetition(boolean rep, int i) {
        repeated = rep;
        daysbetweenReps = i;
    }
    
    /**
     * Method used to determine if the reminder is due or not
     * return depends on if both the due date and due time are after the current time and date
     * @return True is the reminder is due, false if it is not due
     */
    public boolean isDue() {
        //return ((date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) && (time.isAfter(LocalTime.now()) || time.equals(LocalTime.now())));
        //Use compare to method, rather than multiple instances of LocalDate or LocalTime

        LocalDate today = LocalDate.now();

        if (today.compareTo(date) <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
