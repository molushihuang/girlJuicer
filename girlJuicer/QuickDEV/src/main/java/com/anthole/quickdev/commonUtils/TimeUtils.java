package com.anthole.quickdev.commonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
     * 通过时间戳获取默认格式的时间
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 通过时间戳获取指定格式的时间
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * 通过时间戳获取指定格式的时间
     *
     * @param timeInMillis
     * @param format
     * @return
     */
    public static String getTime(long timeInMillis, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return getTime(timeInMillis, dateFormat);
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

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(String format) {
        return getTime(getCurrentTimeInLong(), format);
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
     * 调此方法输入所要转换的时间输入例如（"yyyy-MM-dd HH:mm:ss"）返回时间戳(不包括毫秒)
     *
     * @param time
     * @return
     */
    public static String getTimestamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 根据指定的格式化后的时间转换为时间戳(不包括毫秒)
     * @param time
     * @return
     */
    public static String getTimestamp(String time, String stringFormat) {
        SimpleDateFormat sdr = new SimpleDateFormat(stringFormat, Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳(不包括毫秒)
     *
     * @param time
     * @return
     */
    public String getTimestampWord(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（10位不包含毫秒）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timeDate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（13位包含毫秒）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timeDateInMillis(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i));
        return times;

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
