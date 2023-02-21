package com.txl.equipment.date;

import com.txl.equipment.exception.LinkageIllegalArgumentException;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by TangXiangLin on 2023-02-21 12:14
 * 用于方便地创建符合特定条件的java.util.Date实例
 */
public class DateBuilder {

    public enum IntervalUnit { MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR }
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final long MILLISECONDS_IN_MINUTE = 60l * 1000l;
    public static final long MILLISECONDS_IN_HOUR = 60l * 60l * 1000l;
    public static final long SECONDS_IN_MOST_DAYS = 24l * 60l * 60L;
    public static final long MILLISECONDS_IN_DAY = SECONDS_IN_MOST_DAYS * 1000l;
    private static final int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 100;

    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private int second;
    private TimeZone tz;
    private Locale lc;

    // 1。Constructor
    private DateBuilder() {
        Calendar cal = Calendar.getInstance();

        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }

    private DateBuilder(TimeZone tz) {
        Calendar cal = Calendar.getInstance(tz);

        this.tz = tz;
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }

    private DateBuilder(Locale lc) {
        Calendar cal = Calendar.getInstance(lc);

        this.lc = lc;
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }

    private DateBuilder(TimeZone tz, Locale lc) {
        Calendar cal = Calendar.getInstance(tz, lc);

        this.tz = tz;
        this.lc = lc;
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }


    // 2。 Builder
    public static DateBuilder newDate() {
        return new DateBuilder();
    }

    public static DateBuilder newDateInTimezone(TimeZone tz) {
        return new DateBuilder(tz);
    }

    public static DateBuilder newDateInLocale(Locale lc) {
        return new DateBuilder(lc);
    }

    public static DateBuilder newDateInTimeZoneAndLocale(TimeZone tz, Locale lc) {
        return new DateBuilder(tz, lc);
    }

    public Date build() {
        Calendar cal;

        if(tz != null && lc != null)
            cal = Calendar.getInstance(tz, lc);
        else if(tz != null)
            cal = Calendar.getInstance(tz);
        else if(lc != null)
            cal = Calendar.getInstance(lc);
        else
            cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /** 设置小时 */
    public DateBuilder atHourOfDay(int atHour) {
        validateHour(atHour);
        this.hour = atHour;
        return this;
    }

    /** 设置分钟 */
    public DateBuilder atMinute(int atMinute) {
        validateMinute(atMinute);
        this.minute = atMinute;
        return this;
    }

    /** 设置秒 */
    public DateBuilder atSecond(int atSecond) {
        validateSecond(atSecond);
        this.second = atSecond;
        return this;
    }

    /** 设置时、分、秒 */
    public DateBuilder atHourMinuteAndSecond(int atHour, int atMinute, int atSecond) {
        validateHour(atHour);
        validateMinute(atMinute);
        validateSecond(atSecond);
        this.hour = atHour;
        this.second = atSecond;
        this.minute = atMinute;
        return this;
    }

    /** 设置在某天 */
    public DateBuilder onDay(int onDay) {
        validateDayOfMonth(onDay);
        this.day = onDay;
        return this;
    }

    /** 设置在某月 */
    public DateBuilder inMonth(int inMonth) {
        validateMonth(inMonth);
        this.month = inMonth;
        return this;
    }

    /** 设置月中的某天 */
    public DateBuilder inMonthOnDay(int inMonth, int onDay) {
        validateMonth(inMonth);
        validateDayOfMonth(onDay);
        this.month = inMonth;
        this.day = onDay;
        return this;
    }

    /** 设置在某年 */
    public DateBuilder inYear(int inYear) {
        validateYear(inYear);

        this.year = inYear;
        return this;
    }

    /** 设置在某时区 */
    public DateBuilder inTimeZone(TimeZone timezone) {
        this.tz = timezone;
        return this;
    }

    /** 设置在某区域 */
    public DateBuilder inLocale(Locale locale) {
        this.lc = locale;
        return this;
    }

    /** 未来日期 */
    public static Date futureDate(int interval, IntervalUnit unit) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.setLenient(true);
        c.add(translate(unit), interval);
        return c.getTime();
    }

    /** 转换 */
    private static int translate(IntervalUnit unit) {
        switch(unit) {
            case DAY : return Calendar.DAY_OF_YEAR;
            case HOUR : return Calendar.HOUR_OF_DAY;
            case MINUTE : return Calendar.MINUTE;
            case MONTH : return Calendar.MONTH;
            case SECOND : return Calendar.SECOND;
            case MILLISECOND : return Calendar.MILLISECOND;
            case WEEK : return Calendar.WEEK_OF_YEAR;
            case YEAR : return Calendar.YEAR;
            default : throw new LinkageIllegalArgumentException("Unknown IntervalUnit");
        }
    }

