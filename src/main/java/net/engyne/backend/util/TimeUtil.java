package net.engyne.backend.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Long timestamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static Calendar calendar(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        return calendar;
    }
}
