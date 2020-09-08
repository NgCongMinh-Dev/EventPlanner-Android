package com.htw.project.eventplanner.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTimeConverter {

    public static final String DATE_FORMAT = "dd.MM.yyyy";

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT, Locale.GERMAN);

    private DateTimeConverter() {
    }

    public static Date getDate(String value) {
        try {
            return FORMATTER.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDate(Date date) {
        return FORMATTER.format(date);
    }

}
