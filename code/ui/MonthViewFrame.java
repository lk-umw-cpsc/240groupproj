package code.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.ui.fonts.FontManager;

public class MonthViewFrame extends JFrame {

    private static final String[] DAYS_OF_THE_WEEK = {
        "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"
    };

    private static final String[] MONTHS = {
        null,
        "January", "February", "March", "April", "May",
        "June", "July", "August", "September", "October",
        "November", "December"
    };
    
    private BackgroundDaemon daemon;

    private DayWidget[] calendarWidgets = new DayWidget[35];
    private JLabel monthLabel;

    // private ?[][] calendarWidgets;

    public MonthViewFrame(BackgroundDaemon d) {
        super("Month View"); // get a better name for this
        daemon = d;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Box monthLabelContainer = Box.createHorizontalBox();
        monthLabel = new JLabel("placeholder");
        monthLabel.setFont(FontManager.getInstance().getBoldFont().deriveFont(32.0f));
        monthLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
        monthLabelContainer.add(Box.createHorizontalGlue());
        monthLabelContainer.add(monthLabel);
        monthLabelContainer.add(Box.createHorizontalGlue());
        add(monthLabelContainer);

        JPanel weekdayGridPanel = new JPanel();
        weekdayGridPanel.setLayout(new GridLayout(1, 7));

        Font fontDaysOfWeek = FontManager.getInstance().getRegularFont();

        for (int c = 0; c < 7; c++) {
            JPanel p = new JPanel();
            JLabel l = new JLabel(DAYS_OF_THE_WEEK[c]);
            l.setFont(fontDaysOfWeek);
            p.add(l);
            weekdayGridPanel.add(p);
        }
        weekdayGridPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, weekdayGridPanel.getPreferredSize().height));
        add(weekdayGridPanel);

        JPanel calendarGridPanel = new JPanel();
        calendarGridPanel.setLayout(new GridLayout(5, 7));        
        // calendarWidgets = new ?[5][7];
        for (int widget = 0; widget < calendarWidgets.length; widget++) {
            DayWidget w = new DayWidget(widget % 7 != 6, widget < 28);
            calendarWidgets[widget] = w;
            calendarGridPanel.add(w);
        }

        add(calendarGridPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear(LocalDateTime dt) {
        LocalDateTime today = LocalDateTime.now();

        int currentMonth = today.getMonthValue();
        monthLabel.setText(MONTHS[currentMonth] + " " + today.getYear());

        LocalDateTime firstOfTheMonth;
        // back up to the first day of the month
        for (firstOfTheMonth = dt; firstOfTheMonth.getDayOfMonth() > 1; firstOfTheMonth = firstOfTheMonth.minusDays(1));
        // back up to the Sunday before the first day of the month (if it didn't fall on a Sunday)
        LocalDateTime currentDay = firstOfTheMonth.minusDays(firstOfTheMonth.getDayOfWeek().getValue() % 7);
        for (int day = 0; day < 35; day++, currentDay = currentDay.plusDays(1)) {
            boolean isToday = currentDay.getDayOfMonth() == today.getDayOfMonth()
                    && currentDay.getMonthValue() == today.getMonthValue()
                    && currentDay.getYear() == today.getYear();
            boolean isThisMonth = currentDay.getMonthValue() == currentMonth;
            calendarWidgets[day].updateInfo(currentDay.getDayOfMonth(), isThisMonth, isToday);
        }

        setVisible(true);
    }

    public void appear() {
        appear(LocalDateTime.now());
    }
    
}
