import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    
    private static void createAndShowGUI() {
        MainFrame frame = new MainFrame();
        frame.createAndShow();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        // Make our UI look like the user's OS
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Call createAndShowGUI from the Swing event thread
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}