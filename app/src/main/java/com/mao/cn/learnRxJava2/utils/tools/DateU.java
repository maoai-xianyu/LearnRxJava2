package com.mao.cn.learnRxJava2.utils.tools;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateU {
    public static ArrayList<String> getMonthOfDay(String year, String month, ArrayList<String> list) {
        ArrayList<String> allDay = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(list.get(i).substring(0, 4), year)) {
                if (TextUtils.equals(list.get(i).substring(5, 7), month)) {
                    allDay.add(list.get(i).substring(8, 10));
                }
            }
        }

        return allDay;
    }

    public static String getTimeStamp(String data) {
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(data);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() + "";
    }

    public static String getScheduleDay3(String tempDay) {
        String month = tempDay.substring(5, 7);
        String day = tempDay.substring(8, 10);
        return month + "月" + day + "日";
    }

    public static String getScheduleDay(String tempDay) {
        String month = tempDay.substring(5, 7);
        String day = tempDay.substring(8, 10);
        String week = getWeekByDateStr(tempDay);
        return month + "月" + day + "日 " + week;
    }

    public static String getScheduleDay2(String tempDay) {
        String month = tempDay.substring(5, 7);
        String day = tempDay.substring(8, 10);
        String week = getWeekByDateStr(tempDay);
        return month + "月" + day + "日(" + week + ")";
    }

    public static String getWeekByDateStr(String strDate) {
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(5, 7));
        int day = Integer.parseInt(strDate.substring(8, 10));

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);

        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);

        switch (weekIndex) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    public static String getVideoDuration(int ms) {
        int duration = ms / 1000;
        int hours = duration / 3600;
        int minutes = (duration / 60) - (hours * 60);
        int seconds = duration - (hours * 3600) - (minutes * 60);
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public static String getHourAndMin(String tempDay) {
        return tempDay.substring(11, 16);
    }

    public static String getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        String displayName = timeZone.getDisplayName(false, TimeZone.SHORT);
        return displayName;
    }

    public static boolean isInChina() {
        if (getTimeZone().equals("GMT+08:00")) {
            return true;
        }
        return false;
    }

    /**
     * 是否超过了下午七点，如果是，那么就只判断是否完成自主学习的任务
     */
    public static boolean aboveSevenOclock() {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        Date oldDate = null;
        try {
            oldDate = sf.parse(sf.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldDate.getHours() > 18;
    }
}
