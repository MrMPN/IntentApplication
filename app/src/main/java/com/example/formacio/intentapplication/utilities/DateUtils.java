package com.example.formacio.intentapplication.utilities;

import android.icu.util.Calendar;

import java.util.ArrayList;

public class DateUtils {

    static public ArrayList<Integer> stringToDate (int day){
        ArrayList<Integer> dayOfWeek = new ArrayList<>();
        switch (day){
            case 0:
                dayOfWeek.add(Calendar.MONDAY);
                break;
            case 1:
                dayOfWeek.add(Calendar.TUESDAY);
                break;
            case 2:
                dayOfWeek.add(Calendar.WEDNESDAY);
                break;
            case 3:
                dayOfWeek.add(Calendar.THURSDAY);
                break;
            case 4:
                dayOfWeek.add(Calendar.FRIDAY);
                break;
            case 5:
                dayOfWeek.add(Calendar.SATURDAY);
                break;
            case 6:
                dayOfWeek.add(Calendar.SUNDAY);
                break;
        }
        return dayOfWeek;
    }
}
