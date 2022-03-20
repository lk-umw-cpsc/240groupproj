package code;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import code.ui.MainFrame;

public class Main {
    
    private static void createAndShowGUI() {
        MainFrame frame = new MainFrame();
        frame.createAndShow();
    }

    public static void main(String[] args) {
        // Make our UI look like the user's OS
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException 
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Unable to set system L&F");
        }
        // Call createAndShowGUI from the Swing event thread
        //SwingUtilities.invokeLater(Main::createAndShowGUI);

        //SystemTrayIcons.createAndShowGUI();
        
    }
}