package xyz.zimuju.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil {
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void drawCenterText(String text, RectF rectF, Canvas canvas, Paint paint) {
        Paint.Align align = paint.getTextAlign();
        float x;
        float y;
        //x
        if (align == Paint.Align.LEFT) {
            x = rectF.centerX() - paint.measureText(text) / 2;
        } else if (align == Paint.Align.CENTER) {
            x = rectF.centerX();
        } else {
            x = rectF.centerX() + paint.measureText(text) / 2;
        }

        Paint.FontMetrics metrics = paint.getFontMetrics();
        float acent = Math.abs(metrics.ascent);
        float descent = Math.abs(metrics.descent);
        y = rectF.centerY() + (acent - descent) / 2f;
        canvas.drawText(text, x, y, paint);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int round(Context context, int paramInt) {
        if (context == null) {
            throw new RuntimeException("context is null...");
        }
        return Math.round(paramInt / getDensity(context));
    }

    public static int getScreenWidth(Activity activity) {
        if (activity != null) {
            DisplayMetrics displaymetrics = activity.getResources().getDisplayMetrics();
            return displaymetrics.widthPixels;
        }
        return 1080;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

}
