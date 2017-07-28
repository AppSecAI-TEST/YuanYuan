package xyz.zimuju.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


public class GsonHelper {

    public static <T> T fromJson(byte[] bytes, Class<T> clz) {
        if (bytes == null || bytes.length == 0)
            return null;
        try {
            return fromJson(new String(bytes, "UTF-8"), clz);
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T fromJson(byte[] bytes, Type clz) {
        if (bytes == null || bytes.length == 0)
            return null;
        Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            return gs.fromJson(new String(bytes, "utf-8"), clz);
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T fromJson(String str, Type clz) {
        if (str == null || str.length() == 0)
            return null;
        Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            return gs.fromJson(str, clz);
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T fromJson(String str, Class<T> clz) {
        if (str == null || str.length() == 0)
            return null;
        Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            return gs.fromJson(str, clz);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String toJson(Object obj) {
        Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gs.toJson(obj);
    }
}
