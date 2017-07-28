package xyz.zimuju.common.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**

 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {
    /**
     * 系统计时开始时间
     */
    public static final int[] SYSTEM_START_DATE = {1970, 0, 1, 0, 0, 0};
    public static final int LEVEL_YEAR = 0;
    public static final int LEVEL_MONTH = 1;
    public static final int LEVEL_DAY = 2;
    public static final int LEVEL_HOUR = 3;
    public static final int LEVEL_MINUTE = 4;
    public static final int LEVEL_SECOND = 5;
    public static final int[] LEVELS = {
            LEVEL_YEAR,
            LEVEL_MONTH,
            LEVEL_DAY,
            LEVEL_HOUR,
            LEVEL_MINUTE,
            LEVEL_SECOND,
    };
    public static final String NAME_YEAR = "年";
    public static final String NAME_MONTH = "月";
    public static final String NAME_DAY = "日";
    public static final String NAME_HOUR = "时";
    public static final String NAME_MINUTE = "分";
    public static final String NAME_SECOND = "秒";
    public static final String[] LEVEL_NAMES = {
            NAME_YEAR,
            NAME_MONTH,
            NAME_DAY,
            NAME_HOUR,
            NAME_MINUTE,
            NAME_SECOND,
    };
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY_OF_MONTH = 2;
    public static final int HOUR_OF_DAY = 3;
    public static final int MINUTE = 4;
    public static final int SECOND = 5;
    public static final int[] MIN_TIME_DETAILS = {0, 0, 0};
    public static final int[] MAX_TIME_DETAILS = {23, 59, 59};
    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;
    /**
     * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
     * 格式的意义如下： 日期和时间模式 <br>
     * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * </p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>时区 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>时区 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     * 注意SimpleDateFormat不是线程安全的
     */
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat YMD_SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    // yyyy.MM.dd
    public static final SimpleDateFormat YMD_DOT_SDF = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    private static final String TAG = "TimeUtil";

    private TimeUtils() {/* 不能实例化**/}

    /**
     * @param level
     * @return
     */
    public static boolean isContainLevel(int level) {
        for (int existLevel : LEVELS) {
            if (level == existLevel) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param level
     * @return
     */
    public static String getNameByLevel(int level) {
        return isContainLevel(level) ? LEVEL_NAMES[level - LEVEL_YEAR] : "";
    }

    /**
     * 获取时间,hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        return date == null ? "" : getTime(date.getTime());
    }

    /**
     * 获取时间,hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTime(long date) {
        return new SimpleDateFormat("hh:mm:ss").format(new Date(date));
    }

    /**
     * 获取完整时间
     *
     * @param date
     * @return
     */
    public static String getWholeTime(Date date) {
        return date == null ? "" : getWholeTime(date.getTime());
    }

    /**
     * 获取完整时间 yyyy年mm月dd日hh时mm分
     *
     * @param date
     * @return
     */
    public static String getWholeTime(long date) {
        int[] details = TimeUtils.getWholeDetail(date);

        return details[0] + "年" + details[1] + "月"
                + details[2] + "日  " + details[3] + "时" + details[4] + "分";
    }

    /**
     * 将long型时间长度数据转化为文字形式时间长度
     * 去掉了1970年1月1日8时的初始值
     *
     * @param duration
     * @return
     */
    public static String getSmartTime(long duration) {

        int[] smartDetail = getWholeDetail(duration);

        String smartTime = "";

        if (smartDetail[5] > 0) {
            smartTime = String.valueOf(smartDetail[5]) + "秒" + smartTime;
        }
        if (smartDetail[4] > 0) {
            smartTime = String.valueOf(smartDetail[4]) + "分" + smartTime;
        }
        if (smartDetail[3] > 8) {
            smartTime = String.valueOf(smartDetail[3]) + "时" + String.valueOf(smartDetail[4]) + "分";
        }
        if (smartDetail[2] > 1) {
            smartTime = String.valueOf(smartDetail[2]) + "天" + String.valueOf(smartDetail[3]) + "时";
        }
        if (smartDetail[1] > 1) {
            smartTime = String.valueOf(smartDetail[1]) + "月" + String.valueOf(smartDetail[2]) + "天";
        }
        if (smartDetail[0] > 1970) {
            smartTime = String.valueOf(smartDetail[0]) + "年" + smartTime;
        }

        return smartTime;
    }

    public static String getSmartDate(Date date) {
        return date == null ? "" : getSmartDate(date.getTime());
    }

    /**
     * 智能时间显示，12:30,昨天，前天...
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSmartDate(long date) {

        int[] nowDetails = getWholeDetail(System.currentTimeMillis());
        int[] smartDetail = getWholeDetail(date);

        String smartDate = "";

        if (nowDetails[0] == smartDetail[0]) {//this year
            if (nowDetails[1] == smartDetail[1]) {//this month
                String time = " " + StringUtils.getString(new SimpleDateFormat("HH:mm").format(date));

                long day = nowDetails[2] - smartDetail[2];//between/(24*3600);
                if (day >= 3) {//fomer day
                    smartDate = String.valueOf(smartDetail[2]) + "日" + time;
                } else if (day >= 2) {//fomer day
                    smartDate = "前天" + time;
                } else if (day >= 1) {//fomer day
                    smartDate = "昨天" + time;
                } else if (day >= 0) {//today
                    if (0 == (nowDetails[HOUR_OF_DAY] - smartDetail[HOUR_OF_DAY])) {
                        long minute = nowDetails[MINUTE] - smartDetail[MINUTE];
                        if (minute < 1) {
                            smartDate = "刚刚";
                        } else if (minute < 31) {
                            smartDate = minute + "分钟前";
                        } else {
                            smartDate = time;
                        }
                    } else {
                        smartDate = time;
                    }
                } else if (day >= -1) {//tomorrow
                    smartDate = "明天" + time;
                } else if (day >= -2) {//the day after tomorrow
                    smartDate = "后天" + time;
                } else {
                    smartDate = String.valueOf(smartDetail[2]) + "日" + time;
                }
            } else {//!!!
                smartDate = String.valueOf(smartDetail[1]) + "月" + String.valueOf(smartDetail[2]) + "日";
            }
        } else {//!!!
            smartDate = String.valueOf(smartDetail[0]) + "年" + String.valueOf(smartDetail[1]) + "月";
        }

        //		System.out.println("返回智能日期" + smartDate);
        return smartDate;
    }

    /**
     * 获取日期 年，月， 日 对应值
     *
     * @param date
     * @return
     */
    public static int[] getDateDetail(Date date) {
        return date == null ? null : getDateDetail(date.getTime());
    }

    /**
     * 获取日期 年，月， 日 对应值
     *
     * @param time
     * @return
     */
    public static int[] getDateDetail(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return new int[]{
                mCalendar.get(Calendar.YEAR),//0
                mCalendar.get(Calendar.MONTH) + 1,//1
                mCalendar.get(Calendar.DAY_OF_MONTH),//2
        };
    }

    /**
     * 获取日期  时， 分， 秒 对应值
     *
     * @param time
     * @return
     */
    public static int[] getTimeDetail(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return new int[]{
                mCalendar.get(Calendar.HOUR_OF_DAY),//3
                mCalendar.get(Calendar.MINUTE),//4
                mCalendar.get(Calendar.SECOND)//5
        };
    }

    /**
     * 获取日期 年，月， 日， 时， 分， 秒 对应值
     *
     * @param time
     * @return
     */
    public static int[] getWholeDetail(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return new int[]{
                mCalendar.get(Calendar.YEAR),//0
                mCalendar.get(Calendar.MONTH) + 1,//1
                mCalendar.get(Calendar.DAY_OF_MONTH),//2
                mCalendar.get(Calendar.HOUR_OF_DAY),//3
                mCalendar.get(Calendar.MINUTE),//4
                mCalendar.get(Calendar.SECOND)//5
        };
    }

    /**
     * 获取两个时间的时间间隔
     *
     * @param sdf
     * @param dateLong0
     * @param dateLong1
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getBetween(SimpleDateFormat sdf, long dateLong0, long dateLong1) {
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        }
        Date date0;
        Date date1;
        long between = 0;
        try {

            date0 = sdf.parse(sdf.format(new Date(dateLong0)));
            date1 = sdf.parse(sdf.format(new Date(dateLong1)));
            between = (date0.getTime() - date1.getTime()) / 1000;//除以1000是为了转换成秒
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //		System.out.println("between=" + String.valueOf(between));
        return between;
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     */
    public static int getAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }
        if (birthday.getYear() > getDateDetail(System.currentTimeMillis())[0]) {
            birthday.setYear(birthday.getYear() - TimeUtils.SYSTEM_START_DATE[0]);
        }

        return getAge(new int[]{birthday.getYear(), birthday.getMonth(), birthday.getDay()});
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     */
    public static int getAge(long birthday) {
        return getAge(getDateDetail(birthday));
    }

    /**
     * 根据生日获取年龄
     *
     * @return
     */
    public static int getAge(int[] birthdayDetail) {
        if (birthdayDetail == null || birthdayDetail.length < 3) {
            return 0;
        }

        int[] nowDetails = getDateDetail(System.currentTimeMillis());

        int age = nowDetails[0] - birthdayDetail[0];

        if (nowDetails[1] < birthdayDetail[1]) {
            age = age - 1;
        } else if (nowDetails[1] == birthdayDetail[1]) {
            if (nowDetails[2] < birthdayDetail[2]) {
                age = age - 1;
            }
        }

        return age;
    }

    /**
     * 根据生日计算星座
     *
     * @param birthday
     * @return constellation
     */
    public static String getStar(Date birthday) {
        Calendar c = Calendar.getInstance();
        c.setTime(birthday);
        int month = c.get(Calendar.MONTH);                // 月份从0 ~ 11
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int[] DayArr = {19, 18, 20, 19, 20, 21, 22, 22, 22, 23, 22, 21};
        String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
        if (dayOfMonth > DayArr[month]) {
            month = month + 1;
            if (month == 12) {
                month = 0;
            }
        }
        return starArr[month];
    }

    /**
     * 获取生日,不带年份
     *
     * @param date
     * @return
     */
    public static String getBirthday(Date date) {
        return getBirthday(date, false);
    }

    /**
     * 获取生日
     *
     * @param date
     * @param needYear
     * @return
     */
    public static String getBirthday(Date date, boolean needYear) {
        return date == null ? "" : getBirthday(date.getTime(), needYear);
    }

    /**
     * 获取生日,不带年份
     *
     * @param date
     * @return
     */
    public static String getBirthday(long date) {
        return getBirthday(date, false);
    }

    /**
     * 获取生日
     *
     * @param date
     * @param needYear
     * @return
     */
    public static String getBirthday(long date, boolean needYear) {
        int[] details = TimeUtils.getWholeDetail(date);

        if (needYear) {
            return details[0] + "年" + details[1] + "月" + details[2] + "日";
        }
        return details[1] + "月" + details[2] + "日";
    }

    /******************** 时间相关常量 ********************/

    /**
     * 获取智能生日
     *
     * @return
     */
    public static String getSmartBirthday(int[] birthdayDetails) {
        if (birthdayDetails == null || birthdayDetails.length < 3) {
            return "";
        }
        if (birthdayDetails[0] > TimeUtils.SYSTEM_START_DATE[0]) {
            birthdayDetails[0] = birthdayDetails[0] - TimeUtils.SYSTEM_START_DATE[0];
        }
        return getSmartBirthday(new Date(birthdayDetails[0], birthdayDetails[1], birthdayDetails[2]));
    }

    /**
     * @param birthday
     * @return
     */
    public static String getSmartBirthday(Date birthday) {
        if (birthday == null) {
            return "";
        }
        if (birthday.getYear() > getDateDetail(System.currentTimeMillis())[0]) {
            birthday.setYear(birthday.getYear() - TimeUtils.SYSTEM_START_DATE[0]);
        }

        return getSmartBirthday(birthday.getTime(), false) + " " + (TimeUtils
                .getDateDetail(System.currentTimeMillis())[0] - birthday.getYear()) + "岁";
    }

    /**
     * 获取智能生日
     *
     * @param birthday
     * @param needYear
     * @return
     */
    public static String getSmartBirthday(long birthday, boolean needYear) {
        int[] birthdayDetails = getDateDetail(birthday);
        int[] nowDetails = getDateDetail(System.currentTimeMillis());

        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.set(birthdayDetails[0], birthdayDetails[1], birthdayDetails[2]);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(nowDetails[0], nowDetails[1], nowDetails[2]);

        int days = birthdayCalendar.get(Calendar.DAY_OF_YEAR) - nowCalendar.get(Calendar.DAY_OF_YEAR);
        if (days < 8) {
            if (days >= 3) {
                return days + "天后";
            }
            if (days >= 2) {
                return TimeUtils.Day.NAME_THE_DAY_AFTER_TOMORROW;
            }
            if (days >= 1) {
                return TimeUtils.Day.NAME_TOMORROW;
            }
            if (days >= 0) {
                return TimeUtils.Day.NAME_TODAY;
            }
        }

        if (needYear) {
            return birthdayDetails[0] + "年" + birthdayDetails[1] + "月" + birthdayDetails[2] + "日";
        }
        return birthdayDetails[1] + "月" + birthdayDetails[2] + "日";
    }

    public static boolean fomerIsEqualOrBigger(int[] fomer, int[] current) {
        return fomer == current || fomerIsBigger(fomer, current);
    }

    public static boolean fomerIsBigger(int[] fomer, int[] current) {
        if (fomer == null || current == null) {
            Log.e(TAG, "fomerIsBigger  fomer == null || current == null" +
                    " >>  return false;");
            return false;
        }
        int compareLength = fomer.length < current.length ? fomer.length : current.length;

        for (int i = 0; i < compareLength; i++) {
            if (fomer[i] < current[i]) {
                return false;
            }
            if (fomer[i] > current[i]) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断现在是否属于一段时间,不包含端点
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isNowInTimeArea(int[] start, int[] end) {
        return isInTimeArea(getTimeDetail(System.currentTimeMillis()), start, end);
    }

    /**
     * 判断一个时间是否属于一段时间,不包含端点
     * (start, end)可跨越0:00,即start < end也行
     *
     * @param time
     * @param start
     * @param end
     * @return
     */
    public static boolean isInTimeArea(int[] time, int[] start, int[] end) {
        if (fomerIsEqualOrBigger(end, start)) {
            return fomerIsEqualOrBigger(time, start) && fomerIsEqualOrBigger(end, time);
        }

        if (fomerIsEqualOrBigger(time, start) && fomerIsEqualOrBigger(MAX_TIME_DETAILS, time)) {
            return true;
        }
        return fomerIsEqualOrBigger(time, MIN_TIME_DETAILS) && fomerIsEqualOrBigger(end, time);

    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    public static String milliseconds2StringYMD(long milliseconds) {
        return milliseconds2String(milliseconds, YMD_SDF);
    }

    public static String milliseconds2StringYMDDot(long milliseconds) {
        return milliseconds2String(milliseconds, YMD_DOT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                     <li>{@link TimeUnit#SEC }: 秒</li>
     *                     <li>{@link TimeUnit#MIN }: 分</li>
     *                     <li>{@link TimeUnit#HOUR}: 小时</li>
     *                     <li>{@link TimeUnit#DAY }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    private static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit, SimpleDateFormat format) {
        return milliseconds2Unit(Math.abs(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2都为Date类型</p>
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getIntervalTime(Date time0, Date time1, TimeUnit unit) {
        return milliseconds2Unit(Math.abs(date2Milliseconds(time1)
                - date2Milliseconds(time0)), unit);
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}:毫秒</li>
     *             <li>{@link TimeUnit#SEC }:秒</li>
     *             <li>{@link TimeUnit#MIN }:分</li>
     *             <li>{@link TimeUnit#HOUR}:小时</li>
     *             <li>{@link TimeUnit#DAY }:天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time为Date类型</p>
     *
     * @param time Date类型时间
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *             <li>{@link TimeUnit#SEC }: 秒</li>
     *             <li>{@link TimeUnit#MIN }: 分</li>
     *             <li>{@link TimeUnit#HOUR}: 小时</li>
     *             <li>{@link TimeUnit#DAY }: 天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getIntervalByNow(Date time, TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time));
    }

    /**
     * 获取星期
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 星期
     */
    public static String getWeek(String time, SimpleDateFormat format) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, format));
    }

    /**
     * 获取星期
     *
     * @param time Date类型时间
     * @return 星期
     */
    public static String getWeek(Date time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(time);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        Date date = string2Date(time);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        Date date = string2Date(time);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        Date date = string2Date(time);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static String converTime(long time) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - time / 1000;// ������ʱ���������
        String timeStr = null;
        if (timeGap > 3 * 24 * 60 * 60) {
            timeStr = getDayTime(time) + " " + getMinTime(time);
        } else if (timeGap > 24 * 2 * 60 * 60) {// 2�����Ͼͷ��ر�׼ʱ��
            timeStr = "ǰ�� " + getMinTime(time);
        } else if (timeGap > 24 * 60 * 60) {// 1��-2��
            timeStr = timeGap / (24 * 60 * 60) + "���� " + getMinTime(time);
        } else if (timeGap > 60 * 60) {// 1Сʱ-24Сʱ
            timeStr = timeGap / (60 * 60) + "���� " + getMinTime(time);
        } else if (timeGap > 60) {// 1����-59����
            timeStr = timeGap / 60 + "���� " + getMinTime(time);
        } else {// 1����-59����
            timeStr = "���� " + getMinTime(time);
        }
        return timeStr;
    }

    public static String getChatTime(long time) {
        return getMinTime(time);
    }

    public static String getPrefix(long time) {
        long currentSeconds = System.currentTimeMillis();
        long timeGap = currentSeconds - time;// ������ʱ���
        String timeStr = null;
        if (timeGap > 24 * 3 * 60 * 60 * 1000) {
            timeStr = getDayTime(time) + " " + getMinTime(time);
        } else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
            timeStr = "ǰ�� " + getMinTime(time);
        } else if (timeGap > 24 * 60 * 60 * 1000) {
            timeStr = "���� " + getMinTime(time);
        } else {
            timeStr = "���� " + getMinTime(time);
        }
        return timeStr;
    }

    public static String getDayTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        return format.format(new Date(time));
    }

    public static String getMinTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getShowTime(long time) {
        long curLongDate = System.currentTimeMillis();//CommonUtils.getServerTime();

        Calendar curc = Calendar.getInstance();
        curc.setTimeInMillis(curLongDate);

        Calendar chatc = Calendar.getInstance();
        chatc.setTimeInMillis(time);

        int curY = curc.get(Calendar.YEAR);

        int chatY = chatc.get(Calendar.YEAR);
        int chatM = chatc.get(Calendar.MONTH) + 1;
        int chatD = chatc.get(Calendar.DAY_OF_MONTH);
        int chatH = chatc.get(Calendar.HOUR_OF_DAY);
        int chatMM = chatc.get(Calendar.MINUTE);

        String chatHM = chatH + ":" + addZero(chatMM);

        int twoDifDay = getTwoDayDif(curc, chatc);
        if (twoDifDay == 0) { //一天以内
            if (chatH < 12) {
                return "上午 " + chatHM;
            } else {
                return "下午 " + chatHM;
            }
        } else if (twoDifDay == 1) {
            return "昨天 " + chatHM;
        } else if (twoDifDay == 2) {
            return "前天 " + chatHM;
        } else if (curY == chatY) {
            return addZero(chatM) + "-" + addZero(chatD) + " " + chatHM;
        } else {
            String sy = chatY + "";
            sy = sy.substring(2, 4);
            return sy + "-" + addZero(chatM) + "-" + addZero(chatD) + " " + chatHM;
        }
    }

    public static int getTwoDayDif(Calendar c1, Calendar c2) {
        int y1 = c1.get(Calendar.YEAR);
        int d1 = c1.get(Calendar.DAY_OF_YEAR);

        int y2 = c2.get(Calendar.YEAR);
        int d2 = c2.get(Calendar.DAY_OF_YEAR);

        if (y1 == y2) {
            return d1 - d2;
        } else if (y1 > y2) {
            int ydif = y1 - y2;
            int totaldif = 0;
            for (int i = 0; i < ydif; i++) {
                int yearTotalDay = 365;
                if (isLeapYear(y2 + i)) {
                    yearTotalDay = 366;
                }
                totaldif += yearTotalDay;
            }

            return d1 + totaldif - d2;
        } else {
            return -1;
        }
    }

    public static String addZero(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return i + "";
    }

    public static String getPracticeReportTime(long time) {
        String str = "";
        time = time / 1000;
        long min = time / 60;
        if (min > 0) {
            long sec = time % 60;
            if (min > 9) {
                if (sec > 9) {
                    str = min + "'" + sec + "''";
                } else {
                    str = min + "'0" + sec + "''";
                }
            } else {
                if (sec > 9) {
                    str = "0" + min + "'" + sec + "''";
                } else {
                    str = "0" + min + "'0" + sec + "''";
                }
            }
        } else {
            long sec = time % 60;
            if (sec > 9) {
                str = "00'" + sec + "''";
            } else {
                str = "00'0" + sec + "''";
            }
        }

        return str;
    }

    public static float getHour(long millisecond) {
        double second = millisecond / 1000;
        BigDecimal b = new BigDecimal(second / 60 / 60);
        float hour = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return hour;
    }

    /**
     * 毫秒转分钟
     *
     * @return
     */
    public static long getMilliToMin(long millisecond) {
        long minute = 0;
        if (millisecond <= 0) {
            return minute;
        }
        long second = millisecond / 1000;
        if (second <= 0) {
            return minute;
        }
        minute = second / 60;
        return minute;
    }

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    public static class Day {

        public static final String NAME_THE_DAY_BEFORE_YESTERDAY = "前天";
        public static final String NAME_YESTERDAY = "昨天";
        public static final String NAME_TODAY = "今天";
        public static final String NAME_TOMORROW = "明天";
        public static final String NAME_THE_DAY_AFTER_TOMORROW = "后天";


        public static final int TYPE_SUNDAY = 0;
        public static final int TYPE_MONDAY = 1;
        public static final int TYPE_TUESDAY = 2;
        public static final int TYPE_WEDNESDAY = 3;
        public static final int TYPE_THURSDAY = 4;
        public static final int TYPE_FRIDAY = 5;
        public static final int TYPE_SATURDAY = 6;
        public static final int[] DAY_OF_WEEK_TYPES = {
                TYPE_SUNDAY,
                TYPE_MONDAY,
                TYPE_TUESDAY,
                TYPE_WEDNESDAY,
                TYPE_THURSDAY,
                TYPE_FRIDAY,
                TYPE_SATURDAY,
        };

        public static final String NAME_SUNDAY = "日";
        public static final String NAME_MONDAY = "一";
        public static final String NAME_TUESDAY = "二";
        public static final String NAME_WEDNESDAY = "三";
        public static final String NAME_THURSDAY = "四";
        public static final String NAME_FRIDAY = "五";
        public static final String NAME_SATURDAY = "六";
        public static final String[] DAY_OF_WEEK_NAMES = {
                NAME_SUNDAY,
                NAME_MONDAY,
                NAME_TUESDAY,
                NAME_WEDNESDAY,
                NAME_THURSDAY,
                NAME_FRIDAY,
                NAME_SATURDAY,
        };


        /**
         * @param type
         * @return
         */
        public static boolean isContainType(int type) {
            for (int existType : DAY_OF_WEEK_TYPES) {
                if (type == existType) {
                    return true;
                }
            }
            return false;
        }

        public static String getDayNameOfWeek(int type) {
            return isContainType(type) ? DAY_OF_WEEK_NAMES[type - TYPE_SUNDAY] : "";
        }

    }

}
