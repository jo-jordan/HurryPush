package com.lzjlxebr.hurrypush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterUtils {

    public static Long normalizeDateFromString(String dateStr) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateStringFromDateLong(Long dateLong) {
        Date date = new Date(dateLong);
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.CHINA).format(date);
    }
}
