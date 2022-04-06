package code.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import code.schedule.DateTimeUtilities;
import code.schedule.ScheduledReminder;
import code.ui.fonts.FontManager;

public class ReminderUIEntry implements MouseListener {

    private static ImageIcon xIcon;
    private static ImageIcon editIcon;
    static {
        try {
            BufferedImage x = ImageIO.read(new File("Images/x.png"));
            xIcon = new ImageIcon(x);
            editIcon = new ImageIcon(ImageIO.read(new File("Images/edit.png")));
        } catch(IOException e) {}
    }

    private ScheduledReminder reminder;
    private ReminderManagerFrame parent;
    private JPanel container;

    private JLabel xButton;
    private JLabel editButton;

    public ReminderUIEntry(ReminderManagerFrame parent, ScheduledReminder r) {
        this.parent = parent;
        reminder = r;
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        container.add(Box.createHorizontalStrut(8));
        Box xButtonBox = Box.createVerticalBox();

        xButton = new JLabel(xIcon);
        xButton.addMouseListener(this);
        xButtonBox.add(Box.createVerticalStrut(24));
        xButtonBox.add(xButton);
        xButtonBox.add(Box.createVerticalStrut(24));
        xButtonBox.setMaximumSize(xButtonBox.getPreferredSize());
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, xButton.getHeight()));
        container.add(xButtonBox);

        container.add(Box.createHorizontalStrut(8));

        Box editButtonBox = Box.createVerticalBox();
        editButtonBox.add(Box.createVerticalGlue());
        editButton = new JLabel(editIcon);
        editButton.addMouseListener(this);
        editButtonBox.add(editButton);
        editButtonBox.add(Box.createVerticalGlue());

        container.add(editButtonBox);

        container.add(Box.createHorizontalStrut(16));

        Box infoBox = Box.createVerticalBox();

        JLabel nameLabel = new JLabel(r.getName());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        infoBox.add(nameLabel);

        FontManager fonts = FontManager.getInstance();
        Font regular = fonts.getRegularFont();
        Font light = fonts.getLightFont();
        Font bold = fonts.getBoldFont();

        nameLabel.setFont(bold);
        String desc = r.getDescription();
        if (!desc.isBlank()) {
            infoBox.add(Box.createVerticalStrut(8));
            JLabel descLabel = new JLabel(desc);
            descLabel.setFont(light);
            infoBox.add(descLabel);
        }

        infoBox.setMaximumSize(infoBox.getPreferredSize());
        container.add(infoBox);

        container.add(Box.createHorizontalGlue());

        Box timeDateBox = Box.createVerticalBox();
        Box layer = Box.createHorizontalBox();
        layer.add(Box.createHorizontalGlue());
        JLabel dayLabel = new JLabel(DateTimeUtilities.format(r.getWhenDue()));
        dayLabel.setFont(regular);
        layer.add(dayLabel);
        timeDateBox.add(layer);
        if (r.repeats()) {
            String dayOrDays = "day";
            int days = r.getDaysBetweenRepetitions();
            if (days > 1) {
                dayOrDays = " " + days + " days";
            }
            timeDateBox.add(Box.createVerticalStrut(8));
            layer = Box.createHorizontalBox();
            layer.add(Box.createHorizontalGlue());
            JLabel repeatLabel = new JLabel("Repeats every" + dayOrDays);
            repeatLabel.setFont(light);
            layer.add(repeatLabel);
            timeDateBox.add(layer);
        }

        container.add(timeDateBox);

        container.add(Box.createHorizontalStrut(8));

        container.setBorder(new MatteBorder(0, 0, 1, 0, new Color(175, 175, 175)));
        container.setOpaque(true);
        container.setBackground(Color.WHITE);
    }

    public ScheduledReminder getReminder() {
        return reminder;
    }

    public Component getContainer() {
        return container;
    }

    private void cancel() {
        parent.cancelReminder(reminder);
    }

    private void edit() {
        parent.editReminder(reminder);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == xButton) {
            cancel();
        } else if (e.getSource() == editButton) {
            edit();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}