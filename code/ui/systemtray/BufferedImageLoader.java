package code.ui.systemtray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class is used to create a buffered Image
 */
public class BufferedImageLoader 
{
    private BufferedImage image;

    /**
     * This takes the the path and converts it file to an buffered image
     * @param path This accepts a string as the path
     * @return this returns teh image which takes a path and file and converts it
     * @throws IOException throws if file is not found
     */
    public BufferedImage loadImage(String path) throws IOException
    {
        image = ImageIO.read(new File(path));
        return image;
    }
    
}
