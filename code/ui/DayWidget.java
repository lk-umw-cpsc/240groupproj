package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import code.ui.fonts.FontManager;

public class DayWidget extends JComponent {

    private int day;
    private String dayAsString;
    private boolean thisMonth;
    private boolean today;

    private final boolean drawsRightBorder;
    private final boolean drawsBottomBorder;

    public DayWidget(boolean drawRightBorder, boolean drawBottomBorder) {
        drawsRightBorder = drawRightBorder;
        drawsBottomBorder = drawBottomBorder;

        setPreferredSize(new Dimension(200, 150));
        // setBorder(new MatteBorder(0, 0, 1, 1, Color.BLACK));
        setOpaque(true);
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

        initEvents(g);
        if (today) {
            g.setColor(FG_COLOR_TODAY);
        } else if (thisMonth) {
            g.setColor(FG_COLOR_THIS_MONTH);
        } else {
            g.setColor(FG_COLOR_OTHER_MONTH);
        }
        g.setFont(DAY_FONT);
        g.drawString(dayAsString, 6, 18);

        if (today) {
            g.setFont(EVENT_FONT);
            drawEvent(g, "8a Pretend this is an event", 0);
            drawEvent(g, "12p Another event", 1);
        }

        g.setColor(COLOR_CELL_BORDER);
        if (drawsRightBorder) {
            g.drawLine(width - 1, 0, width - 1, height - 1);
        }
        if (drawsBottomBorder) {
            g.drawLine(0, height - 1, width - 1, height - 1);
        }
    }

    private static final Color BG_COLOR_EVENT = new Color(173, 217, 22);
    private static final Color FG_COLOR_EVENT = Color.BLACK;
    private static final Font EVENT_FONT = FontManager.getInstance().getRegularFont().deriveFont(12.0f);
    private FontMetrics eventFontMetrics;

    private int eventY;
    private final int eventYStart = 25;
    private final int eventGap = 4;
    private void initEvents(Graphics g) {
        eventFontMetrics = g.getFontMetrics(EVENT_FONT);
        eventY = eventYStart;
    }
    private void drawEvent(Graphics g, String brief, int eventNumber) {
        // int width = eventFontMetrics.stringWidth(brief) + 8;

        // g.setColor(BG_COLOR_EVENT);
        // g.fillRoundRect(3, eventY - 12, width, 16, 2, 2);
        
        // g.setColor(FG_COLOR_EVENT);
        // g.drawString(brief, 7, 37 + eventNumber * 18);
        eventY += eventGap + drawStringInRectangle(g, EVENT_FONT, BG_COLOR_EVENT, FG_COLOR_EVENT, brief, 3, eventY, 3, 3);
    }

    public void updateInfo(int day, boolean isThisMonth, boolean isToday) {
        this.day = day;
        today = isToday;
        dayAsString = Integer.toString(day);
        thisMonth = isThisMonth;
    }

    private static int drawStringInRectangle(Graphics g, Font f, Color bg, Color fg, String text, int x, int y, int padding, int borderRadius) {
        FontMetrics fm = g.getFontMetrics(f);
        int h = fm.getHeight() + (padding << 1);
        int w = fm.stringWidth(text);
        g.setColor(bg);
        g.fillRoundRect(x, y, w + (padding << 1), h, borderRadius, borderRadius);
        g.setColor(fg);
        g.drawString(text, x + padding, y + padding + fm.getAscent());
        return h;
    }

    private static void enableFontAntiAliasing(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }
    
}
