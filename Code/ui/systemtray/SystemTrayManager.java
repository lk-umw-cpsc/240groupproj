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

import code.BackgroundDaemon;

/**
 * The SystemTrayManager class creates a system tray icon for our app,
 * including a popupmenu of options to interact with the application.
 * 
 * The showNotification method will be called by the event manager to
 * prompt the user about the reminder they set.
 * 
 * TO-DO: Change from static to non-static methods, requiring an instance of
 * a SystemTrayManager to be created.
 */
public class SystemTrayManager {

    private BufferedImage calendarSprite;

    private BackgroundDaemon daemon;

    private TrayIcon trayIcon;

    public SystemTrayManager(BackgroundDaemon d) {
        daemon = d;
        SystemTray systemTray = SystemTray.getSystemTray();
        //TrayIcon icon2 = null;
        BufferedImageLoader loader = new BufferedImageLoader();

        Calendar cal = Calendar.getInstance();
        int dayNumber = cal.get(Calendar.DAY_OF_MONTH);
        Image daySprite = null;
        try {
            calendarSprite = loader.loadImage("Sprites/CalendarSprites.png");
            // calendarSprite = loader.loadImage(".\\Sprites\\CalendarSprites.png");
            SpriteSheet calendarDay = new SpriteSheet(calendarSprite);
            daySprite = calendarDay.grabImage(dayNumber);

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
        
        trayIcon = new TrayIcon(daySprite, SysTrayInfoBuilder.buildInfo());
        
        PopupMenu popupMenu = new PopupMenu();

        MenuItem showOption = new MenuItem("Show...");
        // showOption.addActionListener(SystemTrayManager::show);
        popupMenu.add(showOption);

        MenuItem exitOption = new MenuItem("Exit");
        exitOption.addActionListener(this::exit);
        popupMenu.add(exitOption);

        trayIcon.setPopupMenu(popupMenu);
        try {
            //systemTray.add(icon2);
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Unable to create tray icon!");
        }
    }

    public void showNotification(String message) {
        // to-do: implement me
    }

    public void dayChanged() {
        // update icon to show new date
    }

    private void exit(ActionEvent e) {
        daemon.exit();
    }
}