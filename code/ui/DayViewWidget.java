package code.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import code.BackgroundDaemon;
import code.schedule.DateTimeFormatter;
import code.schedule.ScheduledEvent;
import code.ui.fonts.FontManager;

public class DayViewWidget extends JComponent implements MouseListener, MouseMotionListener {

    private static final int PIXELS_PER_HOUR = 128;
    private static final int PIXELS_PER_15 = PIXELS_PER_HOUR >> 2;

    private int cellHovered;

    private int cellDragged;

    private int hoverY;

    private boolean dragging;
    private boolean pressing;

    private LocalDate date;

    private List<ScheduledEvent> events;
    private ScheduledEvent editCancelTarget;

    private List<YRange> hitboxes;

    private JPopupMenu rightClickMenu;

    public DayViewWidget() {
        setPreferredSize(new Dimension(450, PIXELS_PER_HOUR * 24));
        cellHovered = -1;
        cellDragged = -1;
        hitboxes = new ArrayList<>();
        rightClickMenu = new JPopupMenu();
        JMenuItem item;
        item = new JMenuItem("Edit");
        item.addActionListener(this::editOptionChosen);
        rightClickMenu.add(item);
        item = new JMenuItem("Cancel");
        item.addActionListener(this::cancelOptionChosen);
        rightClickMenu.add(item);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void editOptionChosen(ActionEvent e) {
        BackgroundDaemon.getInstance().getAddEventFrame().appearForEdit(this, editCancelTarget);
    }

    private void cancelOptionChosen(ActionEvent e) {
        BackgroundDaemon.getInstance().cancel(date, editCancelTarget);
    }

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color HOUR_LINE_COLOR = new Color(160, 160, 160);
    private static final Color FIFTEEN_MIN_LINE_COLOR = new Color(224, 224, 224);
    private static final Color TIME_HOUR_COLOR = new Color(32, 32, 32);
    private static final Color TIME_FIFTEEN_MIN_COLOR = new Color(64, 64, 64);
    private static final Color HOVERED_CELL_BACKGROUND_COLOR = new Color(209, 71, 82);
    private static final Color HOVERED_CELL_FOREGROUND_COLOR = Color.WHITE;
    
    private static final Color BG_COLOR_EVENT = new Color(248, 255, 224);
    private static final Color BG_COLOR_EVENT_HOVER = new Color(173, 217, 22);
    private static final Color FG_COLOR_EVENT = Color.BLACK;

    private static final Font EVENT_FONT = FontManager.getInstance().getBoldFont();
    private static final Font EVENT_LOCATION_FONT = FontManager.getInstance().getLightFont();
    @Override
    public void paint(Graphics g) {
        FontManager.enableFontAntiAliasing(g);
        int width = getWidth();
        int height = getHeight();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, width, height);

        if (cellHovered >= 0) {
            g.setColor(HOVERED_CELL_BACKGROUND_COLOR);
            if (cellDragged < 0) {
                g.fillRect(0, cellHovered * PIXELS_PER_15, width, PIXELS_PER_15);
            } else {
                g.fillRect(0, cellHovered * PIXELS_PER_15, width, PIXELS_PER_15 * (cellDragged - cellHovered + 1));
            }
        }

        if (events != null) {
            for (ScheduledEvent e : events) {

                LocalTime start = e.getStartTime();
                LocalTime end = e.getEndTime();
                int topY = start.getHour() * PIXELS_PER_HOUR;
                topY += (int)(start.getMinute() / 60.0 * PIXELS_PER_HOUR);

                int bottomY = end.getHour() * PIXELS_PER_HOUR;
                bottomY += (int)(end.getMinute() / 60.0 * PIXELS_PER_HOUR);

                if (hoverY >= topY && hoverY <= bottomY) {
                    g.setColor(BG_COLOR_EVENT_HOVER);
                } else {
                    g.setColor(BG_COLOR_EVENT);
                }
                g.fillRect(0, topY, width, bottomY - topY);
                g.setColor(FG_COLOR_EVENT);
                g.setFont(EVENT_FONT);
                String name = e.getName();
                int stringWidth = (int)g.getFontMetrics().getStringBounds(name + " ", g).getWidth();
                g.drawString(e.getName(), 80, topY + 21);
                String location = e.getLocation();
                if (!location.isBlank()) {
                    g.setFont(EVENT_LOCATION_FONT);
                    g.drawString("@ " + e.getLocation(), 80 + stringWidth, topY + 21);
                }
            }
        }
    
        int y = 0;
        g.setFont(FontManager.getInstance().getMonospaceFont());
        int cell = 0;
        for (int hour = 0; hour < 24; hour++) {
            if ((cellDragged < 0 && cell == cellHovered) 
                    || (cellDragged >= 0 && cell >= cellHovered && cell <= cellDragged)) {
                g.setColor(HOVERED_CELL_FOREGROUND_COLOR);
            } else {
                g.setColor(TIME_HOUR_COLOR);                
            }
            g.drawString(String.format("%7s", DateTimeFormatter.toAmPm(hour, 0)), 6, y+22);
            cell++;
            for (int quarter = 1; quarter < 4; quarter++) {
                if ((cellDragged < 0 && cell == cellHovered) 
                        || (cellDragged >= 0 && cell >= cellHovered && cell <= cellDragged)) {
                    g.setColor(HOVERED_CELL_FOREGROUND_COLOR);
                } else {
                    g.setColor(TIME_FIFTEEN_MIN_COLOR);
                }
                g.drawString(String.format("%7s", DateTimeFormatter.toAmPm(hour, (quarter * 15))),
                        6, y + quarter * PIXELS_PER_15 + 22);
                g.setColor(FIFTEEN_MIN_LINE_COLOR);
                g.drawLine(0, y + (quarter) * PIXELS_PER_15, width - 1, y + (quarter) * PIXELS_PER_15);
                cell++;
            }
            y += PIXELS_PER_HOUR;
            if (hour < 23) {
                g.setColor(HOUR_LINE_COLOR);
                g.drawLine(0, y, width - 1, y);
            }
        }
    }

