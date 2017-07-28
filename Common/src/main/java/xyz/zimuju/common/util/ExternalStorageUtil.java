package xyz.zimuju.common.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/*
 * @description ExternalStorageUtil : 外置储存卡的工具类
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-12-20-下午3:42
 * @version v1.0.0
 */
public class ExternalStorageUtil {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    private ExternalStorageUtil() {
    }

    /*
     * @function 获取缓存目录
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-12-20-下午3:42
     * @parameters Context context 上下文对象
     * @return File 返回一个File对象
     * @version v1.0.0
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            Log.w("UpdateFun TAG", "Can't define system cache directory! The app should be re-installed.");
        }
        return appCacheDir;
    }


    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (! appCacheDir.exists()) {
            if (! appCacheDir.mkdirs()) {
                Log.w("UpdateFun TAG", "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i("UpdateFun TAG", "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
