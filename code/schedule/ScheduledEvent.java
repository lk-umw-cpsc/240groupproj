package code.schedule;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;

import code.ui.DrawableCalendarNote;
import code.ui.fonts.FontManager;

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

public class ScheduledEvent implements Comparable<ScheduledEvent>, DrawableCalendarNote {
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location = "none2";

    private BufferedImage image;

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

    public ScheduledReminder getHourBeforeReminder() {
        return hourBefore;
    }

    public ScheduledReminder getDayBeforeReminder() {
        return dayBefore;
    }

    public ScheduledReminder getWeekBeforeReminder() {
        return weekBefore;
    }

    public ScheduledReminder getMonthBeforeReminder() {
        return monthBefore;
    }

    public void setHourBeforeReminder(ScheduledReminder r) {
        hourBefore = r;
    }

    public void setDayBeforeReminder(ScheduledReminder r) {
        dayBefore = r;
    }

    public void setWeekBeforeReminder(ScheduledReminder r) {
        weekBefore = r;
    }

    public void setMonthBeforeReminder(ScheduledReminder r) {
        monthBefore = r;
    }

    public void clearHourBeforeReminder() {
        hourBefore = null;
    }

    public void clearDayBeforeReminder() {
        dayBefore = null;
    }

    public void clearWeekBeforeReminder() {
        weekBefore = null;
    }

    public void clearMonthBeforeReminder() {
        monthBefore = null;
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

    private static final Font EVENT_FONT = FontManager.getInstance().getSmallFont();

    private static final Color BG_COLOR_EVENT_TODAY = new Color(173, 217, 22);
    private static final Color FG_COLOR_EVENT_TODAY = Color.BLACK;
    private static final Color BG_COLOR_EVENT = new Color(209, 71, 82);
    private static final Color FG_COLOR_EVENT = Color.WHITE;
    private static final Color BG_COLOR_EVENT_OTHER_MONTH = new Color(237, 140, 148);
    private static final Color FG_COLOR_EVENT_OTHER_MONTH = Color.WHITE;

    @Override
    public BufferedImage getNote(Graphics canvas, boolean today, boolean thisMonth) {
        if (image != null) {
            return image;
        }
        final int padding = 3;
        FontMetrics fm = canvas.getFontMetrics(EVENT_FONT);
        Rectangle2D nameBox = fm.getStringBounds(toBriefString(), canvas);
        int nameWidth = (int)nameBox.getWidth();

        image = new BufferedImage(
                nameWidth + 2 * padding, (int)nameBox.getHeight() + padding * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        FontManager.enableFontAntiAliasing(g);
        g.setFont(EVENT_FONT);
        if (today) {
            FontManager.drawStringInRectangle(g, fm, BG_COLOR_EVENT_TODAY, FG_COLOR_EVENT_TODAY, toBriefString(), 0, 0, padding, 3);
        } else if (thisMonth) {
            FontManager.drawStringInRectangle(g, fm, BG_COLOR_EVENT, FG_COLOR_EVENT, toBriefString(), 0, 0, padding, 3);
        } else {
            FontManager.drawStringInRectangle(g, fm, BG_COLOR_EVENT_OTHER_MONTH, FG_COLOR_EVENT_OTHER_MONTH, toBriefString(), 0, 0, padding, 3);
        }
        return image;
    }
}
