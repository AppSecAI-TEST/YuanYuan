package xyz.zimuju.common.util;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**

 */
public class StringUtils {
    public static final String EMPTY = "无";
    public static final String UNKNOWN = "未知";
    public static final String UNLIMITED = "不限";
    public static final String I = "我";
    public static final String YOU = "你";
    public static final String HE = "他";
    public static final String SHE = "她";
    public static final String IT = "它";
    public static final String MALE = "男";
    public static final String FEMALE = "女";
    public static final String TODO = "未完成";
    public static final String DONE = "已完成";
    public static final String FAIL = "失败";
    public static final String SUCCESS = "成功";
    public static final String SUNDAY = "日";
    public static final String MONDAY = "一";
    public static final String TUESDAY = "二";
    public static final String WEDNESDAY = "三";
    public static final String THURSDAY = "四";
    public static final String FRIDAY = "五";
    public static final String SATURDAY = "六";
    public static final String YUAN = "元";
    public static final String HTTP = "http";
    public static final String URL_PREFIX = "http://";
    public static final String URL_PREFIXS = "https://";
    public static final String FILE_PATH_PREFIX = "file://";
    public static final int PRICE_FORMAT_DEFAULT = 0;
    public static final int PRICE_FORMAT_PREFIX = 1;
    public static final int PRICE_FORMAT_SUFFIX = 2;
    public static final int PRICE_FORMAT_PREFIX_WITH_BLANK = 3;
    public static final int PRICE_FORMAT_SUFFIX_WITH_BLANK = 4;
    public static final String[] PRICE_FORMATS = {"", "￥", "元", "￥ ", " 元"};

    //获取去掉前后空格后的string>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //获取去掉所有空格后的string <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private static final String TAG = "StringUtil";
    private static String currentString = "";

    public StringUtils() {
    }

    /**
     * 获取刚传入处理后的string
     *
     * @return
     * @must 上个影响currentString的方法 和 这个方法都应该在同一线程中，否则返回值可能不对
     */
    public static String getCurrentString() {
        return currentString == null ? "" : currentString;
    }

    //获取去掉所有空格后的string >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //获取string的长度<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取string,为null则返回""
     *
     * @param tv
     * @return
     */
    public static String getString(TextView tv) {
        if (tv == null || tv.getText() == null) {
            return "";
        }
        return getString(tv.getText().toString());
    }

    /**
     * 获取string,为null则返回""
     *
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return object == null ? "" : getString(String.valueOf(object));
    }

    /**
     * 获取string,为null则返回""
     *
     * @param cs
     * @return
     */
    public static String getString(CharSequence cs) {
        return cs == null ? "" : getString(cs.toString());
    }

    /**
     * 获取string,为null则返回""
     *
     * @param s
     * @return
     */
    public static String getString(String s) {
        return s == null ? "" : s;
    }

    //获取string的长度>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //判断字符是否非空 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取去掉前后空格后的string,为null则返回""
     *
     * @param tv
     * @return
     */
    public static String getTrimedString(TextView tv) {
        return getTrimedString(getString(tv));
    }

    /**
     * 获取去掉前后空格后的string,为null则返回""
     *
     * @param object
     * @return
     */
    public static String getTrimedString(Object object) {
        return getTrimedString(getString(object));
    }

    /**
     * 获取去掉前后空格后的string,为null则返回""
     *
     * @param cs
     * @return
     */
    public static String getTrimedString(CharSequence cs) {
        return getTrimedString(getString(cs));
    }

    /**
     * 获取去掉前后空格后的string,为null则返回""
     *
     * @param s
     * @return
     */
    public static String getTrimedString(String s) {
        return getString(s).trim();
    }

    //判断字符是否非空 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //判断字符类型 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取去掉所有空格后的string,为null则返回""
     *
     * @param tv
     * @return
     */
    public static String getNoBlankString(TextView tv) {
        return getNoBlankString(getString(tv));
    }

