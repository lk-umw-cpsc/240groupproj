package test.ui.systemtray;

import java.awt.image.BufferedImage;

public class SpriteSheet
{   
    private BufferedImage image;

    public SpriteSheet(BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage grabImage(int day)
    {
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

        BufferedImage img = image.getSubimage(  (day-a) * 16, (int)Math.ceil((day-1)/7) * 16, 16, 16);
        return img;
    }


}