package xyz.zimuju.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintScreenUtil {

    public static void shootScrollView(ScrollView topScrollView, ScrollView bottomScrollView, int bitmap, Context context) {

        Date date = new Date();
        String strs = "";
        try {
            //yyyy表示年MM表示月dd表示日
            //yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmss");
            //进行格式化
            strs = sdf.format(date);
            System.out.println(strs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = Environment.getExternalStorageDirectory() + File.separator + "/DCIM/Camera" + strs + ".jpg";
        Resources res = context.getResources();
        Bitmap mergeBitmap = null;
        Bitmap bmp = BitmapFactory.decodeResource(res, bitmap);


        if (EmptyUtil.isEmpty(bottomScrollView)) {
            Bitmap scrollViewBitmap = getScrollViewBitmap(topScrollView, path);
            mergeBitmap = add2Bitmap(scrollViewBitmap, bmp);
        } else {
            if (bottomScrollView.getHeight() == 0) {
                return;
            }
            Bitmap scrollViewBitmap = getScrollViewBitmap(topScrollView, path);
            Bitmap bottomScrollViewBitmap = getScrollViewBitmap(bottomScrollView, path);
            mergeBitmap = add2Bitmap2(scrollViewBitmap, bottomScrollViewBitmap, bmp);
        }

        File file = new File(path);
        savePic(mergeBitmap, file);

        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), strs, null);
            MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, null);
        } catch (Exception e) {

        }


    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView, String picpath) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);

        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return bitmap;
    }


    private static Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(first.getWidth(), height, Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }


    private static Bitmap add2Bitmap2(Bitmap first, Bitmap bottom, Bitmap second) {
        int height = first.getHeight() + bottom.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(first.getWidth(), height, Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(bottom, 0, first.getHeight(), null);
        //canvas.drawBitmap(second,first.getWidth(), 0, null);
        canvas.drawBitmap(second, 0, bottom.getHeight() + first.getHeight(), null);
        return result;
    }


    // 保存到sdcard
    public static void savePic(Bitmap b, File f) {
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
