package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

        setPreferredSize(new Dimension(100, 100));
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

        if (today) {
            g.setColor(FG_COLOR_TODAY);
        } else if (thisMonth) {
            g.setColor(FG_COLOR_THIS_MONTH);
        } else {
            g.setColor(FG_COLOR_OTHER_MONTH);
        }
        g.setFont(DAY_FONT);
        g.drawString(dayAsString, 6, 18);

        g.setColor(COLOR_CELL_BORDER);
        if (drawsRightBorder) {
            g.drawLine(width - 1, 0, width - 1, height - 1);
        }
        if (drawsBottomBorder) {
            g.drawLine(0, height - 1, width - 1, height - 1);
        }
    }

    public void updateInfo(int day, boolean isThisMonth, boolean isToday) {
        this.day = day;
        today = isToday;
        dayAsString = Integer.toString(day);
        thisMonth = isThisMonth;
    }

    private static void enableFontAntiAliasing(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }
    
}
