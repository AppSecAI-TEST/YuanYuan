package xyz.zimuju.common.configuration;

import android.content.Context;

public class UploadAndDownloadKey {
    public final static int WITH_DIALOG = 1;
    public final static int WITH_NOTIFICATION = 2;

    public static Context FROM_ACTIVITY = null;
    public static Boolean ISManual = false;
    public static Boolean LoadManual = false;
    public static int TOShowDownloadView = 0;
    public static String saveFileName = "Talk99-new—version.apk";
    public static String apkUrl = "";
    public static String changeLog = "";
    public static String version;
    public static boolean interceptFlag = false;
    public static String API_TOKEN = "bdb7ed6b9d2d45dab2f0907bbfb3813f"; // 和BugHD一样
    public static String APP_ID = "575e09e200fc746a5b000017";
    public static int DialogOrNotification = WITH_DIALOG; // Default
}
