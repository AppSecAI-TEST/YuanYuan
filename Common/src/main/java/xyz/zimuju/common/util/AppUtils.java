package xyz.zimuju.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

/*
 * @description GetAppInfo ： 获取应用的信息
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-11-9-下午3:50
 * @version v1.0.0
 */
public class AppUtils {
    /*
     * @function 获取应用的名称
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-11-9-下午3:52
     * @parameters Context context : 上下文对象
     * @return String app的名称
     * @version v1.0.0
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appName = pi.applicationInfo.loadLabel(context.getPackageManager()).toString();
            if (appName == null || appName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    /*
     * @function 获取app的版本号，例如v1.0.1
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-11-9-下午3:53
     * @parameters Context context : 上下文对象
     * @return String 版本号
     * @version v1.0.0
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * @function 获取应用的包名，例如com.example.demo
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-11-9-下午3:54
     * @parameters Context context : 上下文对象
     * @return String 包名
     * @version v1.0.0
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /*
     * @function 获取应用的图标
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-11-9-下午3:55
     * @parameters Context context 上下文对象
     * @return Drawable 一个Drawable对象
     * @version v1.0.0
     */
    public static Drawable getAppIcon(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo info = packageManager.getApplicationInfo(context.getPackageName(), 0);
            return info.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @function 获取APK的文件名
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-11-9-下午3:57
     * @parameters  Context context 上下文对象
     *              String apkath apk文件存储的目录
     * @return String apk的文件名
     * @version v1.0.0
     */
    public static String getAPKPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;
        }
        return null;
    }

}
