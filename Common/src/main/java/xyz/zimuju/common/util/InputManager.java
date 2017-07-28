package xyz.zimuju.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputManager {
    public static InputManager inputManager;
    private Context context;
    private InputMethodManager inputMethodManager;

    private InputManager(Context context) {
        this.context = context;
        // 得到InputMethodManager的实例
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static InputManager getInstances(Context context) {
        if (inputManager == null) {
            inputManager = new InputManager(context);
        }
        return inputManager;
    }

    public static void showSoftWindow(EditText editText, Context context) {
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    public static void hideSoftWindow(EditText editText, Context context) {
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 切换软键盘的显示与隐藏
     */
    public void totleShowSoftInput() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public boolean isActive() {
        return inputMethodManager.isActive();
    }

    /**
     * 判断软键盘 弹出
     */
    public void showSoftInput(EditText editText) {
        if (!inputMethodManager.isActive()) {
            inputMethodManager.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        }
    }

    /**
     * 关闭软键盘
     * 针对于 有一个EdtxtView * @param input_email
     */
    public void hideSoftInput(EditText input_email) {
        if (inputMethodManager.isActive()) {
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            inputMethodManager.hideSoftInputFromWindow(input_email.getWindowToken(), 0);
        }
    }

    /**
     * 针对于 有多个EditText * 关闭所有的软键盘
     */
    public void hideALlSoftInput() {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
