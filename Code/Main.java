package code;

import java.awt.SystemTray;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Project entry point. Spawns the BackgroundDaemon, which will
 * set up the SystemTrayManager and any child windows.
 */
public class Main {

    public static void main(String[] args) {
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported on this OS. Exiting.");
            return;
        }
        // Make our UI look like the user's OS
        setLookAndFeel();

        // Spawn the background daemon
        new Thread(new BackgroundDaemon()).start();
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException 
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Unable to set system L&F");
        }
    }
}