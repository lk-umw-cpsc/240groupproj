import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Testing...");
    }
    
    public void createAndShow() {
        Box mainPanel = Box.createVerticalBox();
        // mainPanel.setPreferredSize(new Dimension(200, 100));
        add(mainPanel);

        mainPanel.add(Box.createVerticalGlue());
        Box centeredPanel = Box.createHorizontalBox();
            centeredPanel.add(Box.createHorizontalGlue());
            centeredPanel.add(new JLabel("Hello, world!"));
            centeredPanel.add(Box.createHorizontalGlue());
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
