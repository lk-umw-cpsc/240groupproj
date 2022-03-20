package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class AnimatedPanel extends JPanel implements Runnable {

    private BufferedImage character;

    private int x;
    private int brightness;
    private boolean brightnessDirection;
    
    public AnimatedPanel() {
        try {
            character = ImageIO.read(new File(".//Sprites//sprite1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(250, 250));
        setMaximumSize(getPreferredSize());
        x = 2;
    }

    @Override
    public void run() {
        while (true) {
            x += 1;
            if (x > 250 + character.getWidth())
                x = -character.getWidth();
            if (brightnessDirection) {
                brightness--;
                if (brightness <= 0)
                    brightnessDirection = false;
            } else {
                brightness++;
                if (brightness >= 255)
                    brightnessDirection = true;
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(brightness, brightness, brightness));
        g.drawString("-CPSC 240-", 75, 180);
        g.drawImage(character, x, 100, character.getWidth() * 2, character.getHeight() * 2, null);
    }

}