    public void updateDay(LocalDate d, List<ScheduledEvent> events) {
        date = d;
        this.events = events;
        hitboxes.clear();
        if (events != null) {
            for (ScheduledEvent e : events) {
                LocalTime start = e.getStartTime();
                LocalTime end = e.getEndTime();
                int topY = start.getHour() * PIXELS_PER_HOUR;
                topY += (int)(start.getMinute() / 60.0 * PIXELS_PER_HOUR);

                int bottomY = end.getHour() * PIXELS_PER_HOUR;
                bottomY += (int)(end.getMinute() / 60.0 * PIXELS_PER_HOUR);
                YRange range = new YRange();
                range.top = topY;
                range.bottom = bottomY;
                range.event = e;
                hitboxes.add(range);
            }
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressing) {
            pressing = false;
            dragging = true;
        }
        if (!dragging/* || e.getButton() != MouseEvent.BUTTON1*/) {
            return;
        }
        int draggedCell = e.getY() / PIXELS_PER_15;
        if (draggedCell >= cellHovered) {
            boolean needToPaint = draggedCell != cellDragged;
            cellDragged = draggedCell;
            if (needToPaint) {
                repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int newHover = e.getY() / PIXELS_PER_15;
        if (newHover != cellHovered) {
            cellHovered = e.getY() / PIXELS_PER_15;
            repaint();
        }
        hoverY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pressing = false;
            if (e.getClickCount() == 2) {
                int y = e.getY();
                for (YRange r : hitboxes) {
                    if (r.contains(y)) {
                        editCancelTarget = r.event;
                        editOptionChosen(null);
                        return;
                    }
                }
                int slot = y / PIXELS_PER_15;
                int hour = slot / 4;
                int minute = slot % 4 * 15;
                int hourEnd = hour + 1;
                int minuteEnd = minute;
                if (hourEnd == 24) {
                    hourEnd = 23;
                    minuteEnd = 59;
                }
                BackgroundDaemon.getInstance().getAddEventFrame().appear(null,
                        date, LocalTime.of(hour, minute), LocalTime.of(hourEnd, minuteEnd));
                repaint();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pressing = true;
            cellHovered = e.getY() / PIXELS_PER_15;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // ???
        if (dragging && e.getButton() == MouseEvent.BUTTON1) {
            dragging = false;
            int startSlot = cellHovered;
            int endSlot = e.getY() / PIXELS_PER_15;
            if (startSlot > endSlot) {
                cellDragged = -1;
                cellHovered = -1;
                repaint();
                return;
            }
            int hourStart = startSlot / 4;
            int minuteStart = startSlot % 4 * 15;
            int hourEnd = endSlot / 4;
            int minuteEnd = endSlot % 4 * 15 + 15;
            if (minuteEnd == 60) {
                minuteEnd = 0;
                hourEnd++;
                if (hourEnd == 24) {
                    hourEnd = 23;
                    minuteEnd = 59;
                }
            }
            BackgroundDaemon.getInstance().getAddEventFrame().appear(null,
                date, LocalTime.of(hourStart, minuteStart), LocalTime.of(hourEnd, minuteEnd));
            cellDragged = -1;
            cellHovered = -1;
            repaint();
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            int y = e.getY();
            for (YRange r : hitboxes) {
                if (r.contains(y)) {
                    editCancelTarget = r.event;
                    rightClickMenu.show(this, e.getX(), y);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        cellHovered = -1;
        cellDragged = -1;
        dragging = false;
        hoverY = -1;
        repaint();
    }
    
}
