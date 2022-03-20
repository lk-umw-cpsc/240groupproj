package code.ui;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

import code.BackgroundDaemon;

public class AddReminderFrame extends JFrame {

    private BackgroundDaemon daemon;
        
    public AddReminderFrame(BackgroundDaemon daemon) {
        super("Create new reminder");
        this.daemon = daemon;
    }
    
    public void build() {
        // Create a vertical box, which will display its children
        // from top to bottom in the order they're added
        Box mainPanel = Box.createVerticalBox();
        // mainPanel.setPreferredSize(new Dimension(200, 100));
        add(mainPanel);
        // Add vertical glue, which resizes as the window is resized
        // This glue, plus another glue after our content, will cause the
        // content to be centered vertically within the window.
        mainPanel.add(Box.createVerticalGlue());
        // Create another box whose children are displayed left to right
        Box centeredPanel = Box.createHorizontalBox();

            centeredPanel.add(new JLabel("I'm left-aligned"));
            // Add horizontal glue after the label, left-aligning the label
            centeredPanel.add(Box.createHorizontalGlue());
        mainPanel.add(centeredPanel);

        centeredPanel = Box.createHorizontalBox();
            centeredPanel.add(Box.createHorizontalGlue());
            // add something here...
            centeredPanel.add(new JLabel("I'm centered"));
            centeredPanel.add(Box.createHorizontalGlue());
        mainPanel.add(centeredPanel);
        mainPanel.add(Box.createVerticalGlue());

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
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