    /**
     * 获取去掉所有空格后的string,为null则返回""
     *
     * @param object
     * @return
     */
    public static String getNoBlankString(Object object) {
        return getNoBlankString(getString(object));
    }

    /**
     * 获取去掉所有空格后的string,为null则返回""
     *
     * @param cs
     * @return
     */
    public static String getNoBlankString(CharSequence cs) {
        return getNoBlankString(getString(cs));
    }

    /**
     * 获取去掉所有空格后的string,为null则返回""
     *
     * @param s
     * @return
     */
    public static String getNoBlankString(String s) {
        return getString(s).replaceAll(" ", "");
    }

    /**
     * 获取string的长度,为null则返回0
     *
     * @param tv
     * @param trim
     * @return
     */
    public static int getLength(TextView tv, boolean trim) {
        return getLength(getString(tv), trim);
    }

    /**
     * 获取string的长度,为null则返回0
     *
     * @param object
     * @param trim
     * @return
     */
    public static int getLength(Object object, boolean trim) {
        return getLength(getString(object), trim);
    }

    /**
     * 获取string的长度,为null则返回0
     *
     * @param cs
     * @param trim
     * @return
     */
    public static int getLength(CharSequence cs, boolean trim) {
        return getLength(getString(cs), trim);
    }

    /**
     * 获取string的长度,为null则返回0
     *
     * @param s
     * @param trim
     * @return
     */
    public static int getLength(String s, boolean trim) {
        s = trim ? getTrimedString(s) : s;
        return getString(s).length();
    }

    /**
     * 判断字符是否非空
     *
     * @param tv
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(TextView tv, boolean trim) {
        return isNotEmpty(getString(tv), trim);
    }

    /**
     * 判断字符是否非空
     *
     * @param object
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(Object object, boolean trim) {
        return isNotEmpty(getString(object), trim);
    }

    /**
     * 判断字符是否非空
     *
     * @param cs
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs, boolean trim) {
        return isNotEmpty(getString(cs), trim);
    }

    /**
     * 判断字符是否非空
     *
     * @param s
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(String s, boolean trim) {
        //		Log.i(TAG, "getTrimedString   s = " + s);
        if (s == null) {
            return false;
        }
        if (trim) {
            s = s.trim();
        }
        if (s.length() <= 0) {
            return false;
        }

        currentString = s;

        return true;
    }

    //判断手机格式是否正确
    public static boolean isPhone(String phone) {
        if (isNotEmpty(phone, true) == false) {
            return false;
        }

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-2,5-9])|(17[0-9]))\\d{8}$");

        currentString = phone;

        return p.matcher(phone).matches();
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        if (isNotEmpty(email, true) == false) {
            return false;
        }

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);

        currentString = email;

        return p.matcher(email).matches();
    }

    //判断字符类型 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //提取特殊字符<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //判断是否全是数字
    public static boolean isNumer(String number) {
        if (isNotEmpty(number, true) == false) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(number);
        if (!isNum.matches()) {
            return false;
        }

        currentString = number;

        return true;
    }

    /**
     * 判断字符类型是否是号码或字母
     *
     * @param inputed
     * @return
     */
    public static boolean isNumberOrAlpha(String inputed) {
        if (inputed == null) {
            Log.e(TAG, "isNumberOrAlpha  inputed == null >> return false;");
            return false;
        }
        Pattern pNumber = Pattern.compile("[0-9]*");
        Matcher mNumber;
        Pattern pAlpha = Pattern.compile("[a-zA-Z]");
        Matcher mAlpha;
        for (int i = 0; i < inputed.length(); i++) {
            mNumber = pNumber.matcher(inputed.substring(i, i + 1));
            mAlpha = pAlpha.matcher(inputed.substring(i, i + 1));
            if (!mNumber.matches() && !mAlpha.matches()) {
                return false;
            }
        }

        currentString = inputed;
        return true;
    }

