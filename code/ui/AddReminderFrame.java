package code.ui;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import code.BackgroundDaemon;

public class AddReminderFrame extends JFrame {

    private static final int PADDING = 16;

    /**
     * Reminder form:
     * 
     * Name:
     * [field]
     * 
     * Description (optional):
     * [field]
     * 
     * Date:
     * [field] (button that opens date picker)
     * 
     * Time:
     * [field][field][drop down]
     */

    private BackgroundDaemon daemon;
        
    public AddReminderFrame(BackgroundDaemon daemon) {
        super("Create new reminder");
        this.daemon = daemon;
        setResizable(false);
    }
    
    public void build() {
        Box horizontallyPaddedPanel = Box.createHorizontalBox();
        Box mainPanel = Box.createVerticalBox();

        horizontallyPaddedPanel.add(Box.createHorizontalStrut(PADDING));
        horizontallyPaddedPanel.add(mainPanel);
        horizontallyPaddedPanel.add(Box.createHorizontalStrut(PADDING));
        
        mainPanel.add(Box.createVerticalStrut(PADDING));
        // Create another box whose children are displayed left to right
        Box layer = Box.createHorizontalBox();

            layer.add(new JLabel("Name:"));
            // Add horizontal glue after the label, left-aligning the label
            layer.add(Box.createHorizontalGlue());
        mainPanel.add(layer);
        mainPanel.add(new JTextField(24));

        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Description (optional):"));
            layer.add(Box.createHorizontalGlue());
        mainPanel.add(layer);
        mainPanel.add(new JTextField(24));

        layer = Box.createHorizontalBox();
        layer.add(new JButton("Tester"));
        layer.add(Box.createHorizontalGlue());
        mainPanel.add(layer);

        mainPanel.add(Box.createVerticalStrut(PADDING));

        add(horizontallyPaddedPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void appear(boolean daily) {
        if (daily) {

        }
        try {
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
