package com.programmersdiary.meetingmanager.testutils;

import java.util.Calendar;
import java.util.Date;

public class TestUtils {

    public static Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

}
