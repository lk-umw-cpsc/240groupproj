package code.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import code.ui.fonts.FontManager;

public class BasicCalendarNote implements DrawableCalendarNote {
    protected Color foregroundColor;
    protected Color backgroundColor;
    protected Color foregroundColorToday;
    protected Color backgroundColorToday;
    protected Color foregroundColorOtherMonth;
    protected Color backgroundColorOtherMonth;

    protected BufferedImage image;

    protected String briefString;

    private static final Font NOTE_FONT = FontManager.getInstance().getSmallFont();
    
    private static final int PADDING = 3;
    private static final int BORDER_RADIUS = 3;

    @Override
    public BufferedImage getNote(Graphics canvas, boolean today, boolean thisMonth) {
        if (image != null) {
            return image;
        }
        FontMetrics fm = canvas.getFontMetrics(NOTE_FONT);
        Rectangle2D noteBox = fm.getStringBounds(briefString, canvas);
        int nameWidth = (int)noteBox.getWidth();

        image = new BufferedImage(
                nameWidth + 2 * PADDING, (int)noteBox.getHeight() + PADDING * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        FontManager.enableFontAntiAliasing(g);
        g.setFont(NOTE_FONT);
        if (today) {
            FontManager.drawStringInRectangle(g, fm, backgroundColorToday, foregroundColorToday, briefString, 0, 0, PADDING, BORDER_RADIUS);
        } else if (thisMonth) {
            FontManager.drawStringInRectangle(g, fm, backgroundColor, foregroundColor, briefString, 0, 0, PADDING, BORDER_RADIUS);
        } else {
            FontManager.drawStringInRectangle(g, fm, backgroundColorOtherMonth, foregroundColorOtherMonth, briefString, 0, 0, PADDING, BORDER_RADIUS);
        }
        return image;
    }

}