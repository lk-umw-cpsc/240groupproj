package code.ui.systemtray;

import java.awt.AWTException;
import java.awt.Graphics;
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

    private Image daySprite = null;
    private SpriteSheet calendarDay;
    private Calendar cal = Calendar.getInstance();
    private int dayNumber = cal.get(Calendar.DAY_OF_MONTH);

    public SystemTrayManager(BackgroundDaemon d, 
            AddReminderFrame arf, ReminderManagerFrame rmf) {
        daemon = d;
        this.addReminderFrame = arf;
        this.reminderManagerFrame = rmf;
        SystemTray systemTray = SystemTray.getSystemTray();
        BufferedImageLoader loader = new BufferedImageLoader();

        try 
        {
            calendarSprite = loader.loadImage("Sprites/CalendarSprites.png");
            calendarDay = new SpriteSheet(calendarSprite);
            daySprite = calendarDay.grabImage(dayNumber);
            
           
           
        } 
        catch (IOException e) 
        {
            System.out.println("Error loading tray icon");
            return;
        }

        trayIcon = new TrayIcon(daySprite, SysTrayInfoBuilder.buildInfo());
        
        PopupMenu popupMenu = new PopupMenu();


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
        try 
        {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Unable to create tray icon!");
        }
    }

    public void showNotification(String title, String message) 
    {

        BufferedImage notification = calendarDay.grabImage(32);

        notification = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics combined = notification.getGraphics();
        combined.drawImage(daySprite, 0, 0, null);
        combined.drawImage(notification, 0, 0, null);

        daySprite = notification;
        trayIcon.setImage(daySprite);

        trayIcon.displayMessage(title, message, MessageType.INFO);
        
    }

    public void dayChanged() 
    {
        cal = Calendar.getInstance();
        dayNumber = cal.get(Calendar.DAY_OF_MONTH);
        daySprite = calendarDay.grabImage(dayNumber);
        trayIcon.setImage(daySprite);
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
        reminderManagerFrame.appear();
    }

    private void exit(ActionEvent e) 
    {
        // Check to see if any windows are open first?
        daemon.exit();
    }
}