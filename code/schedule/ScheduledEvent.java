package code.schedule;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import code.ui.BasicCalendarNote;

/**
 * This represents a blocked out portion of the user's schedule.
 * 
 * Doctor's appointments, scheduled meetings, etc. will all
 * be A ScheduledEvent (subclass)
 * 
 * Also includes a start/end time, as well as a location for extra utility.
 * 
 */

public class ScheduledEvent extends BasicCalendarNote implements Comparable<ScheduledEvent> {

    private static final Color BG_COLOR_EVENT_TODAY = new Color(173, 217, 22);
    private static final Color FG_COLOR_EVENT_TODAY = Color.BLACK;
    private static final Color BG_COLOR_EVENT = new Color(209, 71, 82);
    private static final Color FG_COLOR_EVENT = Color.WHITE;
    private static final Color BG_COLOR_EVENT_OTHER_MONTH = new Color(237, 140, 148);
    private static final Color FG_COLOR_EVENT_OTHER_MONTH = Color.WHITE;

    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    public ScheduledReminder hourBefore;
    public ScheduledReminder dayBefore;
    public ScheduledReminder weekBefore;
    public ScheduledReminder monthBefore;

    /**
     * Colors for the event when viewed in the month view frame
     */
    public ScheduledEvent() {
        foregroundColor = FG_COLOR_EVENT;
        backgroundColor = BG_COLOR_EVENT;

        foregroundColorToday = FG_COLOR_EVENT_TODAY;
        backgroundColorToday = BG_COLOR_EVENT_TODAY;

        foregroundColorOtherMonth = FG_COLOR_EVENT_OTHER_MONTH;
        backgroundColorOtherMonth = BG_COLOR_EVENT_OTHER_MONTH;
    }

    /**
     * Constructor for the ScheduledEvent object. 
     * @param name The name of the event
     * @param date what day the event takes place on, then parsed as a LocalDate type
     * @param startTime the start time of the event, parsed as LocalTime type
     * @param endTime the end time of the event, parsed as LocalTime type
     * @param location The location of the event
     * @param reminders Takes a string of characters, and uses their placements to determine the frequency of reminders in the app
     */
    public ScheduledEvent(String name, String date, String startTime, String endTime, String location, String reminders) {
        this();
        this.name = name;
        this.location = location;
        this.date = LocalDate.parse(date);
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        // end = start.plusMinutes(lDur);
        this.location = location;
        briefString = toBriefString();

        String reminderName;
        String reminderDescription;
        if (reminders.charAt(0) == 'y') {
            LocalDateTime eventDateTime = LocalDateTime.of(this.date, this.startTime);
            LocalDateTime dt = eventDateTime.minusMonths(1);
            reminderName = "In one month: " + this.name;
            if (location.isBlank()) {
                reminderDescription = DateTimeUtilities.format(eventDateTime);
            } else {
                reminderDescription = DateTimeUtilities.format(eventDateTime) + " @ " + location;
            }
            monthBefore = new ScheduledReminder(reminderName, reminderDescription, 
                    dt, 0);

        }
        if (reminders.charAt(1) == 'y') {
            LocalDateTime dt = LocalDateTime.of(this.date, this.startTime).minusWeeks(1);
            reminderName = "In one week: " + this.name;
            if (location.isBlank()) {
                reminderDescription = "Next " + DateTimeUtilities.getDayOfWeek(this.date) + 
                        " at " + DateTimeUtilities.toAmPm(this.startTime);
            } else {
                reminderDescription = "Next " + DateTimeUtilities.getDayOfWeek(this.date) + 
                        " at " + DateTimeUtilities.toAmPm(this.startTime) + " @ " + location;
            }
            weekBefore = new ScheduledReminder(reminderName, reminderDescription, 
                    dt, 0);
        }
        if (reminders.charAt(2) == 'y') {
            LocalDateTime dt = LocalDateTime.of(this.date, this.startTime).minusDays(1);
            reminderName = "Tomorrow at " + DateTimeUtilities.toAmPm(this.startTime) + ": " + this.name;
            if (location.isBlank()) {
                reminderDescription = "";
            } else {
                reminderDescription = "@ " + location;
            }
            dayBefore = new ScheduledReminder(reminderName, reminderDescription, 
                    dt, 0);
        }
        if (reminders.charAt(3) == 'y') {
            LocalDateTime dt = LocalDateTime.of(this.date, this.startTime).minusHours(1);
            reminderName = "One hour from now: " + this.name;
            
            if (location.isBlank()) {
                reminderDescription = DateTimeUtilities.toAmPm(this.startTime);
            } else {
                reminderDescription = DateTimeUtilities.toAmPm(this.startTime) + " @ " + location;
            }
            hourBefore = new ScheduledReminder(reminderName, reminderDescription, 
                    dt, 0);
        }
    }

    /**
     * Constructor for Scheduled event object, used to create event without reminders
     * Also used when inputting LocalDate and LocalTime variables instead of strings for date, startTime, and endTime
     * Mainly used for validateForm method in AddEventFrame.java class
     * @param name The name of the event
     * @param date The date of the event
     * @param startTime The time the event starts
     * @param endTime The time the event ends
     * @param location The location of the event
     */
    public ScheduledEvent(String name, LocalDate date, LocalTime startTime, LocalTime endTime, String location) {
        this();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.location = location;
        briefString = toBriefString();
    }

