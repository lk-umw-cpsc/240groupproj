package code.ui;

import code.schedule.ScheduledEvent;

/**
 * Class used for hit detection within the DayViewFrame
 * 
 * Note: getters and setters not used here for the sake of cpu efficiency
 */
public class YRange {

    public int top;
    public int bottom;
    public ScheduledEvent event;

    /**
     * Checks to see if a given y value falls within this YRange
     * @param y the y value to check
     * @return true if it does, false otherwise
     */
    public boolean contains(int y) {
        return y >= top && y < bottom;
    }    
}
