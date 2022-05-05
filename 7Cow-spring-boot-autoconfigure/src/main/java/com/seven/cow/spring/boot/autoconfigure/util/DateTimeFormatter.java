package com.seven.cow.spring.boot.autoconfigure.util;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DateTimeFormatter {

    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String UTC_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String YEAR_MOTH_PATTERN = "yyyy-MM";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String CHINESE_TIME_PATTERN = "HH时mm分ss秒";

    private static final Map<String, java.time.format.DateTimeFormatter> formatterMap = new ConcurrentHashMap<>();

    public static java.time.format.DateTimeFormatter getDateTimeFormatter(String pattern) {
        return formatterMap.computeIfAbsent(pattern, java.time.format.DateTimeFormatter::ofPattern);
    }

}
