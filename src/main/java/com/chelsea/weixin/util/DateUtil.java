package com.chelsea.weixin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.support.CronSequenceGenerator;


/**
 * <b>System：</b>BSMS<br/>
 * <b>Title：</b>DateUtil.java<br/>
 * <b>Description：</b> 对功能点的描述<br/>
 * <b>@author： </b>sunlinlin_a<br/>
 * <b>@date：</b>2016年1月25日 下午4:40:26<br/>
 * <b>@version：</b> 1.0.0.0<br/>
 * <b>Copyright (c) 2016 ASPire Tech.</b>
 * 
 */
public class DateUtil {

    /**
     * 格式化时间常量年月日
     */
    public static final String FORMAT_DATE = "yyyyMMdd";

    /**
     * 格式化时间常量年-月-日
     */
    public static final String FORMAT_DATE_BAR = "yyyy-MM-dd";

    /**
     * 获取日期时间串格式
     */
    public static final String FORMAT_DATE_TIME = "yyyyMMddHHmmss";

    /**
     * 格式化时间常量年-月-日 时：分：秒
     */
    public static final String FORMAT_DATE_TIME_BAR = "yyyy-MM-dd HH:mm:ss";

    /**
     *  一周的天数
     */
    public static final int DAY_OF_WEEK = 7;
    
