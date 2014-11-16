package com.infodeliver.webservice.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getSystemDate() {
        return new Date();
    }

    public static Calendar getSystemCalendar() {
        return Calendar.getInstance();
    }

    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(getSystemDate());
    }
}
