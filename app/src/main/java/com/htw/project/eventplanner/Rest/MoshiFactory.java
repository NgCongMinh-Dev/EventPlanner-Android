package com.htw.project.eventplanner.Rest;

import com.htw.project.eventplanner.Utils.DateTimeConverter;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public final class MoshiFactory {

    public static Moshi create() {
        Moshi moshi = new Moshi.Builder()
                .add(getDateTimeAdapter())
                .build();
        return moshi;
    }

    private static Object getDateTimeAdapter(){
        Object customDateAdapter = new Object() {
            final DateFormat dateFormat = DateTimeConverter.FORMATTER;

            @ToJson
            synchronized String dateToJson(Date d) {
                return dateFormat.format(d);
            }

            @FromJson
            synchronized Date dateToJson(String s) throws ParseException {
                return dateFormat.parse(s);
            }
        };

        return  customDateAdapter;
    }

}