    /**
     * iso8061格式 yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String ISO_FORMAT_DATE_TIME_BAR = "yyyy-MM-dd'T'HH:mm:ss";//iso8061格式

    /**
     * 指定时间的日单元加number个单位
     * 
     * @param date
     *            日期Date类型
     * @param number
     *            指定单元日追加的日数
     * @return
     */
    public static Calendar addDay(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, number);
        return calendar;
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 返回格式为"yyyy-MM-dd HH:mm:ss"的时间字符串的Date形式
     * 
     * @param dataStr
     * @return
     */
    public static Date toDate(String dataStr) {
        return toDate(dataStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static int compareDate(String date1, String date2) throws ParseException {
        return compareDate(date1, date2, "yyyy-MM-dd");
    }

    public static int compareDate(String date1, String date2, String format) throws ParseException {
        SimpleDateFormat adf = new SimpleDateFormat(format);
        Date a = null;
        Date b = null;
        int i;
        a = adf.parse(date1);
        b = adf.parse(date2);

        if (a.before(b)) {
            i = -1;
        } else if (a.after(b)) {
            i = 1;
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * 求 endDate 比 startDate 大多少天
     * 
     * @param startDate
     * @param endDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static int diffDay(String startDate, String endDate, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date startD = sdf.parse(startDate);
        Date endD = sdf.parse(endDate);
        return (int) ((endD.getTime() - startD.getTime()) / 1000 / 3600 / 24);
    }

    /**
     * 返回指定格式的时间字符串的Date形式
     * 
     * @param dataStr
     * @param format
     * @return
     */
    public static Date toDate(String dataStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dataStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toDateString(String dateStr) {
        String tempDay = dateStr.substring(0, 10);
        String[] tempArray = tempDay.split("-");
        StringBuffer date = new StringBuffer();
        for (int i = 0; i < tempArray.length; i++) {
            date.append(tempArray[i]);
        }
        date.append(" " + dateStr.substring(11, dateStr.length()));
        return date.toString();
    }

    /**
     * 把日期字符串格式化，例如传进来dateStr为20071023,返回应该是2007-10-23
     * 
     * @param dateStr
     * @return String
     */
    public static String getDataFomatString(String dateStr) {
        if (dateStr == null) {
            return "";
        }
        if (dateStr.length() < 8) {
            return "";
        }
        if (dateStr.length() > 8)
            return dateStr;

        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
    }

    /**
     * 把日期字符串格式化，例如传进来dateStr为20071023121212,返回应该是2007-10-23 12:12:12
     * 
     * @param dateStr
     * @return String
     */
    public static String getDataFomatFullString(String dateStr) {
        String returnDate = "";
        if (dateStr == null) {
            return returnDate;
        }
        if (dateStr.length() == 8) {
            return returnDate = getDataFomatString(dateStr);
        }
        if (dateStr.length() > 8 && dateStr.length() < 14) {
            return returnDate = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
        }
        if (dateStr.length() >= 14) {
            return returnDate = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8)
                    + " " + dateStr.substring(8, 10) + ":" + dateStr.substring(10, 12) + ":"
                    + dateStr.substring(12, 14);
        }
        return returnDate;
    }

    /**
     * 把日期字符串格式化，例如传进来dateStr为2007-10-23,返回应该是20071023
     * 
     * @param dateStr
     * @return String
     */
    public static String getDataString(String dateStr) {
        if (dateStr == null) {
            return "";
        }
        String[] tempArray = dateStr.split("-");
        StringBuffer date = new StringBuffer();
        String temp;
        for (int i = 0; i < tempArray.length; i++) {
            temp = tempArray[i];
            if (temp.length() == 1) {
                date.append("0" + tempArray[i]);
            } else {
                date.append(tempArray[i]);
            }
        }
        // date.append(" "+dateStr.substring(11, dateStr.length()));
        return date.toString();
    }

    /**
     * yyyy-MM-dd HH:mm:ss转yyyyMMddHHmmss
     * 
     * @param dateStr
     * @return
     */
    public static String getDateFomatStr(String dateStr) {
        String result = "";
        if (null == dateStr) {
            return result;
        }
        if (dateStr.length() == 19) {
            String[] tempArray = dateStr.split(" ");
            String dateString = getDataString(tempArray[0]);
            String[] timeArray = tempArray[1].split(":");
            StringBuffer date = new StringBuffer();
            String temp;
            for (int i = 0; i < timeArray.length; i++) {
                temp = timeArray[i];
                if (temp.length() == 1) {
                    date.append("0" + timeArray[i]);
                } else {
                    date.append(timeArray[i]);
                }
            }
            result = dateString + date.toString();
        }

        return result;
    }

    /**
     * 取当前时间,格式:yyyyMMddHHmmss
     * 
     * @return yyyyMMddHHmmss
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat formatterLong = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatterLong.format(date);
    }

    /**
     * 取当前时间,格式:yyyy-MM-dd HH:mm:ss
     * 
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentFormateTime() {
        Date date = new Date();
        SimpleDateFormat standart_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return standart_formatter.format(date);
    }

    /**
     * @return 得到当前时间例如 20070714
     */
    public static String getCurrTimeDir() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());

        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 获取当前的系统时间,格式为:yyyy-MM-dd
     * 
     * @return
     */
    public static String getCurrTime() {
        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 
     * getCurrDateUseDefaultTime:获取系统当前日期时分秒采用000000. <br/>
     *
     * 作者： {xiaobing yang}
     * 
     * @return 得到当前时间 例如20160918000000
     */
    public static String getCurrDateUseDefaultTime() {
        String now = getcurTimeByFomat(DateUtil.FORMAT_DATE);
        now += "000000";
        return now;
    }

    /**
     * 
     * getCurrStandDateTimeUseDefaultTime:(返回当前时间，格式yyyy-MM-dd HH:mm:ss （时分秒
     * 00:00:00）). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * 作者： {xiaobing yang}
     * 
     * @return 当日的0点
     */
    public static String getCurrStandDateTimeUseDefaultTime() {
        String now = getcurTimeByFomat(DateUtil.FORMAT_DATE_BAR);
        now += " 00:00:00";
        return now;
    }

    public static String getDateStringFormat(Date date) {
        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String getCurrTime(String formatName) {
        SimpleDateFormat df = new java.text.SimpleDateFormat(formatName);
        return df.format(new Date());
    }

    /**
     * 得到当前详细日期、时间，如"2007-10-17 11:02:20".
     * 
     * @version 1.0
     */
    public static String curTime() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());

        // String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        // String DATE_FORMAT = "yyyyMMdd";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    public static String getcurTimeByFomat(String DATE_FORMAT) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    public static String getDateStamp(String datetime) {
        String[] datetimeTemp = datetime.split("-");
        String month = datetimeTemp[1];
        String day = datetimeTemp[2];
        if (month.length() == 1) {
            month = "0" + datetimeTemp[1];
        }
        if (day.length() == 1) {
            day = "0" + datetimeTemp[2];
        }
        return datetimeTemp[0] + "-" + month + "-" + day;
    }

    /**
     * 对入参进行处理， 返回指定类型的参数字符串
     * 
     * @param fullDateStr
     *            入参格式：yyyy-mm-dd HH24:MI:SS
     * @return dataToSecond 返回时间的格式：yyyymmdd HH24:MI
     */
    public static String getDateToSecond(String fullDateStr) {
        String dataToSecond = fullDateStr;

        // 截取时间格式到秒
        if (fullDateStr != null) {
            dataToSecond = fullDateStr.substring(0, 16);
            // dataToSecond=interceptStr.replaceAll("-", "");
        }

        return dataToSecond;

    }

    /**
     * 把字符串转化为java.util.Date
     * 
     * @param strDate
     * @param dateFormat
     * @throws ParseException
     */
    public static Date getStrDate(String strDate, String dateFormat) throws ParseException {
        Date date = null;
        if ((strDate != null) && (strDate.trim().length() > 0)) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            date = sdf.parse(strDate);
        }
        return date;
    }

    /**
     * 把字符串转化为java.util.Date
     * 
     * @param strDate
     * @param dateFormat
     * @throws ParseException
     */
    public static Date getStrDate(Date strDate, String dateFormat) throws ParseException {
        Date date = null;
        if (strDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            date = getStrDate(sdf.format(strDate), dateFormat);
        }
        return date;
    }

    /**
     * 把日期转化为日期字符串
     * 
     * @param date
     * @param dateFormat
     * @throws ParseException
     */
    public static String getDateStr(Date date, String dateFormat) {
        String strDate = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 把指定日期格式的日期字符串转换为指定的日期格式字符串
     * 
     *  dateStr
     *  dateStrFormat
     *            default=yyyymmddhh24mmss
     *  tranDateFormt
     *            default=yyyy-mm-dd hh24:mm:ss
     * @return
     */
    public static String getTranDateStr(String... args) {
        if (args.length > 3) {
            throw new IllegalArgumentException(
                    "最多支持三个参数，分别为dateStr(必须),dateStrFormat(可选，默认为yyyymmddhh24mmss),tranDateFormt(可选，默认为：yyyy-mm-dd hh24:mm:ss");
        }
        if (args[0] == null)
            return "";
        if (args.length == 1) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new SimpleDateFormat("yyyyMMddHHmmss").parse(args[0]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(args.length == 3){
            try {
                return new SimpleDateFormat(args[2])
                        .format(new SimpleDateFormat(args[1]).parse(args[0]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return args[0];
    }

    /**
     * 时间格式yyyyMMddHHmmss
     * 
     * @param timeBegin
     * @param timeEnd
     * @param seconds
     * @return true(超时)|false(未超时)
     */
    public static boolean compareDateOut(Date timeBegin, Date timeEnd, int seconds) {
        // Ĭ��false,timeBeginû�г�ʱtimeEnd
        boolean rs = false;
        if (timeBegin != null && timeEnd != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
            Calendar now = Calendar.getInstance(TimeZone.getDefault());
            now.setTime(timeBegin);
            sdf.setTimeZone(TimeZone.getDefault());
            // �����Ǻ���-����
            now.add(Calendar.SECOND, seconds * 60);
            // System.out.println(now.getTimeInMillis());
            // System.out.println(timeEnd.getTime());
            System.out.println(getDateStr(now.getTime(), FORMAT_DATE_TIME));
            System.out.println(getDateStr(timeEnd, FORMAT_DATE_TIME));
            if (now.getTime().before(timeEnd)) {
                rs = true;
            }
        }
        return rs;
    }

    // /**
    // * SQL日期类型到java日期类型的转换
    // * @param times
    // * @return
    // */
    // public static Date getDateFromSqlDate(Object time){
    // if(time == null) return null;
    // if(time instanceof java.sql.Timestamp){
    // return new Date(((java.sql.Timestamp)time).getTime());
    // }else if(time instanceof java.sql.Date){
    // return new Date(((java.sql.Date)time).getTime());
    // }else if(time instanceof oracle.sql.TIMESTAMP){
    // try {
    // return new Date(((oracle.sql.TIMESTAMP)
    // time).timestampValue().getTime());
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }
    // return null;
    // }

    public static Date getDefaultQueryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getDefaultQueryReleaseBeginDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getDefaultQueryReleaseEndDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 得到当前日期的前一天,如果是当前日期是本月1号，取当天
     * 
     * @return
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_MONTH) > 1) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    /**
     * 日期格式2014/1/1、2014年1月1日转换为20140101
     * 
     * @return
     */
    public static String dateConversion(String dateStr) {
        if (dateStr.contains("年"))
            dateStr = dateStr.replaceAll("年", "/");
        if (dateStr.contains("月"))
            dateStr = dateStr.replaceAll("月", "/");
        if (dateStr.contains("日"))
            dateStr = dateStr.replaceAll("日", "/");
        String tempDay = dateStr.substring(0, dateStr.length());
        String[] tempArray = new String[] {};
        StringBuffer date = new StringBuffer();
        if (dateStr.contains("/"))
            tempArray = tempDay.split("/");
        else
            date.append(dateStr);
        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i].length() == 1) {
                date.append("0" + tempArray[i]);
                continue;
            } else {
                date.append(tempArray[i]);
            }
        }
        return date.toString();
    }

    /**
     * 获取日期date的num天数的日期（num为负数是之前、num为正数是之后、num为零是当前日期）
     * 
     * @param date
     * @param num
     * @return
     */
    public static Date getBeforeAndAfterTheDate(Date date, int num) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, num);
        return cd.getTime();
    }

