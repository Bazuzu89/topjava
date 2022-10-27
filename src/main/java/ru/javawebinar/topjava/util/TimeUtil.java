package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        boolean result;
        if (startTime != null && endTime != null) {
            result = lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
        } else if (startTime == null && endTime != null) {
            result =  lt.compareTo(endTime) < 0;
        } else if (startTime != null && endTime == null) {
            result = lt.compareTo(startTime) >= 0;
        } else {
            result = true;
        }
        return result;
    }
}