    /**
     * 判断字符类型是否是身份证号
     *
     * @param idCard
     * @return
     */
    public static boolean isIDCard(String idCard) {
        if (isNumberOrAlpha(idCard) == false) {
            return false;
        }
        idCard = getString(idCard);
        if (idCard.length() == 15) {
            Log.w(TAG, "isIDCard idCard.length() == 15 old IDCard");
            currentString = idCard;
            return true;
        }
        if (idCard.length() == 18) {
            currentString = idCard;
            return true;
        }

        return false;
    }

    /**
     * 判断字符类型是否是网址
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        if (isNotEmpty(url, true) == false) {
            return false;
        } else if (!url.startsWith(URL_PREFIX) && !url.startsWith(URL_PREFIXS)) {
            return false;
        }

        currentString = url;
        return true;
    }

    //提取特殊字符>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //校正（自动补全等）字符串<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 判断文件路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFilePathExist(String path) {
        return StringUtils.isFilePath(path) && new File(path).exists();
    }

    /**
     * 判断字符类型是否是路径
     *
     * @param path
     * @return
     */
    public static boolean isFilePath(String path) {
        if (isNotEmpty(path, true) == false) {
            return false;
        }

        if (!path.contains(".") || path.endsWith(".")) {
            return false;
        }

        currentString = path;

        return true;
    }

    /**
     * 去掉string内所有非数字类型字符
     *
     * @param tv
     * @return
     */
    public static String getNumber(TextView tv) {
        return getNumber(getString(tv));
    }

    /**
     * 去掉string内所有非数字类型字符
     *
     * @param object
     * @return
     */
    public static String getNumber(Object object) {
        return getNumber(getString(object));
    }

    /**
     * 去掉string内所有非数字类型字符
     *
     * @param cs
     * @return
     */
    public static String getNumber(CharSequence cs) {
        return getNumber(getString(cs));
    }

    /**
     * 去掉string内所有非数字类型字符
     *
     * @param s
     * @return
     */
    public static String getNumber(String s) {
        if (isNotEmpty(s, true) == false) {
            return "";
        }

        String numberString = "";
        String single;
        for (int i = 0; i < s.length(); i++) {
            single = s.substring(i, i + 1);
            if (isNumer(single)) {
                numberString += single;
            }
        }

        return numberString;
    }

    /**
     * 获取网址，自动补全
     *
     * @param tv
     * @return
     */
    public static String getCorrectUrl(TextView tv) {
        return getCorrectUrl(getString(tv));
    }

    /**
     * 获取网址，自动补全
     *
     * @param url
     * @return
     */
    public static String getCorrectUrl(String url) {
        Log.i(TAG, "getCorrectUrl : \n" + url);
        if (isNotEmpty(url, true) == false) {
            return "";
        }

//		if (! url.endsWith("/") && ! url.endsWith(".html")) {
//			url = url + "/";
//		}

        if (isUrl(url) == false) {
            return URL_PREFIX + url;
        }
        return url;
    }

    /**
     * 获取去掉所有 空格 、"-" 、"+86" 后的phone
     *
     * @param tv
     * @return
     */
    public static String getCorrectPhone(TextView tv) {
        return getCorrectPhone(getString(tv));
    }

    /**
     * 获取去掉所有 空格 、"-" 、"+86" 后的phone
     *
     * @param phone
     * @return
     */
    public static String getCorrectPhone(String phone) {
        if (isNotEmpty(phone, true) == false) {
            return "";
        }

        phone = getNoBlankString(phone);
        phone = phone.replaceAll("-", "");
        if (phone.startsWith("+86")) {
            phone = phone.substring(3);
        }
        return phone;
    }

    /**
     * 获取邮箱，自动补全
     *
     * @param tv
     * @return
     */
    public static String getCorrectEmail(TextView tv) {
        return getCorrectEmail(getString(tv));
    }

