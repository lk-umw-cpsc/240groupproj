package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import code.BackgroundDaemon;

public class MonthViewFrame extends JFrame {

    private static final String[] DAYS_OF_THE_WEEK = {
        "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"
    };
    
    private BackgroundDaemon daemon;

    // private ?[][] calendarWidgets;

    public MonthViewFrame(BackgroundDaemon d) {
        super("Month View"); // get a better name for this
        daemon = d;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel weekdayGridPanel = new JPanel();
        weekdayGridPanel.setLayout(new GridLayout(1, 7));

        for (int c = 0; c < 7; c++) {
            JPanel p = new JPanel();
            p.add(new JLabel(DAYS_OF_THE_WEEK[c]));
            weekdayGridPanel.add(p);
        }
        weekdayGridPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, weekdayGridPanel.getPreferredSize().height));
        add(weekdayGridPanel);

        JPanel calendarGridPanel = new JPanel();
        calendarGridPanel.setLayout(new GridLayout(5, 7));        
        // calendarWidgets = new ?[5][7];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 7; c++) {
                JPanel widget = new JPanel();
                widget.setPreferredSize(new Dimension(100, 100));
                widget.setBorder(new MatteBorder(0, 0, 1, 1, Color.black));
                widget.add(new JLabel(r + ", " + c));
                calendarGridPanel.add(widget);
                // calendarWidgets[r][c] = widget;
            }
        }

        add(calendarGridPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear(LocalDateTime dt) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 7; c++) {

            }
        }
        setVisible(true);
    }

    public void appear() {
        appear(LocalDateTime.now());
    }
    
}
