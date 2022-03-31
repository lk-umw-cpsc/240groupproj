package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;

import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class DayWidget extends JComponent {

    private int day;
    private String dayAsString;
    private boolean thisMonth;
    private boolean today;

    private final boolean drawsRightBorder;
    private final boolean drawsBottomBorder;

    private List<ScheduledEvent> events;

    public DayWidget(boolean drawRightBorder, boolean drawBottomBorder) {
        drawsRightBorder = drawRightBorder;
        drawsBottomBorder = drawBottomBorder;

        setPreferredSize(new Dimension(200, 150));
        // setBorder(new MatteBorder(0, 0, 1, 1, Color.BLACK));
        setOpaque(true);
    }

    public void updateInfo(int day, boolean isThisMonth, boolean isToday, List<ScheduledEvent> events) {
    // public void updateInfo(int day, boolean isThisMonth, boolean isToday, List<ScheduledEvent> events) {
        this.day = day;
        today = isToday;
        this.events = events;
        dayAsString = Integer.toString(day);
        thisMonth = isThisMonth;
    }

    private static final Color BG_COLOR_THIS_MONTH = new Color(255, 255, 255);
    private static final Color BG_COLOR_OTHER_MONTH = new Color(233, 233, 233);
    private static final Color BG_COLOR_TODAY = new Color(248, 255, 224);
    private static final Color FG_COLOR_THIS_MONTH = new Color(0, 0, 0);
    private static final Color FG_COLOR_OTHER_MONTH = new Color(32, 32, 32);
    private static final Color FG_COLOR_TODAY = new Color(0, 0, 0);
    private static final Color COLOR_CELL_BORDER = Color.BLACK;
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
        g.fillRect(0, 0, getWidth(), getHeight());

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
    private static final Font EVENT_FONT = FontManager.getInstance().getRegularFont().deriveFont(14.0f);
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
    
}
