package com.howzits.baselib.util;

public class DateUtil {

    public static String formatStandardTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / (60 * 60);
        long minutes = seconds / 60 % 60;
        long sec = seconds % 60;
        return String.format("%1$02d:%2$02d:%3$02d", hours, minutes, sec);
    }
}
