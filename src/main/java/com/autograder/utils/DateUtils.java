package com.autograder.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String READABLE_FORMAT = "EEE MM/dd/yy hh:mm a z";
    private static final DateFormat dateIso6801Format = new SimpleDateFormat(ISO8601_FORMAT);
    private static final DateFormat dateReadableFormat = new SimpleDateFormat(READABLE_FORMAT);

    public static String formatIso6801(Date date) {
        return dateIso6801Format.format(date);
    }

    public static String formatReadable(Date date) {
        return dateReadableFormat.format(date);
    }

    public static Date parseIso6801(String source) throws ParseException {
        return dateIso6801Format.parse(source);
    }

    public static Calendar now() {
        return GregorianCalendar.getInstance();
    }

    public static String nowIso6801() {
        return formatIso6801(now().getTime());
    }

    public static String nowReadable() {
        return formatReadable(now().getTime());
    }

    public static String relativize(Date date) {
        return relativize(date, now().getTime());
    }

    /**
     * {@code date1} has to be earlier than {@code date2}.
     */
    public static String relativize(Date date1, Date date2) {
        assert date2.getTime() >= date1.getTime();

        long duration = date2.getTime() - date1.getTime();
        long converted;

        if ((converted = TimeUnit.MILLISECONDS.toDays(duration)) > 0) {
            return String.format("%d %s ago", converted, converted == 1 ? "day" : "days");
        } else if ((converted = TimeUnit.MILLISECONDS.toHours(duration)) > 0) {
            return String.format("%d %s ago", converted, converted == 1 ? "hour" : "hours");
        } else if ((converted = TimeUnit.MILLISECONDS.toMinutes(duration)) > 0) {
            return String.format("%d %s ago", converted, converted == 1 ? "minute" : "minutes");
        } else if ((converted = TimeUnit.MILLISECONDS.toSeconds(duration)) > 0) {
            return String.format("%d %s ago", converted, converted == 1 ? "second" : "seconds");
        } else {
            return "just now";
        }
    }
}
