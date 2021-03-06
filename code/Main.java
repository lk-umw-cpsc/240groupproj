package code;

import java.awt.SystemTray;
import java.awt.Taskbar;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Project entry point. Spawns the BackgroundDaemon, which will
 * set up the SystemTrayManager and any child windows.
 */
public class Main {

    /**
     * Entry point
     * @param args command line args passed by the OS
     */
    public static void main(String[] args) {
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported on this OS. Exiting.");
            return;
        }
        // Make our UI look like the user's OS
        setLookAndFeel();

        // Spawn the background daemon
        new Thread(BackgroundDaemon.getInstance()).start();
    }

    /**
     * Sets the system look & feel to the user's OS L&F
     * Also sets the Mac Dock image if the user is on a Mac
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException | InstantiationException 
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Unable to set system L&F");
        }
        try {
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(ImageIO.read(new File("Images/calendar.png")));
        } catch (IOException e) {
            System.out.println("Unable to open calendar.png.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Note: System does not support changing taskbar icon.");
        }
    }

}