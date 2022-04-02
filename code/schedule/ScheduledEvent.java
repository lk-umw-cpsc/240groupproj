package code.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

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

public class ScheduledEvent implements Comparable<ScheduledEvent> {
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    public ScheduledReminder hourBefore;
    public ScheduledReminder dayBefore;
    public ScheduledReminder weekBefore;
    public ScheduledReminder monthBefore;

    public ScheduledEvent(String name, String date, String startTime, String endTime, String location) {
        this.name = name;
        this.location = location;
        this.date = LocalDate.parse(date);
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        // end = start.plusMinutes(lDur);
        this.location = location;
    }

    public ScheduledEvent(String desc, LocalDate date, LocalTime startTime, LocalTime endTime, String location) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = desc;
        this.location = location;
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

    @Override
    public int compareTo(ScheduledEvent e) {
        int dateComparison = date.compareTo(e.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return startTime.compareTo(e.startTime);
    }
}
