package com.roger.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     *sql.date
     */
    public static java.sql.Date toSQLDate(Date date) {
        return date instanceof java.sql.Date ? (java.sql.Date) date : toSQLDate(date.getTime());
    }

    public static java.sql.Date toSQLDate(long time) {
        return new java.sql.Date(time);
    }

    public static java.sql.Date getSQLDate() {
        return toSQLDate(System.currentTimeMillis());
    }

    /**
     * sql.Timestamp
     */
    public static java.sql.Timestamp toTimestamp(Date date) {
        return date instanceof java.sql.Timestamp ? (java.sql.Timestamp) date : toTimestamp(date.getTime());
    }

    public static java.sql.Timestamp toTimestamp(long time) {
        return new java.sql.Timestamp(time);
    }

    public static java.sql.Timestamp getTimestamp() {
        return toTimestamp(System.currentTimeMillis());
    }


    public static String getNoWTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date nowDate = new Date();
        return sdf.format(nowDate);
    }
}
