package code.schedule;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtilities {

    private static final String[] DAYS_OF_THE_WEEK = {
        null, "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday"
    };

    public static String format(LocalDateTime dt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        String day = "";

        int todayDay = now.getDayOfMonth();
        int todayMonth = now.getMonthValue();
        int todayYear = now.getYear();

        LocalDateTime weekFromNow = LocalDateTime.of(
            todayYear, todayMonth, todayDay, 0, 0).plusWeeks(1);

        int tomDay = tomorrow.getDayOfMonth();
        int tomMonth = tomorrow.getMonthValue();
        int tomYear = tomorrow.getYear();

        int sDay = dt.getDayOfMonth();
        int sMonth = dt.getMonthValue();
        int sYear = dt.getYear();

        if (todayDay == sDay && todayMonth == sMonth && todayYear == sYear) {
            day = "Today";
        } else if (tomDay == sDay && tomMonth == sMonth && tomYear == sYear) {
            day = "Tomorrow";
        } else if (dt.isBefore(weekFromNow)) {
            day = "This " + DAYS_OF_THE_WEEK[dt.getDayOfWeek().getValue()];
        } else {
            day = DAYS_OF_THE_WEEK[dt.getDayOfWeek().getValue()];
            day += ", " + sMonth + "/" + sDay + "/" + sYear;
        }

        
        return day + " at " + toAmPm(dt);
    }

    public static String getDayOfWeek(LocalDate d) {
        return DAYS_OF_THE_WEEK[d.getDayOfWeek().getValue()];
    }

    public static String format(LocalDate d) {
        return String.format("%s, %d/%d/%d", DAYS_OF_THE_WEEK[d.getDayOfWeek().getValue()],
            d.getMonthValue(), d.getDayOfMonth(), d.getYear());
    }

    public static String toAmPm(LocalDateTime dt) {
        String time = String.format(":%02d", dt.getMinute());
        int hour = dt.getHour();
        if (hour >= 12) {
            if (hour > 12) {
                hour = hour % 12;
            }
            time = hour + time + "PM";
        } else {
            if (hour == 0) {
                hour = 12;
            }
            time = hour + time + "AM";
        }
        return time;
    }

    public static String toAmPm(LocalTime t) {
        String time = String.format(":%02d", t.getMinute());
        int hour = t.getHour();
        if (hour >= 12) {
            if (hour > 12) {
                hour = hour % 12;
            }
            time = hour + time + "PM";
        } else {
            if (hour == 0) {
                hour = 12;
            }
            time = hour + time + "AM";
        }
        return time;
    }

    public static String toAmPm(int hour, int minute) {
        String time = String.format(":%02d", minute);
        if (hour >= 12) {
            if (hour > 12) {
                hour = hour % 12;
            }
            time = hour + time + "PM";
        } else {
            if (hour == 0) {
                hour = 12;
            }
            time = hour + time + "AM";
        }
        return time;
    }

    public static String toSlashString(LocalDateTime dt) {
        return dt.getMonthValue() + "/" + dt.getDayOfMonth() + "/" + dt.getYear();
    }

    public static String toSlashString(LocalDate d) {
        return d.getMonthValue() + "/" + d.getDayOfMonth() + "/" + d.getYear();
    }

    private static final Pattern datePattern = Pattern.compile("^([1-9]|1[0-2])/([1-9]|[12]\\d|3[01])/(\\d\\d\\d\\d)$");
    
    public static LocalDate parseDate(String d) {
        Matcher m = datePattern.matcher(d);
        if (!m.matches()) {
            return null;
        }
        int year = Integer.parseInt(m.group(3));
        int month = Integer.parseInt(m.group(1));
        int day = Integer.parseInt(m.group(2));
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return null;
        }
    }
    
    private static final Pattern timePattern = Pattern.compile("^(1[012]|[1-9]):([0-5]\\d)(AM|am|PM|pm)$");

    public static LocalTime parseTime(String t) {
        Matcher m = timePattern.matcher(t);
        if (!m.matches()) {
            return null;
        }
        int hour = Integer.parseInt(m.group(1));
        int minute = Integer.parseInt(m.group(2));
        boolean pm = m.group(3).equalsIgnoreCase("pm");
        if (pm) {
            if (hour < 12) {
                hour += 12;
            }
        } else if (hour == 12) {
            hour = 0;
        }
        try {
            return LocalTime.of(hour, minute, 0);
        } catch (DateTimeException e) {
            return null;
        }
    }
}
