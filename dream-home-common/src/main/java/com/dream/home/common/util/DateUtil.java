package com.dream.home.common.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    /**
     * 年-月(yyyy-MM)
     */
    public static final String PATTERN_YEAR_MONTH = "yyyy-MM";
    public static final ConcurrentDateFormat DATETIME_FORMAT = ConcurrentDateFormat.of(PATTERN_DATETIME);
    public static final ConcurrentDateFormat DATE_FORMAT = ConcurrentDateFormat.of(PATTERN_DATE);
    public static final ConcurrentDateFormat TIME_FORMAT = ConcurrentDateFormat.of(PATTERN_TIME);
    /**
     * 年-月(yyyy-MM)
     */
    public static final ConcurrentDateFormat YEAR_MONTH_FORMAT = ConcurrentDateFormat.of(PATTERN_YEAR_MONTH);

    /**
     * 设置年
     *
     * @param date   时间
     * @param amount 年数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setYears(Date date, int amount) {
        return set(date, Calendar.YEAR, amount);
    }

    /**
     * 设置月
     *
     * @param date   时间
     * @param amount 月数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setMonths(Date date, int amount) {
        return set(date, Calendar.MONTH, amount);
    }

    /**
     * 设置周
     *
     * @param date   时间
     * @param amount 周数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setWeeks(Date date, int amount) {
        return set(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 设置天
     *
     * @param date   时间
     * @param amount 天数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setDays(Date date, int amount) {
        return set(date, Calendar.DATE, amount);
    }

    /**
     * 设置小时
     *
     * @param date   时间
     * @param amount 小时数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setHours(Date date, int amount) {
        return set(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 设置分钟
     *
     * @param date   时间
     * @param amount 分钟数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setMinutes(Date date, int amount) {
        return set(date, Calendar.MINUTE, amount);
    }

    /**
     * 设置秒
     *
     * @param date   时间
     * @param amount 秒数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setSeconds(Date date, int amount) {
        return set(date, Calendar.SECOND, amount);
    }

    /**
     * 设置毫秒
     *
     * @param date   时间
     * @param amount 毫秒数，-1表示减少
     * @return 设置后的时间
     */
    public static Date setMilliseconds(Date date, int amount) {
        return set(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 设置日期属性
     *
     * @param date          时间
     * @param calendarField 更改的属性
     * @param amount        更改数，-1表示减少
     * @return 设置后的时间
     */
    private static Date set(Date date, int calendarField, int amount) {
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 日期时间格式化
     *
     * @param date 时间
     * @return 格式化后的时间
     */
    public static String formatDateTime(Date date) {
        return DATETIME_FORMAT.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date 时间
     * @return 格式化后的时间
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * 时间格式化
     *
     * @param date 时间
     * @return 格式化后的时间
     */
    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date    时间
     * @param pattern 表达式
     * @return 格式化后的时间
     */
    public static String format(Date date, String pattern) {
        return ConcurrentDateFormat.of(pattern).format(date);
    }

    /**
     * 将字符串转换为时间
     *
     * @param dateStr 日期字符串
     * @return 时间
     */
    public static Date parseDate(String dateStr) {
        ConcurrentDateFormat format = ConcurrentDateFormat.of(PATTERN_DATE);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 将字符串转换为时间
     *
     * @param dateStr 日期字符串
     * @return 时间
     */
    public static Date parseTime(String dateStr) {
        ConcurrentDateFormat format = ConcurrentDateFormat.of(PATTERN_DATETIME);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 将字符串转换为时间
     *
     * @param dateStr 时间字符串
     * @param pattern 表达式
     * @return 时间
     */
    public static Date parse(String dateStr, String pattern) {
        ConcurrentDateFormat format = ConcurrentDateFormat.of(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 将字符串转换为时间
     *
     * @param dateStr 时间字符串
     * @param format  ConcurrentDateFormat
     * @return 时间
     */
    public static Date parse(String dateStr, ConcurrentDateFormat format) {
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 相差的天数
     *
     * @param early
     * @param late
     * @return
     */
    public static final int getDaysBetween(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        // 设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);

        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);

        // 得到两个日期相差的天数
        int days = (int) (((long) (caled.getTime().getTime() - calst.getTime().getTime())) / 1000 / 3600 / 24) + 1;

        return days;
    }

    /**
     * 取得月份的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        int days = 0;
        switch (month + 1) {
            case 2:
                int daysOfFeb = 28;
                if (year % 4 == 0) {
                    daysOfFeb = 29;
                }
                days = daysOfFeb;
                break;
            case 1:
                days = 31;
                break;
            case 3:
                days = 31;
                break;
            case 5:
                days = 31;
                break;
            case 7:
                days = 31;
                break;
            case 8:
                days = 31;
                break;
            case 10:
                days = 31;
                break;
            case 12:
                days = 31;
                break;
            case 4:
                days = 30;
                break;
            case 6:
                days = 30;
                break;
            case 9:
                days = 30;
                break;
            case 11:
                days = 30;
                break;
            default:
                days = 30;
        }
        return days;
    }

    /**
     * 增加月份
     *
     * @param date
     * @param monthAmount
     * @return
     */
    public static Date addMonths(Date date, int monthAmount) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthAmount);
        return cal.getTime();
    }

    /**
     * 增加指定的天数
     *
     * @param date  指定日期
     * @param value 天数
     * @return 增加后的日期
     */
    public static Date addDays(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * LocalDateTime 2 Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
//        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zone).toInstant();
//        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 两个日期间相差的天数  这里不包含起始日期，如果想要结果包含起始日期，则需要再结果上+1
     *
     * @return
     */
    public static int getDifferenceDayCount(LocalDate startDate, LocalDate endDate) {

        //LocalDate startDate = LocalDate.parse(startDateStr);
        //LocalDate endDate = LocalDate.parse(endDateStr);
        //取正数
        return Math.abs((int) (endDate.toEpochDay() - startDate.toEpochDay()));
    }

    public static void main(String[] args) {
        Date d1 = parseDate("2019-02-27");
        Date d2 = parseDate("2019-01-29");
        System.out.println("日期：" + formatDate(d2));

        Date date = addMonths(d2, 1);
        System.out.println("加一个月：" + formatDate(date));

        String str = "18:00";
        LocalTime time = LocalTime.now();
        time = LocalTime.parse(str);
        System.out.println(1);
        //System.out.println(getDifferenceDayCount("2019-09-09","2019-09-10"));//不包含起始日期的天数获取

    }
}
