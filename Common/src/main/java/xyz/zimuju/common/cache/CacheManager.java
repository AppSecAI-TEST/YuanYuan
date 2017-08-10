package xyz.zimuju.common.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * lru 缓存在某些手机上会崩溃，暂时用preferences来做缓存
 */
public class CacheManager {

    private static final String PREF_NAME = "master_cache";
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static CacheManager instance;

    public static synchronized CacheManager getInstance(Context cxt) {
        if (mSharedPreferences == null) {
            mSharedPreferences = cxt.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
        return instance;
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }


    public static void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

}
