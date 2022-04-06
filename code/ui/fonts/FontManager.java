package code.ui.fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

/**
 * Singleton class in charge of loading fonts
 */
public class FontManager {
    private static FontManager instance;

    private Font lightFont;
    private Font regularFont;
    private Font boldFont;
    private Font smallFont;
    private Font monospaceFont;

    private FontManager() {
        try {
            lightFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Quicksand-Regular.ttf")).deriveFont(16.0f);
            regularFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Quicksand-Medium.ttf")).deriveFont(16.0f);
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Quicksand-Bold.ttf")).deriveFont(16.0f);
            smallFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/MerriweatherSans-Bold.ttf")).deriveFont(11.0f);
            monospaceFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Cousine-Regular.ttf")).deriveFont(16.0f);
        } catch (IOException | FontFormatException e) {

       }
    }

    public static synchronized FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    public Font getLightFont() {
        return lightFont;
    }

    public Font getRegularFont() {
        return regularFont;
    }

    public Font getBoldFont() {
        return boldFont;
    }

    public Font getSmallFont() {
        return smallFont;
    }

    public Font getMonospaceFont() {
        return monospaceFont;
    }

    public static void enableFontAntiAliasing(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }

    public static int drawStringInRectangle(Graphics g, FontMetrics fm, Color bg, Color fg, String text, int x, int y, int padding, int borderRadius) {
        Rectangle2D r = fm.getStringBounds(text, g);
        int h = (int)r.getHeight() + (padding << 1);
        int w = (int)r.getWidth() + (padding << 1);
        g.setColor(bg);
        g.fillRoundRect(x, y, w, h, borderRadius, borderRadius);
        g.setColor(fg);
        g.drawString(text, x + padding, y + padding + fm.getAscent());
        return h;
    }

    public static void drawCenteredText(Graphics g, FontMetrics fm, String text, int x, int y, int width) {
        x += width / 2;
        Rectangle2D r = fm.getStringBounds(text, g);
        x -= r.getWidth() / 2;
        g.drawString(text, x, y);
    }
}
