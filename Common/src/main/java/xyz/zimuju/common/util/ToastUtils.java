package xyz.zimuju.common.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast = null;
    private static long lastedShowTime = 0L;

    public static void showToast(Context context, String message, int duration) {
        if (lastedShowTime - System.currentTimeMillis() <= 1000) {
            return;
        }

        if (toast != null) {
            toast.cancel();
            toast = null;
        }

        toast = Toast.makeText(context, message, duration);
        toast.setText(message);
        toast.setDuration(duration);
        toast.show();
        lastedShowTime = System.currentTimeMillis();
    }

    public static void showToast(Context context, CharSequence message) {
        if (lastedShowTime - System.currentTimeMillis() <= 1000) {
            return;
        }

        if (toast != null) {
            toast.cancel();
            toast = null;
        }

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        lastedShowTime = System.currentTimeMillis();
    }


    public static void showCenterToast(Context context, CharSequence message) {
        if (lastedShowTime - System.currentTimeMillis() <= 1000) {
            return;
        }

        if (toast != null) {
            toast.cancel();
            toast = null;
        }

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        lastedShowTime = System.currentTimeMillis();
    }

    public static void showCenterToast(Context context, int stringId) {
        if (lastedShowTime - System.currentTimeMillis() <= 1000) {
            return;
        }

        if (toast != null) {
            toast.cancel();
            toast = null;
        }

        toast = Toast.makeText(context, stringId, Toast.LENGTH_SHORT);
        toast.setText(stringId);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        lastedShowTime = System.currentTimeMillis();
    }

}
