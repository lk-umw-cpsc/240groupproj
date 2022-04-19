package code.schedule;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import code.ui.BasicCalendarNote;

/**
 * This represents a blocked out portion of the user's schedule.
 * 
 * Doctor's appointments, scheduled meetings, etc. should all
 * BE A ScheduledEvent (subclass)
 * 
 * Includes a List of optional ScheduledReminders to remind
 * the user about the event/appointment/etc.
 * 
 * Start time
 * End time/duration
 * Desc
 * Location?
 * 
 * Implementation assigned to Jayden
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

    public ScheduledEvent() {
        // colors for the event when viewed in the month view frame
        foregroundColor = FG_COLOR_EVENT;
        backgroundColor = BG_COLOR_EVENT;

        foregroundColorToday = FG_COLOR_EVENT_TODAY;
        backgroundColorToday = BG_COLOR_EVENT_TODAY;

        foregroundColorOtherMonth = FG_COLOR_EVENT_OTHER_MONTH;
        backgroundColorOtherMonth = BG_COLOR_EVENT_OTHER_MONTH;
    }

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

    public ScheduledEvent(String desc, LocalDate date, LocalTime startTime, LocalTime endTime, String location) {
        this();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = desc;
        this.location = location;
        briefString = toBriefString();
    }

    public String getName() {
        return this.name;
    }

    public void setDate(LocalDate d) {
        date = d;
    }

    public void setStartTime(LocalTime t) {
        startTime = t;
    }

    public void setEndTime(LocalTime t) {
        endTime = t;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

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
