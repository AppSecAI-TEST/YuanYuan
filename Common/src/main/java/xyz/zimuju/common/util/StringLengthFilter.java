package xyz.zimuju.common.util;

/*
 * @description StringLengthFilter
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/6/6 - 13:05
 * @version v1.0.0
 */

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLengthFilter implements InputFilter {
    int maxLengthEn;// 最大英文/数字长度 一个汉字算两个字母
    String regexExpression = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

    public StringLengthFilter(int length) {
        super();
        maxLengthEn = length;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int destCount = dest.toString().length() + getChineseCount(dest.toString());
        int sourceCount = source.toString().length() + getChineseCount(source.toString());
        if (destCount + sourceCount > maxLengthEn) {
            source = source.subSequence(0, maxLengthEn) + "...";
        }
        return source;
    }

    private int getChineseCount(String str) {
        int count = 0;
        Pattern pattern = Pattern.compile(regexExpression);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }
}
