package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import code.BackgroundDaemon;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

/**
 * This class is the component placed within each calendar grid cell
 */
public class CalendarDayWidget extends JComponent implements MouseListener {

    private JPopupMenu rightClickMenu;
    private final BackgroundDaemon daemon;

    private int day;
    private String dayAsString;
    private boolean thisMonth;
    private boolean today;
    private boolean hovered;

    private LocalDate associatedDate;

    private final boolean drawsRightBorder;
    private final boolean drawsBottomBorder;

    private List<ScheduledEvent> events;

    /**
     * Creates a new CalendarDayWidget
     * @param drawRightBorder true if the right border should be drawn, otherwise false
     * @param drawBottomBorder true if the bottom border should be drawn, otherwise false
     */
    public CalendarDayWidget(boolean drawRightBorder, boolean drawBottomBorder) {
        drawsRightBorder = drawRightBorder;
        drawsBottomBorder = drawBottomBorder;

        daemon = BackgroundDaemon.getInstance();

        rightClickMenu = new JPopupMenu();
        JMenuItem menuOption = new JMenuItem("View schedule");
        menuOption.addActionListener(this::viewScheduleChosen);
        rightClickMenu.add(menuOption);

        menuOption = new JMenuItem("Add event");
        menuOption.addActionListener(this::addEventChosen);
        rightClickMenu.add(menuOption);

        setPreferredSize(new Dimension(200, 150));
        // setBorder(new MatteBorder(0, 0, 1, 1, Color.BLACK));
        setOpaque(true);
        addMouseListener(this);
        setToolTipText("Double-click to manage this day's events");
    }

    /**
     * Updates this CDW's display information
     * @param date The date shown within this cell 
     * @param isThisMonth Whether this cell falls within the viewed month
     * @param isToday Whether this cell is today
     * @param events A list of ScheduledEvents that fall on this day
     */
    public void updateInfo(LocalDate date, boolean isThisMonth, boolean isToday, List<ScheduledEvent> events) {
        this.associatedDate = date;
        day = date.getDayOfMonth();
        today = isToday;
        this.events = events;
        dayAsString = Integer.toString(day);
        thisMonth = isThisMonth;
        hovered = false;
        // setFocusable(true);
    }

    /**
     * Updates this cell's events and redraws the component
     * @param events
     */
    public void updateEvents(List<ScheduledEvent> events) {
        this.events = events;
        repaint();
    }

    private static final Color BG_COLOR_THIS_MONTH = new Color(255, 255, 255);
    private static final Color BG_COLOR_OTHER_MONTH = new Color(233, 233, 233);
    private static final Color BG_COLOR_TODAY = new Color(248, 255, 224);
    private static final Color FG_COLOR_THIS_MONTH = new Color(0, 0, 0);
    private static final Color FG_COLOR_OTHER_MONTH = new Color(32, 32, 32);
    private static final Color FG_COLOR_TODAY = new Color(0, 0, 0);
    private static final Color COLOR_CELL_BORDER = Color.BLACK;
    private static final Color COLOR_CELL_BORDER_HOVER = new Color(209, 71, 82);
    private static final Font DAY_FONT = FontManager.getInstance().getLightFont();

    /**
     * Draws the component and its information
     */
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        FontManager.enableFontAntiAliasing(g);

        if (today) {
            g.setColor(BG_COLOR_TODAY);
        } else if (thisMonth) {
            g.setColor(BG_COLOR_THIS_MONTH);
        } else {
            g.setColor(BG_COLOR_OTHER_MONTH);
        }
        g.fillRect(0, 0, width, height);

        if (hovered) {
            g.setColor(COLOR_CELL_BORDER_HOVER);
            g.drawRect(0, 0, width - 2, height - 2);
            g.drawRect(1, 1, width - 4, height - 4);
            g.drawRect(2, 2, width - 6, height - 6);
        }

        initEventDrawing(g);
        if (today) {
            g.setColor(FG_COLOR_TODAY);
        } else if (thisMonth) {
            g.setColor(FG_COLOR_THIS_MONTH);
        } else {
            g.setColor(FG_COLOR_OTHER_MONTH);
        }
        g.setFont(DAY_FONT);
        g.drawString(dayAsString, 6, 18);

        if (events != null) {
            for (int i = 0, size = events.size(); i < size; i++) {
                drawEvent(g, events.get(i));
            }
            // drawEvent(g, "8a Pretend this is an event", 0);
            // drawEvent(g, "12p Another event", 1);
        }

        g.setColor(COLOR_CELL_BORDER);
        if (drawsRightBorder) {
            g.drawLine(width - 1, 0, width - 1, height - 1);
        }
        if (drawsBottomBorder) {
            g.drawLine(0, height - 1, width - 1, height - 1);
        }
    }

    

    private int eventY;
    private final int eventYStart = 25;
    private final int eventGap = 4;
    /**
     * Initializes the process of drawing this cell's events
     * @param g The Graphics context to draw to
     */
    private void initEventDrawing(Graphics g) {
        eventY = eventYStart;
    }

    /**
     * Draws a given DrawableCalendarNote to the cell
     * @param g The Graphics context to draw to
     * @param n The DrawableCalendarNote to draw
     */
    private void drawEvent(Graphics g, DrawableCalendarNote n) {
        // int width = eventFontMetrics.stringWidth(brief) + 8;

        // g.setColor(BG_COLOR_EVENT);
        // g.fillRoundRect(3, eventY - 12, width, 16, 2, 2);
        
        // g.setColor(FG_COLOR_EVENT);
        // g.drawString(brief, 7, 37 + eventNumber * 18);
        /*if (today) {
            eventY += eventGap + FontManager.drawStringInRectangle(g, eventFontMetrics, BG_COLOR_EVENT_TODAY, FG_COLOR_EVENT_TODAY, brief, 3, eventY, 3, 3);
        } else if (thisMonth) {
            eventY += eventGap + FontManager.drawStringInRectangle(g, eventFontMetrics, BG_COLOR_EVENT, FG_COLOR_EVENT, brief, 3, eventY, 3, 3);
        } else {
            eventY += eventGap + FontManager.drawStringInRectangle(g, eventFontMetrics, BG_COLOR_EVENT_OTHER_MONTH, FG_COLOR_EVENT_OTHER_MONTH, brief, 3, eventY, 3, 3);
        }*/
        BufferedImage image = n.getNote(g, today, thisMonth);
        g.drawImage(image, 3, eventY, null);
        eventY += image.getHeight() + eventGap;

    }

    /**
     * Method called when the user right clicks on this cell and
     * chooses "Add event"
     * @param e Event info from Swing
     */
    private void addEventChosen(ActionEvent e) {
        daemon.getAddEventFrame().appear(this, associatedDate);
    }

    /**
     * Method called when the user double left-clicks or right clicks
     * and chooses "View schedule"
     * @param e Event info from Swing
     */
    private void viewScheduleChosen(ActionEvent e) {
        daemon.getDayViewFrame().appear(associatedDate, events);
    }

    /**
     * Method called by Swing when the user clicks on this CDW.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            daemon.getDayViewFrame().appear(associatedDate, events);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    /**
     * Method called when the user released a mouse button
     * within this CDW
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightClickMenu.show(this, e.getX(), e.getY());
        }
    }

    /**
     * Method called when the user's cursor enters this
     * CDW
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        hovered = true;
        repaint();
    }

    @Override
    /**
     * Method called when the user's cursor exits this CDW
     */
    public void mouseExited(MouseEvent e) {
        hovered = false;
        repaint();
    }
    
}
