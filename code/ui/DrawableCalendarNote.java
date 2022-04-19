package code.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * An interface used to create graphics objects drawn on the calendar
 */
public interface DrawableCalendarNote {
    /**
     * A method called by the CalendarDayWidget class to draw one of its children
     * @param canvas The Graphics context the note will be drawn within. Note
     * that this isn't drawn to; it's used for determining sizing w/ fonts.
     * @param today true if should be styled as 'today'
     * @param thisMonth true if she be styled as 'day within current month'
     * @return a BufferedImage containing a pre-rendered image for this note
     */
    BufferedImage getNote(Graphics canvas, boolean today, boolean thisMonth);
}
