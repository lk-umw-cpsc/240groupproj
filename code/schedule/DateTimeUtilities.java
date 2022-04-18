package code.schedule;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains useful static methods for formatting
 * and parsing date input and output.
 */
public class DateTimeUtilities {

    private static final String[] DAYS_OF_THE_WEEK = {
        null, "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday"
    };

    /**
     * Formats a LocalDateTime into an English String
     * @param dt The date time to format
     * @return a String containing formatted text
     */
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

    /**
     * Gets the name of the day of the week the given LocalDate
     * falls on
     * @param d The date
     * @return A String containing Sunday, Monday, etc.
     */
    public static String getDayOfWeek(LocalDate d) {
        return DAYS_OF_THE_WEEK[d.getDayOfWeek().getValue()];
    }

    /**
     * Formats a given LocalDate into the format "<day of week>, mm/dd/yyyy"
     * @param d The date to format
     * @return A String with the format described above
     */
    public static String format(LocalDate d) {
        return String.format("%s, %d/%d/%d", DAYS_OF_THE_WEEK[d.getDayOfWeek().getValue()],
            d.getMonthValue(), d.getDayOfMonth(), d.getYear());
    }

    /**
     * Converts a LocalDateTime to a String containing its 12-hour clock time w/ AM/PM
     * @param dt The date time to convert
     * @return A resulting string in the format HH:MMAM/PM
     */
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

    /**
     * Converts a given LocalTime to a String containing the 12-hour time
     * of the time given (with AM/PM)
     * @param t The time to convert
     * @return A formatted String
     */
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

    /**
     * Formats a given time into hh:mmAM/PM format
     * @param hour The hours (0-23)
     * @param minute The minutes (0-59)
     * @return A formatted String
     */
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

    /**
     * Converts a LocalDateTime's date to a "slash string" of format mm/dd/yyyy
     * @param dt The datetime to convert
     * @return a String containing the formatted text
     */
    public static String toSlashString(LocalDateTime dt) {
        return dt.getMonthValue() + "/" + dt.getDayOfMonth() + "/" + dt.getYear();
    }

    /**
     * Convets a LocalDate's date to a "slash string" of format mm/dd/yyyy
     * @param d The date to convert
     * @return a String containing the formatted text
     */
    public static String toSlashString(LocalDate d) {
        return d.getMonthValue() + "/" + d.getDayOfMonth() + "/" + d.getYear();
    }

    private static final Pattern datePattern = Pattern.compile("^([1-9]|1[0-2])/([1-9]|[12]\\d|3[01])/(\\d\\d\\d\\d)$");
    
    /**
     * Turns a "slash string" into a LocalDate
     * @param d The string to convert
     * @return a new LocalDate with the given date, or null if conversion failed
     */
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

    /**
     * Parses a given time represented by a String into a LocalTime object
     * @param t the String to parse
     * @return a new LocalTime object on success, otherwise null
     */
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
