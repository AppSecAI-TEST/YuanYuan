package xyz.zimuju.common.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/*
 * @description CommonUtils ：公共的工具类
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/8/17-9:58
 * @version v1.0.0
 */
public class KeyboardUtil {

    public static void showSoftInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static boolean isShowSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive(); // true 打开
    }
}
