package code.ui;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import code.BackgroundDaemon;
import code.schedule.DateTimeFormatter;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class DayViewFrame extends JFrame {

    private JLabel dayLabel;
    private DayViewWidget widget;

    private final BackgroundDaemon daemon;
    
    public DayViewFrame(BackgroundDaemon daemon) {
        super("Day View");
        this.daemon = daemon;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        Box dayContainer = Box.createHorizontalBox();
        add(dayContainer);
        dayContainer.add(Box.createHorizontalGlue());
        dayContainer.add(dayLabel = new JLabel("placeholder"));
        dayLabel.setFont(FontManager.getInstance().getRegularFont());
        dayContainer.add(Box.createHorizontalGlue());

        JScrollPane scrollPane = new JScrollPane(widget = new DayViewWidget());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(UIConstants.JSCROLLPANE_VERTICAL_SCROLLBAR_INCREMENT);
        scrollPane.setPreferredSize(new Dimension(400 + scrollPane.getVerticalScrollBar().getWidth(), 512));
        add(scrollPane);

        pack();
    }

    public void appear(LocalDate d, List<ScheduledEvent> events) {
        // load events into widget?
        widget.updateDay(d, events);
        dayLabel.setText(DateTimeFormatter.format(d));
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
