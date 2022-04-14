package code.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface DrawableCalendarNote {
    BufferedImage getNote(Graphics canvas, boolean today, boolean thisMonth);
}
