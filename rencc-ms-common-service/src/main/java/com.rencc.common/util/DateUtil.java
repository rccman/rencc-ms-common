package com.rencc.common.util;


import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

public class DateUtil {
    public static final String PUSH_MESSAGE_FORMAT = "MM月dd日 HH:mm";
    public static final String str[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};// 字符串数组
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATE_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_HH_MM_SS_Z1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_HH_MM_SS_Z2 = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_HH_MM_SS_Z3 = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final String DATE_HH_MM_SS_Z4 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_HH_MM_SS_A = "MM/dd/yyyy HH:mm:ss a";
    public static final String DATE_HHMMSS = "yyMMddHHmmss";

    /**
     * 时间格式化（到日期）
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            newDate = shortsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     * 时间格式化（到秒）
     * @return
     */
    public static String formatTime(Date date) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     * 时间格式化（到分）
     * @return
     */
    public static String formatTimeMin(Date date) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static String formatLoaclTime(Date date) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat localsdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            newDate = localsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static Long parseUTC(Date date) {
        return parseUTC(formatDate(date, "yyMMddHHmmss"));
    }

    public static final long parseUTC(String time) {
        int year = 2000 + Integer.parseInt(time.substring(0, 2));
        int month = Integer.parseInt(time.substring(2, 4)) - 1;
        int date = Integer.parseInt(time.substring(4, 6));
        int hour = Integer.parseInt(time.substring(6, 8));
        int minute = Integer.parseInt(time.substring(8, 10));
        int second = Integer.parseInt(time.substring(10, 12));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute, second);
        return calendar.getTimeInMillis() / 1000L;
    }

    /*
     * 根据时间转换问候语 早上：5:00 —— 8:59 上午：9:00 ——10:59 中午：11:00——12:59 下午：13:00——18:59
     * 晚上：19:00——23:59 凌晨：24:00—— 4:59
     */
    public static String getGreetings() {
        String greetings = "早上好";
        String nowdate1 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());// 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            Date d1 = sdf.parse("5:00:00");
            Date d2 = sdf.parse("8:59:59");

            Date d3 = sdf.parse("9:00:00");
            Date d4 = sdf.parse("10:59:59");

            Date d5 = sdf.parse("11:00:00");
            Date d6 = sdf.parse("12:59:59");

            Date d7 = sdf.parse("13:00:00");
            Date d8 = sdf.parse("18:59:59");

            Date d9 = sdf.parse("19:00:00");
            Date d10 = sdf.parse("23:59:59");

            Date d11 = sdf.parse("24:00:00");
            Date d12 = sdf.parse("4:59:59");
            Date nowdate = sdf.parse(nowdate1);
            if (nowdate.after(d12) && nowdate.before(d3))
                greetings = "早上好";
            else if (nowdate.after(d2) && nowdate.before(d5))
                greetings = "上午好";
            else if (nowdate.after(d4) && nowdate.before(d7))
                greetings = "中午好";
            else if (nowdate.after(d6) && nowdate.before(d9))
                greetings = "下午好";
            else if (nowdate.after(d8) && nowdate.before(d11))
                greetings = "晚上好";
            else if (nowdate.after(d10) && nowdate.before(d1))
                greetings = "凌晨好";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return greetings;
    }

    /**
     * 时间格式化（到秒）
     *
     * @param ticks 时间刻度
     * @return
     */
    public static String formatTime(Long ticks) {
        String newDate = "";
        if (ticks == null)
            return newDate;
        try {
            Date date = new Date(ticks);
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static Date string2Date(String s, int type) {
        if (s == null) {
            return null;
        }
        Calendar cal = null;
        String a[] = s.split("-| |:");
        try {
            if (a.length >= 3) {
                cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.valueOf(a[0]));
                cal.set(Calendar.MONTH, Integer.valueOf(a[1]) - 1);
                cal.set(Calendar.DATE, Integer.valueOf(a[2]));
            }
            if (type == 0) {
                if (a.length >= 5) {
                    cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(a[3]));
                    cal.set(Calendar.MINUTE, Integer.valueOf(a[4]));
                    if (a.length == 6) {
                        cal.set(Calendar.SECOND, Integer.valueOf(a[5]));
                    }
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                }
            } else if (type == 1) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
            } else if (type == 2) {
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
            }
        } catch (Exception e) {

        }
        if (cal != null) {
            return cal.getTime();
        }
        return null;
    }

    public static Date stringDateMin(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDateToHHMM(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDateToHHMMSS(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm:ss");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDate(String s) {
        Date date = null;

        try {
            if (StringUtils.isEmpty(s)) {

                return date;
            }
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = longsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDate(String s, String format) {
        Date date = null;

        try {
            if (StringUtils.isEmpty(s)) {

                return date;
            }
            SimpleDateFormat shortsdf = new SimpleDateFormat(format);
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static String stringDateToHHMM(Date date) {
        if (date != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm");
            return shortsdf.format(date);
        }
        return null;
    }

    public static String stringDateToHHMMSS(Date date) {
        if (date != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm:ss");
            return shortsdf.format(date);
        }
        return null;
    }

    /**
     * 以短格式格式化时间,实例：2010-09-19
     *
     * @param time 时间刻度
     * @return 格式化后的时间
     * @author zhengrunjin @ 2010-09-19
     */
    public static String stringDateShortFormat(Long time) {
        if (time != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            return shortsdf.format(new Date(time));
        }
        return null;
    }

    public static String stringDate(Long l) {
        if (l != null) {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return longsdf.format(new Date(l));
        }
        return null;
    }

    public static String stringDateShortFormatUTC(Long time) {
        if (time != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            shortsdf.setTimeZone(TimeZone.getDefault());
            return shortsdf.format(new Date(time * 1000));
        }
        return null;
    }

    public static String stringDateUTC(Long l) {
        if (l != null) {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            longsdf.setTimeZone(TimeZone.getDefault());
            return longsdf.format(new Date(l * 1000));
        }
        return "";
    }

    public static String stringDateUTC(Long l, String format) {
        if (l != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            df.setTimeZone(TimeZone.getDefault());
            return df.format(new Date(l * 1000));
        }
        return "";
    }

    /**
     * 返回于指定日期间隔一定天数的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getSpecDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
        return calendar.getTime();
    }

    public static boolean after(Date date1, Date date2) {
        Calendar dc1 = Calendar.getInstance();
        dc1.setTime(date1);
        Calendar dc2 = Calendar.getInstance();
        dc2.setTime(date2);
        return dc1.after(dc2);
    }

    public static boolean before(Date date1, Date date2) {
        Calendar dc1 = Calendar.getInstance();
        dc1.setTime(date1);
        Calendar dc2 = Calendar.getInstance();
        dc2.setTime(date2);
        return dc1.before(dc2);
    }

    // 日期转换
    public static java.sql.Date getBeforeAfterDate(String datestr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date olddate = null;
        try {
            df.setLenient(false);
            olddate = new java.sql.Date(df.parse(datestr).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("日期转换错误");
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(olddate);

        int Year = cal.get(Calendar.YEAR);
        int Month = cal.get(Calendar.MONTH);
        int Day = cal.get(Calendar.DAY_OF_MONTH);

        int NewDay = Day + day;

        cal.set(Calendar.YEAR, Year);
        cal.set(Calendar.MONTH, Month);
        cal.set(Calendar.DAY_OF_MONTH, NewDay);

        return new java.sql.Date(cal.getTimeInMillis());
    }

    // 日期转换
    public static Date getBeforeAfterDate(Date date, int day) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int Year = cal.get(Calendar.YEAR);
        int Month = cal.get(Calendar.MONTH);
        int Day = cal.get(Calendar.DAY_OF_MONTH);

        int NewDay = Day + day;

        cal.set(Calendar.YEAR, Year);
        cal.set(Calendar.MONTH, Month);
        cal.set(Calendar.DAY_OF_MONTH, NewDay);

        return new Date(cal.getTimeInMillis());
    }

    // 参数日期+小时数得到新日期
    // type：1=天数 ，2=小时数 3=月数
    public static Date getNewDate(Date d, int num, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        switch (type) {
            case 1:
                calendar.add(Calendar.DAY_OF_YEAR, num);
                break;
            case 2:
                calendar.add(Calendar.HOUR_OF_DAY, num);
                break;
            case 3:
                calendar.add(Calendar.MONTH, num);
                break;
        }

        return calendar.getTime();
    }

    /**
     * 两个日期相差的天数,只精确到天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffDays(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = Math.abs(time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 两个日期时分秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getDistanceTime(Date date1, Date date2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour + ":" + min + ":" + sec;
    }

    /**
     * 两个日期相差的天数,只精确到年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffYear(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int time1 = cal.get(Calendar.YEAR);
        cal.setTime(date2);
        int time2 = cal.get(Calendar.YEAR);
        int between_year = time2 - time1;
        return between_year;
    }

    /**
     * 两个日期相差的月数,只精确到月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffMonth(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int time1 = cal.get(Calendar.MONTH);
        cal.setTime(date2);
        int time2 = cal.get(Calendar.MONTH);
        int between_year = time2 - time1;
        return between_year;
    }

    /**
     * 返回一天的结束时间
     *
     * @param d
     * @return
     */
    public static String getEndTimeOfDays(String d) {
        return d.trim() + " 23:59:59";
    }

    /**
     * 获取一天最早的时间点 00:00:00
     *
     * @param date
     * @return
     */
    public static String getEarliestOfDay(String date) {
        return date.trim() + " 00:00:00";
    }

    /**
     * 获取一个月最早的时间
     *
     * @param date
     * @return
     */
    public static String getEarliestOfMonth(String date) {

        return formatDate(stringDateMin(date), "yyyy-MM") + "-01 00:00:00";
    }

    /**
     * 去掉日期的时间部分
     *
     * @param d
     * @return
     */
    public static String formatDateStr(String d) {
        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return shortsdf.format(longsdf.parse(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 去掉日期的时间部分
     *
     * @param d
     * @return
     */
    public static String formatString(String d) {
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return longsdf.format(longsdf.parse(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取过几分钟的时间
     *
     * @param d
     * @return
     */
    public static Date addMinutes(Date d, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获得过几小时的时间
     *
     * @param d     需要计算的时间类型
     * @param hours 小时数
     * @return
     */
    public static Date addHours(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 获得过几天的时间
     *
     * @param d     需要计算的时间类型
     * @return
     */
    public static Date addDays(Date d, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获得前几小时的时间
     *
     * @param d     需要计算的时间类型
     * @param hours 小时数
     * @return
     */
    public static Date beforeHours(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, -hours);
        return calendar.getTime();
    }

    public static Date beforeMin(Date d, int mins) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, -mins);
        return calendar.getTime();
    }

    public static String formatDate(Date date, String format) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            newDate = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static Date formatDate(String strDate, String format) {
        Date date = null;
        if (!StringUtils.hasLength(strDate))
            return date;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            date = df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 根据长类型转换date
     */
    public static Date formatDateBylong(String dateString) {
        if (!StringUtils.hasLength(dateString)) {
            return null;
        }
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return longsdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDate(String datetime) {
        if (datetime == null) {
            return null;
        }
        if (datetime.indexOf(" ") == -1) {
            return datetime;
        }
        return datetime.trim().split(" ")[0];
    }

    /**
     * 获得HH:mm:ss格式的时间字符串
     *
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static String getTimeStr(int hours, int minutes, int seconds) {
        StringBuffer timeStr = new StringBuffer();
        if (hours < 10) {
            timeStr.append("0" + hours);
        } else {
            timeStr.append(hours);
        }
        timeStr.append(":");
        if (minutes < 10) {
            timeStr.append("0" + minutes);
        } else {
            timeStr.append(minutes);
        }
        timeStr.append(":");
        if (seconds < 10) {
            timeStr.append("0" + seconds);
        } else {
            timeStr.append(seconds);
        }

        return timeStr.toString();
    }

    /**
     * 获取时间为0点的某天的Date对象
     *
     * @param offset
     * @return
     */
    public static Date getZeroOfDay(int offset) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), (cal.get(Calendar.DAY_OF_MONTH) + offset), 0, 0, 0);
        return cal.getTime();
    }

    /**
     * 获取时间为24点的某天的Date对象
     *
     * @param offset
     * @return
     */
    public static Date get24OfDay(int offset) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), (cal.get(Calendar.DAY_OF_MONTH) + offset), 23, 59, 59);
        return cal.getTime();
    }

    /**
     * 供图片使用（缓存）
     *
     * @return
     */
    public static Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static Date formatDate(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat(format);
        String newDate = df.format(calendar.getTime());
        Date date = null;
        try {
            date = df.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取一天最早的时间点 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getEarliestOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取一天最晚的时间点 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLatestOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * @param date   创建时间
     * @param minute 倒数分钟
     * @return
     */
    public static long countDownTime(long date, int minute) {
        long countDownTime = 0;

        String dateUTC = DateUtil.stringDateUTC(date);
        Date dowmTime = DateUtil.addMinutes(stringDate(dateUTC), minute);
        Date now = new Date();
        if (dowmTime.after(now)) {
            countDownTime = (dowmTime.getTime() - now.getTime()) / 1000;
        }
        if (countDownTime < 0) {
            countDownTime = 0;
        }
        return countDownTime;
    }

    /**
     * 相差秒数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long dateDiffSec(long time1, long time2) {
        long diff = time2 - time1;
        long diffSec = diff / 1000;
        return diffSec;
    }

    /**
     * 相差秒数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long diffMinute(Date time1, Date time2) {
        long diff = time2.getTime() - time1.getTime();
        long diffSec = diff / (60 * 1000);
        return diffSec;
    }

    /**
     * 精确到小时
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int diffHour(Date time1, Date time2) {
        long diff = time2.getTime() - time1.getTime();
        long diffSec = diff / (60 * 60 * 1000);
        return new Long(diffSec).intValue();
    }

    /**
     * 相差分钟数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int diffMinute(long time1, long time2) {
        long diff = time2 - time1;
        long diffSec = diff / (60 * 1000);
        return new Long(diffSec).intValue();
    }

    /**
     * 获取订单提示时间
     *
     * @param startTime
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:36:03
     */
    public static String getOrdersTimeStr(Date startTime) {
        int i = diffDays(formatDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"),
                formatDate(formatDate(startTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
        StringBuffer sb = new StringBuffer();
        if (i == 0) {
            sb.append("今天(" + getDateOfWeek(startTime) + ")");
        } else if (i == 1) {
            sb.append("明天(" + getDateOfWeek(startTime) + ")");
        } else {
            sb.append(formatDate(startTime, "MM月dd日"));
        }
        sb.append(" " + formatDate(startTime, "HH:mm") + " 出发");
        return sb.toString();
    }

    /**
     * 将日期转换成日常短语 <br/>
     * 1:当天 : 今天 HH:mm <br/>
     * 2:当年 : MM:dd <br/>
     * 3:往年 : yyyy-MM-dd <br/>
     *
     * @param date
     * @return
     */
    public static String formatDateToPhrase(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTime(date);
        if (target.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
            return formatDate(date, "yyyy-MM-dd");
        }
        if (target.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR)) {
            return formatDate(date, "MM-dd");
        }
        return formatDate(date, "今天 HH:mm");
    }

    /**
     * 判断当前日期是否在两个日期之间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean betweenStartDateAndEndDate(Date date, Date startTime, Date endTime) {
        if (date.compareTo(endTime) == -1 && startTime.compareTo(date) == -1) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否在两个时间点内
     *
     * @param time 小时 以","分隔,如23,6
     * @return
     * @author 朱厚飞
     * @date 2015年3月13日 下午3:44:35
     */
    public static boolean between(String time) {
        boolean flag = false;
        Integer stime = null;
        Integer etime = null;
        if (time != null) {
            String[] times = time.split(",");
            if (times.length == 2 && isNumeric(times[0].trim()) && isNumeric(times[1].trim())) {
                stime = Integer.valueOf(times[0].trim());
                etime = Integer.valueOf(times[1].trim());
            }

        }
        if (stime != null && etime != null) {
            Calendar c = Calendar.getInstance();
            int hours = c.get(Calendar.HOUR_OF_DAY);
            if (stime >= etime) {
                if (etime >= 1) {
                    if (stime <= hours && hours <= 23 || 0 <= hours && hours <= etime - 1) {
                        flag = true;
                    }
                } else {
                    if (stime <= hours && hours <= 23 || 0 <= hours && hours <= 23) {
                        flag = true;
                    }
                }
            } else {
                if (etime >= 1) {
                    if (stime <= hours && hours <= etime - 1) {
                        flag = true;
                    }
                } else {
                    if (stime <= hours && hours <= 23) {
                        flag = true;
                    }
                }
            }

        }
        return flag;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static Date format(Date date, String format) {
        return formatDate(date.getTime(), format);
    }

    /**
     * 获取消息推送格式的日期 <b>03月01日 08:00（周日）</b>
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:26:32
     */
    public static String getPushMessageDate(Date date) {
        String shortDate = formatDate(date, PUSH_MESSAGE_FORMAT);
        return shortDate + "（" + str[getDayOfWeek(date) - 1] + "）";
    }

    /**
     * 获取指定日期为星期几:1-7
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:26:53
     */
    public static Integer getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期几，中文的，周日~周六
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:32:57
     */
    public static String getDateOfWeek(Date date) {
        return str[getDayOfWeek(date) - 1];
    }

    /**
     * "" + day1 + "天" + hour1 + "小时" + minute1 + "分钟";
     *
     * @param minute
     * @return
     */
    public static String hourMinute(int minute) {
        minute = minute * 60;
        long day1 = minute / (24 * 3600);
        long hour1 = minute % (24 * 3600) / 3600;
        long minute1 = minute % 3600 / 60;
        // long second1 = minute % 60;
        StringBuffer sb = new StringBuffer("");
        if (day1 > 0) {
            sb.append(day1 + "天");
        }
        if (hour1 >= 0) {
            sb.append(hour1 + "小时");
        }
        if (minute1 >= 0) {
            sb.append(minute1 + "分钟");
        }

        return sb.toString();
    }

    public static Long getTime(String date, SimpleDateFormat sdf) {
        try {
            return sdf.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * utc 转 Date
     *
     * @param date
     * @return
     */
    public static Date utc2Date(Long date) {
        if (date == null) {
            return null;
        }
        return stringDate(DateUtil.stringDateUTC(date));
    }

    /**
     * 时间格式化（到秒）
     * @return
     */
    public static String formatTimeToString(Date date) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyyMMddHHmmss");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static List<String> process(String date1, String date2) {
        List<String> strs = new ArrayList<String>();

        String tmp;
        if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
            tmp = date1;
            date1 = date2;
            date2 = tmp;
        }
        SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
        tmp = shortsdf.format(str2Date(date1).getTime());
        int num = 0;
        while (tmp.compareTo(date2) <= 0) {
            strs.add(tmp);
            num++;
            tmp = shortsdf.format(str2Date(tmp).getTime() + 3600 * 24 * 1000);
        }

        if (num == 0)
            System.out.println("两个日期相邻!");
        Collections.reverse(strs);

        return strs;
    }

    private static Date str2Date(String str) {
        if (str == null)
            return null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            return shortsdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 两个日期时分秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String[] getDistanceAllTime(Date date1, Date date2) {
        String strs[] = new String[]{"", ""};

        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            hour = hour + day * 24;
            float num = (float) diff / (60 * 60 * 1000);
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
            String hours = df.format(num);// 返回的是String类型
            strs[0] = String.valueOf(hours);
            strs[1] = hour + "时" + min + "分" + sec + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strs;
    }

    public static String getDistanceAllTime(Integer time) {
        Integer day = time / (24 * 60 * 60);
        Integer hour = (time / (60 * 60) - day * 24);
        Integer min = ((time / (60)) - day * 24 * 60 - hour * 60);
        return day + "天" + hour + "时" + min + "分";
    }

    public static String getDistanceAllTimes(Integer time) {
        Integer day = time / (24 * 60);
        Integer hour = (time / 60 - day * 24);
        Integer min = (time - day * 24 * 60);
        return day + "天" + hour + "时" + min + "分";
    }

    /***
     * 将某时分秒时间转换成当前日的时分秒时间 by xiaopeng
     *
     * @param hhmmss
     * @return
     */
    public static Date getNowDateByHHMMSS(Date hhmmss) {
        Date returnTime = null;
        if (hhmmss != null) {
            String toHHMMSSTimeStr = DateUtil.stringDateToHHMMSS(hhmmss);
            String nowTimeStr = DateUtil.formatDate(new Date());
            returnTime = DateUtil.stringDate(nowTimeStr + " " + toHHMMSSTimeStr);
        }
        return returnTime;
    }

    /**
     * @param time 指定时间
     * @param str  指定时间的与当前时间相比较的状态 before/after
     * @Title: specifiedTimeState
     * @Description: 判断是否在指定时间的指定状态之内
     * @author: MaZhen
     * @date 2017年10月9日 下午5:58:02
     * @return: boolean
     */
    public static boolean specifiedTimeState(String time, String str) {
        String nowDate = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());// 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date _nowdate = sdf.parse(nowDate);
            Date specifiedTime = sdf.parse(time);
            if (null != str && !"".equals(str)) {
                if (str.equals("before")) {
                    return _nowdate.before(specifiedTime);
                } else {
                    return _nowdate.after(specifiedTime);
                }
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 根据指定格式将字符串转化成日期
     */
    public static Date parseByPattern(String dateString, String pattern) throws ParseException {
        if (!StringUtils.hasLength(dateString)) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateString);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * Author:houshenglong date:2017年9月22日 method:获取当前月份
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance(); // 获取东八区时间
        int cmonth = c.get(Calendar.MONTH) + 1;
        return cmonth;
    }

    /**
     * 时间格式化（到月）
     *
     * @param date
     * @return
     */
    public static String formatDateToyyyyMM(Date date) {
        String newDate = "";
        if (date == null)
            return newDate;
        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM");
            newDate = shortsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     * 时间格式化（到月）
     *
     * @param date
     * @return
     */
    @SuppressWarnings("static-access")
    public static int get_DAY_OF_MONTH(Date date) {
        int dayOfMonth = -1;
        if (date == null)
            return -1;
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(date);
        dayOfMonth = calendarNow.get(calendarNow.DAY_OF_MONTH);

        return dayOfMonth;
    }

    /**
     * Created with: jingyan. Date: 2016/10/25 14:43 Description: date to str
     */
    public static String date2Str(Date date, String formatStr) {
        if (null == date) {
            return null;
        }
        if (null == formatStr || "".equals(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String formatDate = sdf.format(date);
        return formatDate;
    }

    /**
     * jdk1.8日期的线程安全处理 转string
     *
     * @param date
     * @return
     * @author cuiqh
     */
    public static String dateToStringByLocal(LocalDateTime date) {
        java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern(DATE_HH_MM_SS);
        return date.format(format);
    }

    /**
     * jdk1.8日期的线程安全处理 string 转 local
     *
     * @param date
     * @return
     * @author cuiqh
     */
    public static LocalDateTime stringToLocalDateByLocal(String date) {
        java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern(DATE_HH_MM_SS);
        return LocalDateTime.parse(date, format);
    }

    /**
     * jdk1.8日期的线程安全处理 给定日期减少多少天
     *
     * @param date
     * @param days
     * @return
     * @author cuiqh
     */
    public static LocalDateTime addDaysByLocal(LocalDateTime date, int days) {
        return date.plus(days, ChronoUnit.DAYS);
    }

    /**
     * jdk1.8日期的线程安全处理 给定日期加多少天
     *
     * @param date
     * @param days
     * @return
     * @author cuiqh
     */
    public static LocalDateTime minusDaysByLocal(LocalDateTime date, int days) {
        return date.minus(days, ChronoUnit.DAYS);
    }

    /**
     * jdk1.8日期的线程安全处理 获取当前时间
     *
     * @return 返回String
     * @author cuiqh
     */
    public static String getNow() {
        LocalDateTime arrivalDate = LocalDateTime.now();
        java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern(DATE_HH_MM_SS);
        return arrivalDate.format(format);
    }

    /**
     * jdk1.8日期的线程安全处理 获取当前时间
     *
     * @return 返回Date
     * @author cuiqh
     */
    public static Date getDateNow() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    // 获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    // 获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    // 获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    // 获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    // 获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    public static void main(String[] args) {
        /*
         * Date orderStartTime= stringDate("2018-01-12 20:20:00"); String ymd =
         * DateUtil.formatDate(orderStartTime, "yyyy-MM-dd"); Date latestOfDay =
         * DateUtil.getLatestOfDay(orderStartTime); String startStage="19:00:00"; String
         * endStage="19:30:00"; Date activityNewStartTime =
         * DateUtil.stringDate(DateUtil.formatDate(orderStartTime,
         * "yyyy-MM-dd")+" "+startStage); int
         * startStageHH=Integer.parseInt(startStage.split(":")[0]); int
         * endStageHH=Integer.parseInt(endStage.split(":")[0]);
         * if(startStageHH>endStageHH){//跨天了 if( orderStartTime.before(latestOfDay)
         * &&orderStartTime.after(activityNewStartTime)){ ymd =
         * DateUtil.formatDate(DateUtil.addDays(orderStartTime, 1), "yyyy-MM-dd"); } }
         * System.out.println(ymd+" "+endStage);
         */
        String s = DateUtil.formatTime(getBeginDayOfYear());
        String d = DateUtil.formatTime(getEndDayOfYear());
        System.out.println(s);
        System.out.println(d);

    }
}
