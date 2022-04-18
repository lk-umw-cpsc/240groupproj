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
 * Singleton class in charge of loading fonts and drawing text
 */
public class FontManager {
    private static FontManager instance;

    private Font lightFont;
    private Font regularFont;
    private Font boldFont;
    private Font smallFont;
    private Font monospaceFont;

    /**
     * Loads fonts from the fonts folder
     */
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

    /**
     * Returns an instance of the FontManager. This causes the program
     * to load all needed fonts on the initial call to getInstance()
     * @return a reference to the FontManager singleton
     */
    public static synchronized FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    /**
     * Gets the application's light font
     * @return a reference to the Font
     */
    public Font getLightFont() {
        return lightFont;
    }

    /**
     * Gets the application's regular font
     * @return a reference to the Font
     */
    public Font getRegularFont() {
        return regularFont;
    }

    /**
     * Gets the application's bold font
     * @return a reference to the Font
     */
    public Font getBoldFont() {
        return boldFont;
    }

    /**
     * Gets the application's small font
     * @return a reference to the font
     */
    public Font getSmallFont() {
        return smallFont;
    }

    /**
     * Get the application's monospace (fixed-width) font
     * @return a reference to the font
     */
    public Font getMonospaceFont() {
        return monospaceFont;
    }

    /**
     * Enables font anti-aliasing for a given Graphics context
     * @param graphics the Graphics object to enable this on
     */
    public static void enableFontAntiAliasing(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }

    /**
     * Draws a String inside of a box with padding to the given Graphics context.
     * The box's upper-left corner will be placed at the x and y coordinates
     * given. Padding is placed around the text, measured in pixels. The radius
     * of the rounded corners can also be given.
     * @param g The Graphics context to draw the text in
     * @param fm A FontMetrics object used to measure the width of the text
     * @param bg The background color of the box
     * @param fg The foreground color of the text
     * @param text The text to draw
     * @param x The x coordinate of the bounding box's upper-left corner
     * @param y The y coordinate of the bounding box's upper-left corner
     * @param padding Padding, in pixels, to place around the text
     * @param borderRadius The radius, in pixels, of the box's corners (rounding them)
     * @return The height of the drawn box
     */
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

    /**
     * Draws centered text, with the x and y coordinates given being considered
     * the upper-left-most portion of the text space. 
     * @param g The Graphics context to draw the text in
     * @param fm A FontMetrics used to measure the size of the String
     * @param text The text to draw
     * @param x the x coordinate of the upper-left bound
     * @param y the y coordinate of the upper-right bound
     * @param width The width to consider. x + width is the right-bound
     */
    public static void drawCenteredText(Graphics g, FontMetrics fm, String text, int x, int y, int width) {
        x += width / 2;
        Rectangle2D r = fm.getStringBounds(text, g);
        x -= r.getWidth() / 2;
        g.drawString(text, x, y);
    }
}
