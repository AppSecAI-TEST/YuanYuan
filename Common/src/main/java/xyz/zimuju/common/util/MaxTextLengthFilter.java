package xyz.zimuju.common.util;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;

/*
 * @description MaxTextLengthFilter : EditText的文本过滤器
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/9/1-22:23
 * @version v1.0.0
 */
public class MaxTextLengthFilter implements InputFilter {
    private int maxLength;
    private Context context;

    public MaxTextLengthFilter(Context context, int maxLength) {
        this.context = context;
        this.maxLength = maxLength - 1;

    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int keep = maxLength - (dest.length() - (dend - dstart));
        if (keep < (end - start)) {
            StringUtils.showToast(context, "最多可以输入" + maxLength + "个字符");
        }
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null;
        } else {
            return source.subSequence(start, start + keep);
        }
    }
}
