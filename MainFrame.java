import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Testing...");
    }
    
    public void createAndShow() {
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
            // Add horizontal glue before the label
            centeredPanel.add(Box.createHorizontalGlue());
            centeredPanel.add(new JLabel("Hello, world!"));
            // Add horizontal glue after the label
            centeredPanel.add(Box.createHorizontalGlue());
            // The glues cause the label to be centered horizontally 
            // within the window
        mainPanel.add(centeredPanel);

        centeredPanel = Box.createHorizontalBox();
            centeredPanel.add(Box.createHorizontalGlue());
            AnimatedPanel ap = new AnimatedPanel();
            centeredPanel.add(ap);
            centeredPanel.add(Box.createHorizontalGlue());
        mainPanel.add(centeredPanel);
        mainPanel.add(Box.createVerticalGlue());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        new Thread(ap).start();
        setVisible(true);
    }

}
