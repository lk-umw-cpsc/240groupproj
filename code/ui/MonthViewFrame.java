package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class MonthViewFrame extends JFrame implements MouseListener {

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

    private Map<LocalDate, CalendarDayWidget> widgetsMap;

    private LocalDateTime viewedDate;
    
    private BackgroundDaemon daemon;

    private CalendarDayWidget[] calendarWidgets = new CalendarDayWidget[35];
    private JLabel monthLabel;
    private JLabel nextButton;
    private JLabel previousButton;

    private final Color CALENDAR_HEADING_BACKGROUND_COLOR = new Color(209, 71, 82);
    private final Color CALENDAR_HEADING_FOREGROUND_COLOR = Color.WHITE;

    // private ?[][] calendarWidgets;

    public MonthViewFrame(BackgroundDaemon d) {
        super("Month View"); // get a better name for this
        daemon = d;

        widgetsMap = new HashMap<>();

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Box monthLabelContainer = Box.createHorizontalBox();
        monthLabelContainer.setOpaque(true);
        monthLabelContainer.setBackground(CALENDAR_HEADING_BACKGROUND_COLOR);
        monthLabel = new JLabel("placeholder");
        monthLabel.setForeground(CALENDAR_HEADING_FOREGROUND_COLOR);
        monthLabel.setFont(FontManager.getInstance().getBoldFont().deriveFont(32.0f));
        monthLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
        try {
            previousButton = new JLabel(new ImageIcon(ImageIO.read(new File("Images/previous.png"))));
            previousButton.addMouseListener(this);
            previousButton.setBorder(new EmptyBorder(24, 16, 8, 0));
            monthLabelContainer.add(previousButton);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        monthLabelContainer.add(Box.createHorizontalGlue());
        monthLabelContainer.add(monthLabel);
        monthLabelContainer.add(Box.createHorizontalGlue());
        try {
            nextButton = new JLabel(new ImageIcon(ImageIO.read(new File("Images/next.png"))));
            nextButton.addMouseListener(this);
            nextButton.setBorder(new EmptyBorder(24, 0, 8, 16));
            monthLabelContainer.add(nextButton);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        add(monthLabelContainer);

        JPanel weekdayGridPanel = new JPanel();
        weekdayGridPanel.setOpaque(true);
        weekdayGridPanel.setBackground(CALENDAR_HEADING_BACKGROUND_COLOR);
        weekdayGridPanel.setLayout(new GridLayout(1, 7));

        Font fontDaysOfWeek = FontManager.getInstance().getRegularFont();

        for (int c = 0; c < 7; c++) {
            JPanel p = new JPanel();
            p.setBackground(CALENDAR_HEADING_BACKGROUND_COLOR);
            JLabel l = new JLabel(DAYS_OF_THE_WEEK[c]);
            l.setForeground(CALENDAR_HEADING_FOREGROUND_COLOR);
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
            CalendarDayWidget w = new CalendarDayWidget(widget % 7 != 6, widget < 28);
            calendarWidgets[widget] = w;
            calendarGridPanel.add(w);
        }

        add(calendarGridPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear(LocalDateTime dt) {
        updateGrid(dt);
        setVisible(true);
    }

    private void updateGrid(LocalDateTime dt) {
        this.viewedDate = dt;
        LocalDate today = LocalDate.now();

        int currentMonth = dt.getMonthValue();
        monthLabel.setText(MONTHS[currentMonth] + " " + dt.getYear());
        LocalDate firstOfTheMonth = LocalDate.of(dt.getYear(), dt.getMonthValue(), 1);
        Lock lock = daemon.getLock();
        lock.lock();
        widgetsMap.clear();
        Map<LocalDate, List<ScheduledEvent>> events = daemon.getEvents();
        // back up to the first day of the month
        // for (firstOfTheMonth = dt; firstOfTheMonth.getDayOfMonth() > 1; firstOfTheMonth = firstOfTheMonth.minusDays(1));
        // back up to the Sunday before the first day of the month (if it didn't fall on a Sunday)
        LocalDate currentDay = firstOfTheMonth.minusDays(firstOfTheMonth.getDayOfWeek().getValue() % 7);
        for (int day = 0; day < 35; day++, currentDay = currentDay.plusDays(1)) {
            List<ScheduledEvent> happeningThisDay = events.get(currentDay);
            boolean isToday = currentDay.equals(today);
            boolean isThisMonth = currentDay.getMonthValue() == currentMonth;
            widgetsMap.put(currentDay, calendarWidgets[day]);
            calendarWidgets[day].updateInfo(currentDay, isThisMonth, isToday, happeningThisDay);
        }
        lock.unlock();
        repaint();
    }

    private void nextButtonPressed() {
        updateGrid(viewedDate.plusMonths(1));
    }

    private void previousButtonPressed() {
        updateGrid(viewedDate.minusMonths(1));
    }

    public void updateDay(LocalDate d, List<ScheduledEvent> events) {
        CalendarDayWidget widget = widgetsMap.get(d);
        if (widget != null) {
            widget.updateEvents(events);
        }
    }

    public void appear() {
        appear(LocalDateTime.now());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == nextButton) {
            nextButtonPressed();
        } else if(e.getSource() == previousButton) {
            previousButtonPressed();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
