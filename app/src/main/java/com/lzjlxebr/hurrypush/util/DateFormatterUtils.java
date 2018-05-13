package com.lzjlxebr.hurrypush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterUtils {

    public static Long normalizeDateFromString(String dateStr) {

        if ("0000-00-00 00:00:00".equals(dateStr) || "0000-00-00 00:00:00.000".equals(dateStr)) {
            return 0L;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeDiff = 8 * 60 * 60 * 1000;
        long dateInLong = date.getTime();

        return dateInLong - timeDiff;
    }


    public static int getYearFromLong(long time) {
        String date = SimpleDateFormat.getDateInstance(SimpleDateFormat.MONTH_FIELD, Locale.SIMPLIFIED_CHINESE).format(new Date(time));
        String year = date.substring(0, date.indexOf("年"));

        return Integer.parseInt(year);
    }

    public static int getMonthFromLong(long time) {
        String date = SimpleDateFormat.getDateInstance(SimpleDateFormat.MONTH_FIELD, Locale.SIMPLIFIED_CHINESE).format(new Date(time));
        String month = date.substring(date.indexOf("年") + 1, date.indexOf("月"));

        return Integer.parseInt(month);
    }

    public static int getDayFromLong(long time) {
        String date = SimpleDateFormat.getDateInstance(SimpleDateFormat.MONTH_FIELD, Locale.SIMPLIFIED_CHINESE).format(new Date(time));
        String day = date.substring(date.indexOf("月") + 1, date.indexOf("日"));

        return Integer.parseInt(day);
    }
}
