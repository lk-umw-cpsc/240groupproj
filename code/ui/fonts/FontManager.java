package code.ui.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
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

    private FontManager() {
        try {
            lightFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Quicksand-Regular.ttf")).deriveFont(16.0f);
            regularFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Quicksand-Medium.ttf")).deriveFont(16.0f);
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Quicksand-Bold.ttf")).deriveFont(16.0f);
            smallFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/MerriweatherSans-Bold.ttf")).deriveFont(11.0f);
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
}