    /** 明天 */
    public static Date tomorrowAt(int hour, int minute, int second) {
        validateSecond(second);
        validateMinute(minute);
        validateHour(hour);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        c.add(Calendar.DAY_OF_YEAR, 1);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 多少天之后 */
    public static Date todayAt(int hour, int minute, int second) {
        return dateOf(hour, minute, second);
    }

    /** 多少时、分、秒之后 */
    public static Date dateOf(int hour, int minute, int second) {
        validateSecond(second);
        validateMinute(minute);
        validateHour(hour);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 多少时、分、天、月之后 */
    public static Date dateOf(int hour, int minute, int second,
                              int dayOfMonth, int month) {
        validateSecond(second);
        validateMinute(minute);
        validateHour(hour);
        validateDayOfMonth(dayOfMonth);
        validateMonth(month);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 多少时、分、天、月、年之后 */
    public static Date dateOf(int hour, int minute, int second,
                              int dayOfMonth, int month, int year) {
        validateSecond(second);
        validateMinute(minute);
        validateHour(hour);
        validateDayOfMonth(dayOfMonth);
        validateMonth(month);
        validateYear(year);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 距离现在还有多少小时 */
    public static Date evenHourDateAfterNow() {
        return evenHourDate(null);
    }

    /** 距离现在还有多少小时 */
    public static Date evenHourDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 距离现在还有多少小时 */
    public static Date evenHourDateBefore(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 距离现在还有多少分钟 */
    public static Date evenMinuteDateAfterNow() {
        return evenMinuteDate(null);
    }

    /** 距离现在还有多少分钟 */
    public static Date evenMinuteDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 距离现在还有多少分钟 */
    public static Date evenMinuteDateBefore(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 距离现在还有多少秒 */
    public static Date evenSecondDateAfterNow() {
        return evenSecondDate(null);
    }

    /** 距离现在还有多少秒 */
    public static Date evenSecondDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + 1);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 距离现在还有多少秒 */
    public static Date evenSecondDateBefore(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** 接下来还有多少分 */
    public static Date nextGivenMinuteDate(Date date, int minuteBase) {
        if (minuteBase < 0 || minuteBase > 59) {
            throw new LinkageIllegalArgumentException(
                    "minuteBase must be >=0 and <= 59");
        }
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        if (minuteBase == 0) {
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }

        int minute = c.get(Calendar.MINUTE);
        int arItr = minute / minuteBase;
        int nextMinuteOccurance = minuteBase * (arItr + 1);
        if (nextMinuteOccurance < 60) {
            c.set(Calendar.MINUTE, nextMinuteOccurance);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        } else {
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
    }

    /** 接下来还有多少秒 */
    public static Date nextGivenSecondDate(Date date, int secondBase) {
        if (secondBase < 0 || secondBase > 59) {
            throw new LinkageIllegalArgumentException(
                    "secondBase must be >=0 and <= 59");
        }
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setLenient(true);
        if (secondBase == 0) {
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
        int second = c.get(Calendar.SECOND);
        int arItr = second / secondBase;
        int nextSecondOccurance = secondBase * (arItr + 1);
        if (nextSecondOccurance < 60) {
            c.set(Calendar.SECOND, nextSecondOccurance);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        } else {
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
    }

    /** 转换时间 */
    public static Date translateTime(Date date, TimeZone src, TimeZone dest) {
        Date newDate = new Date();
        int offset = (dest.getOffset(date.getTime()) - src.getOffset(date.getTime()));
        newDate.setTime(date.getTime() - offset);
        return newDate;
    }

    /** 校验天 */
    public static void validateDayOfWeek(int dayOfWeek) {
        if (dayOfWeek < SUNDAY || dayOfWeek > SATURDAY) {
            throw new LinkageIllegalArgumentException("Invalid day of week.");
        }
    }

    /** 校验小时 */
    public static void validateHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new LinkageIllegalArgumentException(
                    "Invalid hour (must be >= 0 and <= 23).");
        }
    }

    /** 校验分钟 */
    public static void validateMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new LinkageIllegalArgumentException(
                    "Invalid minute (must be >= 0 and <= 59).");
        }
    }

    /** 校验秒 */
    public static void validateSecond(int second) {
        if (second < 0 || second > 59) {
            throw new LinkageIllegalArgumentException(
                    "Invalid second (must be >= 0 and <= 59).");
        }
    }

    /** 校验天 */
    public static void validateDayOfMonth(int day) {
        if (day < 1 || day > 31) {
            throw new LinkageIllegalArgumentException("Invalid day of month.");
        }
    }

    /** 校验月 */
    public static void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new LinkageIllegalArgumentException(
                    "Invalid month (must be >= 1 and <= 12.");
        }
    }

    /** 校验天 */
    public static void validateYear(int year) {
        if (year < 0 || year > MAX_YEAR) {
            throw new LinkageIllegalArgumentException(
                    "Invalid year (must be >= 0 and <= " + MAX_YEAR);
        }
    }

}
