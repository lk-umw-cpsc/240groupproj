package code.ui.systemtray;

import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.util.Calendar;

import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SystemTrayManager {

    private static JFrame window;
    private static Calendar cal = Calendar.getInstance();
    private static BufferedImage calendarSprite = null;
    //private static boolean notification = false;

    static void createAndShowGUI() {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            TrayIcon icon = null;
            //TrayIcon icon2 = null;
            BufferedImageLoader loader = new BufferedImageLoader();

            

            try {
                int dayNumber = cal.get(Calendar.DAY_OF_MONTH);
                calendarSprite = loader.loadImage("Sprites/CalendarSprites.png");
                // calendarSprite = loader.loadImage(".\\Sprites\\CalendarSprites.png");
                SpriteSheet calendarDay = new SpriteSheet(calendarSprite);
                Image daySprite = calendarDay.grabImage(dayNumber);

                ;
                icon = new TrayIcon(daySprite, SysTrayInfoBuilder.buildInfo());
                
                //Image alertNotify = calendarDay.grabImage(32);
                //icon2 = new TrayIcon(alertNotify);

                /*
                notification = true;
                if (notification)
                {
                    Image alertNotify = calendarDay.grabImage(32);
                }
                */


            } catch (IOException e) {
                System.out.println("Error loading tray icon");
                return;
            }
            
            PopupMenu popupMenu = new PopupMenu();

            MenuItem showOption = new MenuItem("Show...");
            showOption.addActionListener(SystemTrayManager::show);
            popupMenu.add(showOption);

            MenuItem exitOption = new MenuItem("Exit");
            exitOption.addActionListener(SystemTrayManager::exit);
            popupMenu.add(exitOption);

            icon.setPopupMenu(popupMenu);
            try {
                //systemTray.add(icon2);
                systemTray.add(icon);
            } catch (AWTException e) {
                System.out.println("Unable to create tray icon!");
            }
        }

        window = new JFrame("System Tray Test");
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Box horizontal = Box.createHorizontalBox();
        Box vertical = Box.createVerticalBox();
        
        horizontal.add(Box.createHorizontalStrut(10));
        horizontal.add(vertical);
        horizontal.add(Box.createHorizontalStrut(10));

        vertical.add(Box.createVerticalStrut(10));
        vertical.add(new JLabel("Try closing me!"));
        vertical.add(new JLabel("Then try to open me from the system tray."));
        vertical.add(Box.createVerticalStrut(10));

        window.add(horizontal);
        window.pack();
        window.setVisible(true);
    }

    private static void exit(ActionEvent e) {
        System.exit(0);
    }

    private static void show(ActionEvent e) {
        window.setVisible(true);
        window.toFront();
        window.requestFocus();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SystemTrayManager::createAndShowGUI);
    }
}