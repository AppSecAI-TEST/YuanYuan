package xyz.zimuju.common.util;

import java.io.UnsupportedEncodingException;

/*
 * @description ChangeCharset : 字符集转换
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/8/31-17:20
 * @version v1.0.0
 */
public class CharsetUtils {
    public static final String US_ASCII = "US-ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_16 = "UTF-16";
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";

    public static String toASCII(String str) throws UnsupportedEncodingException {
        return changeCharset(str, US_ASCII);
    }

    public static String toISO88591(String str) throws UnsupportedEncodingException {
        return changeCharset(str, ISO_8859_1);
    }

    public static String toUTF8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_8);
    }

    public static String toUTF16BE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16BE);
    }

    public static String toUTF16LE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16LE);
    }

    public static String toUTF16(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16);
    }


    public static String toGBK(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GBK);
    }


    public static String toGB2312(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GB2312);
    }

    public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes();
            return new String(bs, newCharset); // 用新的字符编码生成字符串
        }
        return null;
    }

    public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用源字符编码解码字符串
            byte[] bs = str.getBytes(oldCharset);
            return new String(bs, newCharset);
        }
        return null;
    }
}