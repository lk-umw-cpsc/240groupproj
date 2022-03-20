package test.ui.systemtray;

import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.util.Calendar;

import test.BackgroundDaemon;
import test.ui.AddReminderFrame;

import java.io.IOException;

/**
 * The SystemTrayManager class creates a system tray icon for our app,
 * including a popupmenu of options to interact with the application.
 * 
 * The showNotification method will be called by the event manager to
 * prompt the user about the reminder they set.
 */
public class SystemTrayManager {

    private BufferedImage calendarSprite;

    private AddReminderFrame addReminderFrame;

    private BackgroundDaemon daemon;

    private TrayIcon trayIcon;

    public SystemTrayManager(BackgroundDaemon d, 
            AddReminderFrame addReminderFrame) {
        daemon = d;
        this.addReminderFrame = addReminderFrame;
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

        MenuItem showOption = new MenuItem("Add reminder...");
        showOption.addActionListener(this::addReminder);
        popupMenu.add(showOption);

        popupMenu.addSeparator();

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

    public void showNotification(String title, String message) {
        // to-do: figure out how to change the icon associated with this notification
        trayIcon.displayMessage(title, message, MessageType.INFO);
    }

    public void dayChanged() {
        // to-do: update icon to show new date
    }

    private void addReminder(ActionEvent e) {
        addReminderFrame.appear(false);
    }

    private void exit(ActionEvent e) {
        // Check to see if any windows are open first?
        daemon.exit();
    }
}