package code.ui;

import code.schedule.ScheduledEvent;

public class YRange {

    public int top;
    public int bottom;
    public ScheduledEvent event;

    public boolean contains(int y) {
        return y >= top && y < bottom;
    }    
}
