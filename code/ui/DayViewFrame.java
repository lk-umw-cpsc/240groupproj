package code.ui;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import code.BackgroundDaemon;
import code.schedule.DateTimeUtilities;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class DayViewFrame extends JFrame {

    private JLabel dayLabel;
    private DayViewWidget widget;
    private LocalDate viewedDate;

    private final BackgroundDaemon daemon;
    
    public DayViewFrame(BackgroundDaemon daemon) {
        super("Day View");
        this.daemon = daemon;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        Box dayContainer = Box.createHorizontalBox();
        add(dayContainer);
        dayContainer.add(Box.createHorizontalGlue());
        dayContainer.add(dayLabel = new JLabel("placeholder"));
        dayLabel.setBorder(new EmptyBorder(4, 0, 4, 0));
        dayLabel.setFont(FontManager.getInstance().getRegularFont().deriveFont(24.0f));
        dayContainer.add(Box.createHorizontalGlue());

        JScrollPane scrollPane = new JScrollPane(widget = new DayViewWidget());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(UIConstants.JSCROLLPANE_VERTICAL_SCROLLBAR_INCREMENT);
        scrollPane.setPreferredSize(new Dimension(450 + (int)scrollPane.getVerticalScrollBar().getPreferredSize().getWidth(), 512));
        add(scrollPane);

        pack();
    }

    public void appear(LocalDate d, List<ScheduledEvent> events) {
        viewedDate = d;
        widget.updateDay(d, events);
        dayLabel.setText(DateTimeUtilities.format(d));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(LocalDate d, List<ScheduledEvent> events) {
        if (d.equals(viewedDate)) {
            widget.updateDay(d, events);
        }
    }

}
