package code.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import code.BackgroundDaemon;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

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

    public CalendarDayWidget(boolean drawRightBorder, boolean drawBottomBorder) {
        drawsRightBorder = drawRightBorder;
        drawsBottomBorder = drawBottomBorder;

        daemon = BackgroundDaemon.getInstance();

        rightClickMenu = new JPopupMenu();
        JMenuItem menuOption = new JMenuItem("View schedule");
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

    public void updateInfo(LocalDate date, boolean isThisMonth, boolean isToday, List<ScheduledEvent> events) {
    // public void updateInfo(int day, boolean isThisMonth, boolean isToday, List<ScheduledEvent> events) {
        this.associatedDate = date;
        day = date.getDayOfMonth();
        today = isToday;
        this.events = events;
        dayAsString = Integer.toString(day);
        thisMonth = isThisMonth;
        hovered = false;
        // setFocusable(true);
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

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        enableFontAntiAliasing(g);

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
            ((Graphics2D)g).setStroke(new BasicStroke(3.0f));
            g.drawRect(1, 1, width - 5, height - 5);
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

        g.setFont(EVENT_FONT);
        if (!events.isEmpty()) {
            for (int i = 0, size = events.size(); i < size; i++) {
                drawEvent(g, events.get(i).toBriefString(), i);
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

    private static final Color BG_COLOR_EVENT_TODAY = new Color(173, 217, 22);
    private static final Color FG_COLOR_EVENT_TODAY = Color.BLACK;
    private static final Color BG_COLOR_EVENT = new Color(209, 71, 82);
    private static final Color FG_COLOR_EVENT = Color.WHITE;
    private static final Color BG_COLOR_EVENT_OTHER_MONTH = new Color(237, 140, 148);
    private static final Color FG_COLOR_EVENT_OTHER_MONTH = Color.WHITE;
    private static final Font EVENT_FONT = FontManager.getInstance().getSmallFont();
    private FontMetrics eventFontMetrics;

    private int eventY;
    private final int eventYStart = 25;
    private final int eventGap = 4;
    private void initEventDrawing(Graphics g) {
        eventFontMetrics = g.getFontMetrics(EVENT_FONT);
        eventY = eventYStart;
    }

    private void drawEvent(Graphics g, String brief, int eventNumber) {
        // int width = eventFontMetrics.stringWidth(brief) + 8;

        // g.setColor(BG_COLOR_EVENT);
        // g.fillRoundRect(3, eventY - 12, width, 16, 2, 2);
        
        // g.setColor(FG_COLOR_EVENT);
        // g.drawString(brief, 7, 37 + eventNumber * 18);
        if (today) {
            eventY += eventGap + drawStringInRectangle(g, eventFontMetrics, BG_COLOR_EVENT_TODAY, FG_COLOR_EVENT_TODAY, brief, 3, eventY, 3, 3);
        } else if (thisMonth) {
            eventY += eventGap + drawStringInRectangle(g, eventFontMetrics, BG_COLOR_EVENT, FG_COLOR_EVENT, brief, 3, eventY, 3, 3);
        } else {
            eventY += eventGap + drawStringInRectangle(g, eventFontMetrics, BG_COLOR_EVENT_OTHER_MONTH, FG_COLOR_EVENT_OTHER_MONTH, brief, 3, eventY, 3, 3);
        }
    }

    private static int drawStringInRectangle(Graphics g, FontMetrics fm, Color bg, Color fg, String text, int x, int y, int padding, int borderRadius) {
        Rectangle2D r = fm.getStringBounds(text, g);
        int h = (int)r.getHeight() + (padding << 1);
        int w = (int)r.getWidth() + (padding << 1);
        g.setColor(bg);
        g.fillRoundRect(x, y, w, h, borderRadius, borderRadius);
        g.setColor(fg);
        g.drawString(text, x + padding, y + padding + fm.getAscent());
        return h;
    }

    private static void enableFontAntiAliasing(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }

    private void addEventChosen(ActionEvent e) {
        daemon.getAddEventFrame().appear(this, associatedDate);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            daemon.getdDayViewFrame().appear(associatedDate, events);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightClickMenu.show(this, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hovered = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hovered = false;
        repaint();
    }
    
}