    /**
     * Getter method for the name of the event
     * @return String of the name of the event
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter method for the date of the event
     * @param d The date of the event as a LocalDate type
     */
    public void setDate(LocalDate d) {
        date = d;
    }

    /**
     * Setter method for the name of the event
     * @param name The name of the event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter method for the startTime variable
     * @param t The start time of the event as a LocalTime type
     */
    public void setStartTime(LocalTime t) {
        startTime = t;
    }

    /**
     * Setter method for the endTime variable
     * @param t The end time of the event as a LocalTime type
     */
    public void setEndTime(LocalTime t) {
        endTime = t;
    }

    /**
     * Getter for the date of the event
     * @return LocalDate type variable of the date of the event
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Getter for the start time of the event 
     * @return The start time of the event
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /** 
     * Getter for the end time of the event 
     * @return LocalTime of the end time of the event
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for the location of the event
     * @return String of the location of the event
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Setter for the location of the event
     * @param s Where the event takes place
     */
    public void setLocation(String s) {
        this.location = s;
    }

    /**
     * Gets a reference to the hour-before reminder associated with this
     * event, if there is one
     * @return the reference, otherwise null
     */
    public ScheduledReminder getHourBeforeReminder() {
        return hourBefore;
    }

    /**
     * Gets a reference to the day-before reminder associated with this
     * event, if there is one
     * @return the reference, otherwise null
     */
    public ScheduledReminder getDayBeforeReminder() {
        return dayBefore;
    }

    /**
     * Gets a reference to the week-before reminder associated with this
     * event, if there is one
     * @return the reference, otherwise null
     */
    public ScheduledReminder getWeekBeforeReminder() {
        return weekBefore;
    }

    /**
     * Gets a reference to the month-before reminder associated with this
     * event, if there is one
     * @return the reference, otherwise null
     */
    public ScheduledReminder getMonthBeforeReminder() {
        return monthBefore;
    }

    /**
     * Sets or cancels the hour-before reminder for this event
     * @return a reference to the reminder, or null to cancel
     */
    public void setHourBeforeReminder(ScheduledReminder r) {
        hourBefore = r;
    }

    /**
     * Sets or cancels the day-before reminder for this event
     * @return a reference to the reminder, or null to cancel
     */
    public void setDayBeforeReminder(ScheduledReminder r) {
        dayBefore = r;
    }

    /**
     * Sets or cancels the week-before reminder for this event
     * @return a reference to the reminder, or null to cancel
     */
    public void setWeekBeforeReminder(ScheduledReminder r) {
        weekBefore = r;
    }

    /**
     * Sets or cancels the month-before reminder for this event
     * @return a reference to the reminder, or null to cancel
     */
    public void setMonthBeforeReminder(ScheduledReminder r) {
        monthBefore = r;
    }

    /**
     * Clears the hour-before reminder for this event
     */
    public void clearHourBeforeReminder() {
        hourBefore = null;
    }

    /**
     * Clears the day-before reminder for this event
     */
    public void clearDayBeforeReminder() {
        dayBefore = null;
    }

    /**
     * Clears the week-before reminder for this event
     */
    public void clearWeekBeforeReminder() {
        weekBefore = null;
    }

    /**
     * Clears the month-before reminder for this event
     */
    public void clearMonthBeforeReminder() {
        monthBefore = null;
    }

    /**
     * Gets a brief string for this event. This is used by the Calendar
     */
    public String toBriefString() {
        int hour = startTime.getHour();
        int minute = startTime.getMinute();
        String brief;
        if (hour >= 12) {
            if (hour > 12) {
                hour %= 12;
            }
            brief = Integer.toString(hour);
            if (minute > 0) {
                brief += String.format(":%02d", minute);
            }
            brief += "p";
        } else {
            if (hour == 0) {
                hour = 12;
            }
            brief = Integer.toString(hour);
            if (minute > 0) {
                brief += String.format(":%02d", minute);
            }
            brief += "a";
        }
        return brief + " " + name;
    }

    /**
     * Compares to ScheduledEvents
     * @param e the event to compare this to
     * @return 0 if they fall on the same date, < 0 if this one comes before,
     * otherwise > 0
     */
    @Override
    public int compareTo(ScheduledEvent e) {
        int dateComparison = date.compareTo(e.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return startTime.compareTo(e.startTime);
    }

    /**
     * Gets a String used by ScheduleIO to save whether the user
     * wants to be reminded about this event
     * @return A String containing something like "yyny" for example
     */
    public String getReminderFlagString() {
        String flags = "";
        if (monthBefore != null) {
            flags += 'y';
        } else {
            flags += 'n';
        }
        if (weekBefore != null) {
            flags += 'y';
        } else {
            flags += 'n';
        }
        if (dayBefore != null) {
            flags += 'y';
        } else {
            flags += 'n';
        }
        if (hourBefore != null) {
            flags += 'y';
        } else {
            flags += 'n';
        }
        return flags;
    }
}
