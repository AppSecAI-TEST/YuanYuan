package xyz.zimuju.sample.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;

/*
 * @description SpannableStringUtils :
 * @author Nathaniel-nathanwriting@126.com
 * @time 17-8-5-上午2:05
 * @version v1.0.0
 */
public class SpannableStringUtils {
    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(), 0);
        return spannableString;
    }
}
