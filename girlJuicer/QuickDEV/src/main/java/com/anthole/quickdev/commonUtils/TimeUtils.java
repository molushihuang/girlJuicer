package com.anthole.quickdev.commonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static String getTime(long timeInMillis, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return getTime(timeInMillis, dateFormat);
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String getVideoDuration(Long duration) {
        String durationStr;
        if (duration / (1000 * 60) < 1) {
            if (duration / 1000 < 10) {
                durationStr = "00:0" + String.valueOf(duration).substring(0, 1);
            } else {
                durationStr = "00:" + String.valueOf(duration / 1000);
            }

        } else {
            if (duration % (1000 * 60) < 1000) {
                durationStr = String.valueOf((duration - duration % (1000 * 60)) / (1000 * 60)) + ":0" + String.valueOf(duration % (1000 * 60)).substring(0, 1);
            } else {
                durationStr = String.valueOf((duration - duration % (1000 * 60)) / (1000 * 60)) + ":" + String.valueOf(duration % (1000 * 60)).substring(0, 2);
            }

        }
        return durationStr;
    }
}
