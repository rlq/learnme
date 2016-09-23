package com.lq.ren.many.learn.alarm;

import android.util.Log;

/**
 * Created by lqren on 16/7/27.
 */
public class Alarm {
    private int year;
    private int month;
    private int day;
    private int minute;
    private static java.util.Calendar calendar;
    private static Alarm instance;

    public static Alarm getInstance(){
        if(instance == null){
            instance = new Alarm();
        }
        return instance;
    }

    public Alarm(){
        year = calendar.get(java.util.Calendar.YEAR);
        month = calendar.get(java.util.Calendar.MONTH);
        day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        minute = calendar.get(java.util.Calendar.MINUTE);
        Log.i("HEHE", "calendar "+ calendar);
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
