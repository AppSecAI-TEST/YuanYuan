package xyz.zimuju.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转化工具 date转为时间戳 时间戳转date 互相与String的转换
 * 所有出现的String time 格式都必须为(yyyy-MM-dd HH:mm:ss)，否则出错
 */
public class DateUtil {

    /**
     * String(yyyy-MM-dd HH:mm:ss) 转 Date
     *
     * @param time
     * @return
     * @throws ParseException
     */
    // String date = "2010/05/04 12:34:23";
    public static Date StringToDate(String time) throws ParseException {

        Date date = new Date();
        // 注意format的格式要与日期String的格式相匹配
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = dateFormat.parse(time);
            //System.out.println(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Date转为String(yyyy-MM-dd HH:mm:ss)
     *
     * @param time
     * @return
     */
    public static String DateToString(Date time) {
        String dateStr = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            dateStr = dateFormat.format(time);
            //System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * Date转为String
     *
     * @param time
     * @param format
     * @return
     */
    public static String DateToString(Date time, String format) {
        String dateStr = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            dateStr = dateFormat.format(time);
            //System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
     *
     * @param time
     * @return
     */
    public static Integer StringToTimestamp(String time) {

        int times = 0;
        try {
            times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (times == 0) {
            //System.out.println("String转10位时间戳失败");
        }
        return times;

    }

    /**
     * 10位int型的时间戳转换为String(yyyy-MM-dd HH:mm)
     *
     * @param time
     * @return
     */
    public static String timestampToString(Integer time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            //方法一  
            tsStr = dateFormat.format(ts);
            //System.out.println(tsStr);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 10位int型的时间戳转换为String
     *
     * @param time
     * @param format 自定义format
     * @return
     */
    public static String timestampToString(long time, String format) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        Timestamp ts = new Timestamp(time);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            //方法一
            tsStr = dateFormat.format(ts);
            //System.out.println(tsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 10位时间戳转Date
     *
     * @param time
     * @return
     */
    public static Date TimestampToDate(Integer time) {
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
            ////System.out.println(date);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Date类型转换为10位时间戳
     *
     * @param time
     * @return
     */
    public static Integer DateToTimestamp(Date time) {
        Timestamp ts = new Timestamp(time.getTime());

        return (int) ((ts.getTime()) / 1000);
    }

    /**
     * 剩余的时间
     *
     * @param nowDate
     * @param addTime
     * @return
     */
    public static String releaseTime(Integer nowDate, Integer addTime) {
        Integer temp = nowDate - addTime;

        if ((temp / (86400 * 30)) > 0) {
            temp = temp / (86400 * 30);
            return " " + temp + "个月前";
        }

        if ((temp / 86400) > 0) {
            temp = temp / 86400;
            return " " + temp + "天前";
        }

        if ((temp / 3600) > 0) {
            temp = temp / 3600;
            return " " + temp + "小时前";
        }

        if ((temp / 60) > 0) {
            temp = temp / 60;
            return " " + temp + "分钟前";
        }

        return " 刚刚";
    }

    public static void main(String[] args) {
        //System.out.println(StringToTimestamp("2011-05-09 11:49:45"));
        //System.out.println(DateToTimestamp(TimestampToDate(StringToTimestamp("2011-05-09 11:49:45"))));
    }

    /**
     * 计算时间差是否大于24小时
     *
     * @param lastDate
     * @param currentDate
     * @return
     */
    public static boolean isThanOneDay(long lastDate, long currentDate) {
        long diff = currentDate - lastDate;
        long baseTime = 86400000;
        return diff / baseTime >= 1;
    }

    /**
     * 获取当前系统日期
     *
     * @return
     */
    public static String getCurrentSysDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        String date = sdf.format(new Date());
        return date;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        return date;
    }


    public static Integer getCurrentSysDateToInteger() {
        return DateToTimestamp(new Date());
    }
}
