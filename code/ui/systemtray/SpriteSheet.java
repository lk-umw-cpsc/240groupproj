package code.ui.systemtray;

import java.awt.image.BufferedImage;

public class SpriteSheet
{   
    private BufferedImage image;

    /**
     * This converts a BufferedImage to an image
     * @param image accepts a buffered image to add it to the sprite sheet.
     */
    public SpriteSheet(BufferedImage image)
    {
        this.image = image;
    }

    /**
     * This grabs the image from the sprite sheet located in the sprites folder in CalendarSprites.png
     * @param day accepts an int as the day variable
     * @return returns the position of the img in the png sprite sheet file associated with the location
     */
    public BufferedImage grabImage(int day)
    {
        
        //converts day to variable a to calculate what number to add
        int a = day;
        if (a <= 7)
        {
            a = 1;
        }
        else if (a >= 8 && a < 15 )
        {
            a = 8;
        }
        else if (a >= 15 && a < 22 )
        {
            a = 15;
        }
        else if (a >= 22 && a < 29 )
        {
            a = 22;
        }
        else
        {
            a = 29;
        }

        //Locates where the imaged/day is on the sprite sheet
        BufferedImage img = image.getSubimage(  (day-a) * 16, (int)Math.ceil((day-1)/7) * 16, 16, 16);
        return img;
    }


}