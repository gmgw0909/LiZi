package com.fancy.learncenter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hyy on 2016/9/22.
 */
public class DataUtil {

    /**
     * 返回指定时间字符串。
     * <p>
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return String 指定格式的日期字符串.
     */
    public static String getDateTime(long microsecond, String format) {
        return getFormatDateTime(new Date(microsecond), format);
    }

    /**
     *
     *
     * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
     *
     * @param date   指定的日期
     * @param format 日期格式字符串
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    /**
     * 根据给定的格式，返回当前时间字符串。 和getFormatDate(String format)相似。
     *
     * @param format yyyy  MM dd  hh mm ss
     * @return 返回一个时间字符串
     */
    public static String getCurTimeByFormat(String format) {
        Date newdate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(newdate);
    }

}
