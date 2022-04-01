package code.ui;

import java.awt.Dimension;

import javax.swing.JComponent;

public class DayViewWidget extends JComponent {

    private static final int PIXELS_PER_HOUR = 64;

    public DayViewWidget() {
        setPreferredSize(new Dimension(400, PIXELS_PER_HOUR * 24));
    }
    
}
