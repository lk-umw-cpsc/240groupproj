package code.ui.systemtray;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import code.BackgroundDaemon;
import code.ui.AddEventFrame;
import code.ui.AddReminderFrame;
import code.ui.MonthViewFrame;
import code.ui.ReminderManagerFrame;

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
    private AddEventFrame addEventFrame;
    private ReminderManagerFrame reminderManagerFrame;
    private MonthViewFrame monthViewFrame;

    private BackgroundDaemon daemon;

    private TrayIcon trayIcon;

    private Image daySprite = null;
    private SpriteSheet calendarDay;
    private Calendar cal = Calendar.getInstance();
    private int dayNumber = cal.get(Calendar.DAY_OF_MONTH);

    public SystemTrayManager(BackgroundDaemon d, 
            AddReminderFrame arf, AddEventFrame aef, ReminderManagerFrame rmf, MonthViewFrame mvf) {
        daemon = d;
        this.addReminderFrame = arf;
        this.addEventFrame = aef;
        this.reminderManagerFrame = rmf;
        this.monthViewFrame = mvf;
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
        MenuItem option = new MenuItem("Show Calendar...");
        option.addActionListener(this::showCalendar);
        popupMenu.add(option);

        // Events E
        Menu menuTab = new Menu("Events...");
        popupMenu.add(menuTab);

        option = new MenuItem("Add event...");
        option.addActionListener(this::addEvent);
        menuTab.add(option);
        
        //-----------
        // menuTab.addSeparator();

        option = new MenuItem("Manage events...");
        //option.addActionListener(this::manageReminders);
        // menuTab.add(option);

        // Reminders R
        menuTab = new Menu("Reminders...");
        popupMenu.add(menuTab);

        option = new MenuItem("Add reminder...");
        option.addActionListener(this::addReminder);
        menuTab.add(option);

        menuTab.addSeparator();

        option = new MenuItem("Add one hour reminder...");
        option.addActionListener(this::addOneHourReminder);
        menuTab.add(option);

        option = new MenuItem("Add evening reminder...");
        option.addActionListener(this::addEveningReminder);
        menuTab.add(option);

        option = new MenuItem("Add tomorrow reminder...");
        option.addActionListener(this::addTomorrowReminder);
        menuTab.add(option);

        menuTab.addSeparator();

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
        // popupMenu.add(menuTab);

        option = new MenuItem("Add new Prescription...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("Add new Doctor...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);
        
        //-----------
        // menuTab.addSeparator();

        option = new MenuItem("View Prescriptions...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);

        option = new MenuItem("View Doctors...");
        //option.addActionListener(this::addReminder);
        menuTab.add(option);


        //Settings S
        menuTab = new Menu("Settings...");
        // popupMenu.add(menuTab);


        //-----------
        // popupMenu.addSeparator();


        // Information I

        // option = new MenuItem("About Cal...");
        //option.addActionListener(this::addReminder);
        // popupMenu.add(option);

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

    public void showNotification(String title, String message) {

        BufferedImage notification = calendarDay.grabImage(32);

        notification = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics combined = notification.getGraphics();
        combined.drawImage(daySprite, 0, 0, null);
        combined.drawImage(notification, 0, 0, null);

        daySprite = notification;
        trayIcon.setImage(daySprite);

        trayIcon.displayMessage(title, message, MessageType.INFO);
        
    }

    public void dayChanged() {
        cal = Calendar.getInstance();
        dayNumber = cal.get(Calendar.DAY_OF_MONTH);
        daySprite = calendarDay.grabImage(dayNumber);
        trayIcon.setImage(daySprite);
    }

    private void showCalendar(ActionEvent e) {
        monthViewFrame.appear();
    }

    private void addReminder(ActionEvent e) {
        LocalDateTime inSixHours = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(6);
        if (inSixHours.getHour() > 21) {
            inSixHours = inSixHours.plusDays(1).truncatedTo(ChronoUnit.DAYS).plusHours(8);
        } else if (inSixHours.getHour() < 8) {
            inSixHours = inSixHours.truncatedTo(ChronoUnit.DAYS).plusHours(8);
        }
        addReminderFrame.appear(inSixHours);
    }

    private void addOneHourReminder(ActionEvent e) {
        addReminderFrame.appear(LocalDateTime.now().plusHours(1));
    }

    private void addEveningReminder(ActionEvent e) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target;
        // if after 7PM...
        if (now.getHour() >= 19) {
            // add an hour to the current time to get the target time
            target = now.plusHours(1);
        } else {
            // target time gets 8PM
            target = now.truncatedTo(ChronoUnit.DAYS).plusHours(20);
        }
        addReminderFrame.appear(target);
    }

    private void addTomorrowReminder(ActionEvent e) {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        tomorrow = tomorrow.truncatedTo(ChronoUnit.DAYS).plusHours(8);
        addReminderFrame.appear(tomorrow);
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

    private void addEvent(ActionEvent e) {
        addEventFrame.appear(LocalDateTime.now());
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