package com.exam.management.exammanagementsystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class DateUtils {
    private DateUtils() {

    }

    public static Date getExpirationTime(Long expireHours) {
        Date now = new Date();
        Long expireInMillis = TimeUnit.HOURS.toMillis(expireHours);
        return new Date(expireInMillis + now.getTime());
    }

    public static String getStringDate(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
