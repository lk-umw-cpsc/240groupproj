package code.ui.systemtray;

import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.util.Calendar;

import code.BackgroundDaemon;
import code.ui.AddReminderFrame;
import code.ui.ReminderManagerFrame;

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
    private ReminderManagerFrame reminderManagerFrame;

    private BackgroundDaemon daemon;

    private TrayIcon trayIcon;

    public SystemTrayManager(BackgroundDaemon d, 
            AddReminderFrame arf, ReminderManagerFrame rmf) {
        daemon = d;
        this.addReminderFrame = arf;
        this.reminderManagerFrame = rmf;
        SystemTray systemTray = SystemTray.getSystemTray();
        //TrayIcon icon2 = null;
        BufferedImageLoader loader = new BufferedImageLoader();

        Calendar cal = Calendar.getInstance();
        int dayNumber = cal.get(Calendar.DAY_OF_MONTH);
        Image daySprite = null;
        try {
            calendarSprite = loader.loadImage("Sprites/CalendarSprites.png");
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

        


        //convert menuitem to menu and menuitem is a submenu
        //Note for myself (Joon)
        
        // Calendar C
        MenuItem option = new MenuItem("Calendar...");
        //option.addActionListener(this::addReminder);
        popupMenu.add(option);

        // Events E
        Menu menuTab = new Menu("Events...");
        popupMenu.add(menuTab);

        option = new MenuItem("Add event...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);
        
        //-----------
        menuTab.addSeparator();

        option = new MenuItem("Manage events...");
        //option.addActionListener(this::manageReminders);
        menuTab.add(option);

        // Reminders R
        menuTab = new Menu("Reminders...");
        popupMenu.add(menuTab);

        option = new MenuItem("Add reminder...");
        option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("Add daily reminder...");
        option.addActionListener(this::addDailyReminder);
        menuTab.add(option);

        option = new MenuItem("Add weekly reminder...");
        option.addActionListener(this::addWeeklyReminder);
        menuTab.add(option);

        option = new MenuItem("Add monthly reminder...");
        option.addActionListener(this::addMonthlyReminder);
        menuTab.add(option);

        //-----------
        menuTab.addSeparator();

        option = new MenuItem("Manage reminders...");
        option.addActionListener(this::manageReminders);
        menuTab.add(option);

        
        // Medical M

        menuTab = new Menu("Medical...");
        popupMenu.add(menuTab);

        option = new MenuItem("Add new Prescription...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("Add new Doctor...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);
        
        //-----------
        menuTab.addSeparator();

        option = new MenuItem("View Prescriptions...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("View Doctors...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);


        //Settings S
        menuTab = new Menu("Settings...");
        popupMenu.add(menuTab);


        //-----------
        popupMenu.addSeparator();


        // Information I
        
        menuTab = new Menu("Information...");
        popupMenu.add(menuTab);

        option = new MenuItem("View Prescriptions...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("View Doctors...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        menuTab.addSeparator();

        option = new MenuItem("Add new Prescription...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("Add new Doctor...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);
        

        //-----------
        popupMenu.addSeparator();


        // Exit X
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
        addReminderFrame.appear(0);
    }

    private void addDailyReminder(ActionEvent e) {
        addReminderFrame.appear(1);
    }

    private void addWeeklyReminder(ActionEvent e) {
        addReminderFrame.appear(7);
    }

    private void addMonthlyReminder(ActionEvent e) {
        addReminderFrame.appear(30);
    }

    private void manageReminders(ActionEvent e) {
        reminderManagerFrame.appear();;
    }

    private void exit(ActionEvent e) {
        // Check to see if any windows are open first?
        daemon.exit();
    }
}