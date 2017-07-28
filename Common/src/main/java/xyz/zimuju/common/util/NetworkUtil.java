package xyz.zimuju.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


/*
 * @description NetUtil
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/6/12 - 10:27
 * @version v1.0.0
 */
public class NetworkUtil {
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    public static final int NETWORK_TYPE_SECOND_GENERATION = 2;
    public static final int NETWORK_TYPE_THIRD_GENERATION = 3;
    public static final int NETWORK_TYPE_FOUR_GENERATION = 4;
    public static final int NETWORK_TYPE_WAP = 5;
    public static final int NETWORK_TYPE_INVALID = 6;
    private static PowerManager.WakeLock wakeLock;

    /*
     * @function 判断当前的网络环境并判断是否可用
     * @author Nathaniel-nathanwriting@126.com
     * @time 2016/6/12 - 10:54
     * @parameter Context context
     * @return boolean  true 当前网络可用
     *                  false 当前网络不可用
     * @version v1.0.0
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * @function 获取网络的类型
     * @author Nathaniel-nathanwriting@126.com
     * @time 2016/6/12 - 11:40
     * @parameter Context context
     * @return int
     * @version v1.0.0
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /*
     * @function 获取手机网络类型（2G/3G/4G）：
     * 4G为LTE
     * 联通的3G为UMTS或HSDPA
     * 电信的3G为EVDO
     * 移动和联通的2G为GPRS或EGDE
     * 电信的2G为CDMA
     * @author Nathaniel-nathanwriting@126.com
     * @time 17-1-18-下午3:11
     * @parameters Context context
     * @return int networkTYpe
     * @version v1.0.0
     */
    public static int getNetWorkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            // 2G
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_TYPE_SECOND_GENERATION;

            // 3G
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_TYPE_THIRD_GENERATION;

            // 4G
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_TYPE_FOUR_GENERATION;

            // 未知
            default:
                return NETWORK_TYPE_UNKNOWN;
        }
    }

    /*
     * @function 获取手机连接的网络类型（是WIFI还是手机网络[2G/3G/4G]）：
     * @author Nathaniel-nathanwriting@126.com
     * @time 17-1-18-下午3:13
     * @parameters Context context ：上下文
     * @return int networkStatus ：网络状态
     * @version v1.0.0
     */
    public static int getNetWorkStatus(Context context) {
        int netWorkType = NETWORK_TYPE_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkType(context);
            }
        }

        return netWorkType;
    }

    /*
     * @function 当前状态是不是WiFi
     * @author Nathaniel-nathanwriting@126.com
     * @time 2016/6/12 - 11:35
     * @parameter  Context context
     * @return  boolean true
     *                  false
     * @version v1.0.0
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /*
     * @function 判断当前的网络是否为手机网络
     * @author Nathaniel-nathanwriting@126.com
     * @time 2016/6/12 - 11:38
     * @parameter Context context
     * @return boolean  true
     *                  false
     * @version v1.0.0
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    public static void acquireWakeLock(Context context) {
        if (null == wakeLock) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    public static void releaseWakeLock() {
        if (null != wakeLock) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    /*
     * @function 判断是否是FastMobileNetWork，将3G或者3G以上的网络称为快速网络
     * @author Nathaniel-nathanwriting@126.com
     * @time 17-1-18-下午3:21
     * @parameters Context context : 上下文
     * @return boolean  true: 快速网络
     *                  false： 慢速网络
     * @version v1.0.0
     */
    public static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /*
     * @function 获取网络类型的名字
     * @author Nathaniel-nathanwriting@126.com
     * @time 17-1-18-下午3:25
     * @parameters Context context : 上下文
     * @return String networkTypeName: 网络类型的名字
     * @version v1.0.0
     */
    public static String getNetWorkTypeName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        int networkType = 0;
        String networkTypeName = null;
        if (networkInfo != null && networkInfo.isConnected()) {
            networkTypeName = networkInfo.getTypeName();

            if (networkTypeName.equalsIgnoreCase("WIFI")) {
                networkType = NETWORK_TYPE_WIFI;
            } else if (networkTypeName.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                networkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context)
                        ? NETWORK_TYPE_THIRD_GENERATION
                        : NETWORK_TYPE_SECOND_GENERATION)
                        : NETWORK_TYPE_WAP;
            }
        } else {
            networkType = NETWORK_TYPE_INVALID;
        }

        switch (networkType) {
            case NETWORK_TYPE_WAP:
                networkTypeName = "WAP";
                break;

            case NETWORK_TYPE_SECOND_GENERATION:
                networkTypeName = "2G";
                break;

            case NETWORK_TYPE_THIRD_GENERATION:
                networkTypeName = "3G";
                break;
            case NETWORK_TYPE_FOUR_GENERATION:
                networkTypeName = "4G";
                break;
            case NETWORK_TYPE_INVALID:
                networkTypeName = "None";
                break;
            case NETWORK_TYPE_UNKNOWN:
                networkTypeName = "Unknown";
                break;
        }
        return networkTypeName;
    }
}
