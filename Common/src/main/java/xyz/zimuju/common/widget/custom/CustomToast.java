package xyz.zimuju.common.widget.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xyz.zimuju.common.R;


public class CustomToast extends Toast {
    // 图标状态 不显示图标
    private static final int TYPE_HIDE = -1;
    // 图标状态 显示√
    private static final int TYPE_TRUE = 0;
    // 图标状态 显示×
    private static final int TYPE_FALSE = 1;

    private static CustomToast toast;

    private static ImageView toastImage;
    private static TextView toastText;

    private static long lastTime = 0L;
    private static View contentView;

    public CustomToast(Context context) {
        super(context);
    }

    // 显示一个纯文本吐司
    public static void showText(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT, TYPE_HIDE);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, CharSequence text, boolean isSucceed) {
        showToast(context, text, Toast.LENGTH_SHORT, isSucceed ? TYPE_TRUE : TYPE_FALSE);
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param time    持续的时间
     */
    public static void showText(Context context, CharSequence text, int time) {
        showToast(context, text, time, TYPE_HIDE);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, CharSequence text, int time, boolean isSucceed) {
        showToast(context, text, time, isSucceed ? TYPE_TRUE : TYPE_FALSE);
    }

    /**
     * 显示Toast
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param time      显示时长
     * @param imageType 图标状态
     */
    private static void showToast(Context context, CharSequence text, int time, int imageType) {
        initToast(context, text);
        if (time == Toast.LENGTH_LONG) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        // 判断图标是否该显示，显示 √ 还是 ×
        if (imageType == TYPE_HIDE) {
            toastImage.setVisibility(View.GONE);
        } else {
            if (imageType == TYPE_TRUE) {
                toastImage.setBackgroundResource(R.mipmap.icon_toast_yes);
            } else {
                toastImage.setBackgroundResource(R.mipmap.icon_toast_no);
            }
            toastImage.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(toastImage, "rotationY", 0, 360).setDuration(1700).start();
        }
        toast.show();
    }

    private static void initToast(Context context, CharSequence text) {
        if (toast == null) {
            toast = new CustomToast(context);
            // 获取LayoutInflater对象
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            contentView = inflater.inflate(R.layout.common_toast_layout, null);
            // 吐司上的图片
            toastImage = (ImageView) contentView.findViewById(R.id.toast_image);

            toastText = (TextView) contentView.findViewById(R.id.toast_text);
        } else {
            toast.cancel();
            toastText.setText(text);
            toast.setView(contentView);
        }
        toast.setGravity(Gravity.CENTER, 0, 70);
    }

    public void cancel() {
        super.cancel();
    }

    @Override
    public void show() {
        super.show();
    }

}