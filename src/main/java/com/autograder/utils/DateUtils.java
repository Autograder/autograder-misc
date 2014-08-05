package com.autograder.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final DateFormat dateFormat = new SimpleDateFormat(PATTERN);

    public static String format(Date date) {
        return dateFormat.format(date);
    }

    public static Date parse(String source) throws ParseException {
        return dateFormat.parse(source);
    }

    public static Calendar now() {
        return GregorianCalendar.getInstance();
    }

    public static String nowAsString() {
        return format(now().getTime());
    }
}
