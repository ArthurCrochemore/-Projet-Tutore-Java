package fr.univtours.polytech.punchingcommon.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Common functions used by PunchingMachine and PunchingManagement.
 * TimeUtils can format the date/time and round the time to 15 minutes.
 */
public class TimeUtils {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter TIME_FORMATTER_WHITH_SECONDS = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            .withLocale(java.util.Locale.ENGLISH);

    /**
     * Class not instanciable
     */
    private TimeUtils() {
    }

    /**
     * Convert a String to a LocalTime.
     * The String should be in the format HH:mm (i.e. "14:58").
     * If the String doesn't respect this format, we try to return a time (i.e. "1458" works)
     * If the String contains other characters than \d or :, they are removed
     * 
     * @param string A String containing a time in the
     * @return LocalTime or null if the LocalTime couldn't be guessed
     */
    public static LocalTime stringToLocalTime(String string) {
        if (string == null || string.isEmpty() || string.startsWith("nul")) {
            return null;
        }

        String digits = string.replaceAll("[^\\d:]", "");
        if (digits.length() < 4) {
            digits = "0" + digits;
        }

        String[] tokens = digits.split(":");
        int hours = 0;
        int minutes = 0;
        if (tokens.length == 1) {
            hours = Integer.parseInt(digits.substring(0, Math.min(2, digits.length())));
            if (digits.length() > 2)
                minutes = Integer.parseInt(digits.substring(2, Math.min(4, digits.length())));
        } else if (tokens.length >= 2) {
            hours = parseIntOrElse(tokens[0], 0);
            minutes = parseIntOrElse(tokens[1], 0);
        } else {
            return null;
        }
        return LocalTime.of(hours % 24, minutes % 60);
    }

    /**
     * Convert a localtime to a string, with the format HH:mm
     * 
     * @param time the localTime to convert
     * @return the String that corresponds to the time
     */
    public static String localTimeToString(LocalTime time) {
        if (time == null)
            return null;
        return time.format(TIME_FORMATTER);
    }

    /**
     * Convert a localtime to a string, with the format HH:mm:ss
     * 
     * @param time the localTime to convert
     * @return the String that corresponds to the time
     */
    public static String localTimeToStringWithSeconds(LocalTime time) {
        if (time == null)
            return null;
        return time.format(TIME_FORMATTER_WHITH_SECONDS);
    }

    /**
     * Convert a localDate to a string
     * 
     * @param date the localDate to convert
     * @return the String that corresponds to the date
     */
    public static String localDateToString(LocalDate date) {
        if (date == null)
            return null;
        return date.format(DATE_FORMATTER);
    }

    /**
     * Extract an int from a string safely.
     * if token is a number, we return parseInt(token)
     * Else, defaultValue is returned
     * 
     * @param token        The string which can contain a number
     * @param defaultValue The value returned if parseInt failed
     * @return The int in token or the default value
     */
    private static int parseIntOrElse(String token, int defaultValue) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * method that returns the current time rounded to the nearest 15 minutes
     * 
     * @return the current time rounded to the nearest 15 minutes
     */
    public static LocalTime getCurrentRoundedLocalTime() {
        return roundTo15Minutes(LocalTime.now());
    }

    /**
     * Round a time to the nearest 15 minutes.
     * 
     * - If the time is 14:58, we return 15:00
     * - If the time is after 23:45, the result is 23:45.
     * - If the time is 0:00, the result is 0:00.
     * 
     * @param time The time to round
     * @return The time rounded to 15 minutes
     */
    public static LocalTime roundTo15Minutes(LocalTime time) {
        if (time == null)
            return null;
        
        // Keep only the hour and minutes
        time = LocalTime.of(time.getHour(), time.getMinute());

        int minutes = time.getMinute();

        // If after 23:45, limit to 23:45
        if (time.getHour() == 23 && minutes > 45)
            return LocalTime.of(23, 45);

        // Round to the nearest 15 minutes
        int minutesSupp = minutes % 15;
        if (minutesSupp > 7) {
            time = time.plusMinutes(15L - minutesSupp);
        } else {
            time = time.minusMinutes(minutesSupp);
        }
        return time;
    }

    /**
     * Get the current LocalDateTime and round the time to the nearest 15 minutes.
     * 
     * @return LocalDateTime.now() rounded to the nearest 15 minutes
     */
    public static LocalDateTime getCurrentRoundedLocalDateTime() {
        // Rounding never round to the next day (0:01 -> 0:00 and 23:59 -> 23:45)
        // The LocalDate doesn't change
        return LocalDateTime.of(LocalDate.now(), getCurrentRoundedLocalTime());
    }

    /**
     * Format a LocalTime to the format HH:mm
     * 
     * @param time time to format
     * @return The formated time
     */
    public static String format(LocalTime time) {
        if (time == null)
            return "null";
        return time.format(TIME_FORMATTER);
    }

    /**
     * Format a LocalDate to the English format.
     * Example: "Monday, January 2, 2023"
     * 
     * @param date date to format
     * @return The formated date
     */
    public static String format(LocalDate date) {
        if (date == null)
            return "null";
        return date.format(DATE_FORMATTER);
    }

    /**
     * Calculate the number of minutes since 0:00
     * 
     * @param time time to use
     * @return number of minutes between 0:00 and the given time
     */
    public static int convertLocalTimeToMinute(LocalTime time) {
        if (time == null)
            return 0;
        return time.getMinute() + time.getHour() * 60;
    }

    /**
     * Calculate the number of minutes between two LocalTime.
     * If any LocalTime is null, 0 is returned.
     * 
     * @param entry The first time
     * @param exit  The second time
     * @return Number of seconds between entry and exit
     */
    public static int workingTime(LocalTime entry, LocalTime exit) {
        if (entry == null || exit == null)
            return 0;
        return convertLocalTimeToMinute(exit) - convertLocalTimeToMinute(entry);
    }
}