    /**
     * 获取邮箱，自动补全
     *
     * @param email
     * @return
     */
    public static String getCorrectEmail(String email) {
        if (isNotEmpty(email, true) == false) {
            return "";
        }

        email = getNoBlankString(email);
        if (isEmail(email) == false && !email.endsWith(".com")) {
            email += ".com";
        }

        return email;
    }

    /**
     * 获取价格，保留两位小数
     *
     * @param price
     * @return
     */
    public static String getPrice(String price) {
        return getPrice(price, PRICE_FORMAT_DEFAULT);
    }

    /**
     * 获取价格，保留两位小数
     *
     * @param price
     * @param formatType 添加单位（元）
     * @return
     */
    public static String getPrice(String price, int formatType) {
        if (isNotEmpty(price, true) == false) {
            return getPrice(0, formatType);
        }

        //单独写到getCorrectPrice? <<<<<<<<<<<<<<<<<<<<<<
        String correctPrice = "";
        String s;
        for (int i = 0; i < price.length(); i++) {
            s = price.substring(i, i + 1);
            if (".".equals(s) || isNumer(s)) {
                correctPrice += s;
            }
        }
        //单独写到getCorrectPrice? >>>>>>>>>>>>>>>>>>>>>>

        Log.i(TAG, "getPrice  <<<<<<<<<<<<<<<<<< correctPrice =  " + correctPrice);
        if (correctPrice.contains(".")) {
//			if (correctPrice.startsWith(".")) {
//				correctPrice = 0 + correctPrice;
//			}
            if (correctPrice.endsWith(".")) {
                correctPrice = correctPrice.replaceAll(".", "");
            }
        }

        Log.i(TAG, "getPrice correctPrice =  " + correctPrice + " >>>>>>>>>>>>>>>>");
        return isNotEmpty(correctPrice, true) ? getPrice(new BigDecimal(0 + correctPrice), formatType) : getPrice(0, formatType);
    }

    /**
     * 获取价格，保留两位小数
     *
     * @param price
     * @return
     */
    public static String getPrice(BigDecimal price) {
        return getPrice(price, PRICE_FORMAT_DEFAULT);
    }

    /**
     * 获取价格，保留两位小数
     *
     * @param price
     * @return
     */
    public static String getPrice(double price) {
        return getPrice(price, PRICE_FORMAT_DEFAULT);
    }

    /**
     * 获取价格，保留两位小数
     *
     * @param price
     * @param formatType 添加单位（元）
     * @return
     */
    public static String getPrice(BigDecimal price, int formatType) {
        return getPrice(price == null ? 0 : price.doubleValue(), formatType);
    }

    /**
     * 获取价格，保留两位小数
     *
     * @param price
     * @param formatType 添加单位（元）
     * @return
     */
    public static String getPrice(double price, int formatType) {
        String s = new DecimalFormat("#########0.00").format(price);
        switch (formatType) {
            case PRICE_FORMAT_PREFIX:
                return PRICE_FORMATS[PRICE_FORMAT_PREFIX] + s;
            case PRICE_FORMAT_SUFFIX:
                return s + PRICE_FORMATS[PRICE_FORMAT_SUFFIX];
            case PRICE_FORMAT_PREFIX_WITH_BLANK:
                return PRICE_FORMATS[PRICE_FORMAT_PREFIX_WITH_BLANK] + s;
            case PRICE_FORMAT_SUFFIX_WITH_BLANK:
                return s + PRICE_FORMATS[PRICE_FORMAT_SUFFIX_WITH_BLANK];
            default:
                return s;
        }
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean isEquals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEquals(CharSequence a, CharSequence b) {
        return !isEquals(a, b);
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    public static int formatInteger(String value) {
        if (!StringUtils.isEmpty(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    public static long formatLong(String value) {
        if (!StringUtils.isEmpty(value)) {
            return Long.parseLong(value);
        }
        return 0;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void echoLogs(String message) {
        Log.d("LOG_TAG", message);
    }
}