    /**
     * 校验日期是否合法
     * 
     * @Description 函数说明
     * @param format
     * @param dateString
     * @return
     * @since<b> modification history</b><br/>
     *           --------------------<br/>
     *           author: xiaozongjia 2016年7月6日 下午1:29:08 修改说明<br/>
     */
    public static boolean validateDateFormat(String format, String dateString) {
        if (format == null || dateString == null) {
            return false;
        } else if (format.length() != dateString.length()) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(dateString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param dateStr
     *            输入日期
     * @return yyyyMMddHHmmss转化成yyyy-MM-dd
     */
    public static String getFotmatString(String dateStr) {
        if (null == dateStr || dateStr.trim().length() < 1)
            return "";
        try {
            Date d = DateUtil.toDate(dateStr, FORMAT_DATE_TIME);
            return DateUtil.getDateStr(d, FORMAT_DATE_BAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @param dateStr
     * @return yyyyMMddHHmmss转化成yyyyMMdd
     */
    public static String getFormateDate(String dateStr) {
        if (null == dateStr || dateStr.trim().length() < 1) {
            return "";
        }
        try {
            Date d = DateUtil.toDate(dateStr, FORMAT_DATE_TIME);
            return DateUtil.getDateStr(d, FORMAT_DATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 
     * @param dateStr
     * @return yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
     */
    public static String getFormatString(String dateStr) {
        if (null == dateStr || dateStr.trim().length() < 1)
            return "";
        try {
            return dateStr.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转化为yyyyMMddHHmmss
     * 
     * @Description 函数说明
     * @param dateStr
     * @return
     * @since<b> modification history</b><br/>
     *           --------------------<br/>
     *           author: zhouxiaomin_a 2016年8月3日 上午11:51:59 修改说明<br/>
     */
    public static String formatString(String dateStr) {
        if (null == dateStr || dateStr.trim().length() < 1)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME_BAR);
        sdf.setLenient(false);
        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_DATE_TIME);
        try {
            Date d = sdf.parse(dateStr);
            return sdf1.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * getDisableTime:(根据生效时间计算失效时间). <br/>
     * TODO(注意时空地域对时间造成的差异).<br/>
     * TODO(传入的生效时间是String类型).<br/>
     *
     * 作者： liaofei
     * 
     * @param ableTime
     * @param months
     * @return disableTime
     */
    public static String getDisableTime(String ableTime, Integer months) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(ableTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, months);
        String disableTime = sdf.format(calendar.getTime());
        return disableTime;
    }

    /**
     * getCalendarTimeSet:(根据传入的字符类型时间获取对应的calender). <br/>
     * TODO(传入的time为空或空字符串，获取当前时间对应的calender).<br/>
     *
     * 作者： liaofei
     * 
     * @param time
     * @return calendar
     */
    public static Calendar getCalendarTimeSet(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
    
    /** 
     * 获取过去第几天的日期 
     * 
     * @param past 
     * @param formatStr
     * @return 
     */  
    public static String getPastDate(int past, String formatStr) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
        Date today = calendar.getTime();  
        SimpleDateFormat format = new SimpleDateFormat(formatStr);  
        String result = format.format(today);  
        return result;  
    }
    
    /**
     * 
     * @Description:获取上一周的日期，从周一到周日
     * @return
     * @return List<Date>
     * @author:baojun
     * @date:2017年3月8日 下午2:44:48
     */
    public static List<Date> getLastWeekList() {

    	List<Date> dateList = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        int n = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (n == 0) {
            n = 7;
        }
        cal.add(Calendar.DATE, -(7 + (n - 1)));// 上周一的日期
        Date monday = cal.getTime();
        dateList.add(monday);

        cal.add(Calendar.DATE, 1);
        Date tuesday = cal.getTime();
        dateList.add(tuesday);

        cal.add(Calendar.DATE, 1);
        Date wednesday = cal.getTime();
        dateList.add(wednesday);

        cal.add(Calendar.DATE, 1);
        Date thursday = cal.getTime();
        dateList.add(thursday);

        cal.add(Calendar.DATE, 1);
        Date friday = cal.getTime();
        dateList.add(friday);

        cal.add(Calendar.DATE, 1);
        Date saturday = cal.getTime();
        dateList.add(saturday);

        cal.add(Calendar.DATE, 1);
        Date sunday = cal.getTime();
        dateList.add(sunday);
        return dateList;
    }
    /**
     * 获取今天星期几,返回星期几的数字，7为星期天
     */
    public static int getDayOfToday() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayOfWeek = 7;
        }else{
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayOfWeek;
    }

    /**
     * 获得上上星期一和星期天的日期，格式YYYY-mm-dd。
     * @return 数组，[0]为星期一的日期，[1]为星期二的日期。
     */
    public static String[] getWeekBeforeLast() {
        int dayOfWeek = getDayOfToday();
        Date mondayDate = getBeforeAndAfterTheDate(new Date(), -(dayOfWeek + 2 * DAY_OF_WEEK - 1));
        Date sundayDate = getBeforeAndAfterTheDate(new Date(), -(dayOfWeek + DAY_OF_WEEK));
        String monday = getDateStringFormat(mondayDate);
        String sunday = getDateStringFormat(sundayDate);
        String string[] = {monday,sunday};
        return string;
    }

    /**
     * 获得上星期一和星期天的日期，格式YYYY-mm-dd。
     * @return 数组，[0]为星期一的日期，[1]为星期二的日期。
     */
    public static String[] getLastWeek() {
        int dayOfWeek = getDayOfToday();
        Date mondayDate = getBeforeAndAfterTheDate(new Date(), -(dayOfWeek + DAY_OF_WEEK - 1));
        Date sundayDate = getBeforeAndAfterTheDate(new Date(), -dayOfWeek);
        String monday = getDateStringFormat(mondayDate);
        String sunday = getDateStringFormat(sundayDate);
        String string[] = {monday,sunday};
        return string;
    }

    /**
     * 获得上个星期日的日期。
     * @return 上个星期日的日期，格式YYYY-mm-dd。
     */
    public static String getLastSunday() {
        int dayOfWeek = getDayOfToday();
        Date lastSundayDate = getBeforeAndAfterTheDate(new Date(), -dayOfWeek);
        String lastSunday = getDateStringFormat(lastSundayDate);
        return lastSunday;
    }
    
    /**
	 * @Description:根据cron时间表达式计算下一次时间
	 * @param cronExpression
	 * @return
	 * @return Date
	 * @author:Administrator
	 * @date:2017年3月13日 上午11:03:28
	 */
	public static Date getNextDateByCronExpression(Date date, String cronExpression){
		CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cronExpression);  
		Date nextDate = cronSequenceGenerator.next(date);
		return nextDate;
	}
	
	/**
	 * @Description:根据新增或者减去的秒获取新的时间
	 * @param date
	 * @param second
	 * @return
	 * @return Date
	 * @author:baojun
	 * @date:2017年3月13日 上午11:12:21
	 */
	public static Date dateAddOrMinusSecond(Date date, Integer second){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		Date newDate = c.getTime();
		return newDate;
	}
	
	/**
	 * 
	 * @Description: 根据长整型转换成日期格式
	 * @param time
	 * @return
	 * @return Date
	 * @author:baojun
	 * @date:2017年3月13日 上午11:20:56
	 */
	public static Date getDateByLong(Long time){
		Date date = null;
		try {
			String dateStr = String.valueOf(time);
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
			date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return date;
	}
	
	/**
	 * 获取iso格式时间字符串, yyyy-MM-dd'T'HH:mm:ss
	* formatISO:(这里用一句话描述这个方法的作用). <br/>
	* TODO(这里描述这个方法适用条件 – 可选).<br/>
	* TODO(这里描述这个方法的注意事项 – 可选).<br/>
	*
	* 作者： chenyibing
	* @param date
	* @return
	 */
	public static String formatISOByDate(Date date){
	    String formatStr=DateFormatUtils.format(date, ISO_FORMAT_DATE_TIME_BAR);
        return formatStr;
	}
    
}