package xyz.zimuju.sample.util;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import xyz.zimuju.sample.application.GankIOApplication;
import xyz.zimuju.sample.entity.content.Weibo;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:14:49
 */
public class AuthorityUtils {


    private static Context getContext() {
        return GankIOApplication.getInstance();
    }

    public static boolean isLogin() {
        return PrefUtils.getBoolean(GankIOApplication.getInstance(), "isLogin", false);
    }

    public static void login(Weibo result) {

        setUserName(result.getScreen_name());
        setDescription(result.getDescription());
        setAvatar(result.getAvatar_large());

        setIsLogin(true);
    }

    public static void logout() {
        setUserName("");
        setDescription("");
        setAvatar("");

        setIsLogin(false);
    }

    private static void setIsLogin(boolean isLogin) {
        PrefUtils.putBoolean(GankIOApplication.getInstance(), "isLogin", isLogin);
    }

    public static String getUserName() {
        return PrefUtils.getString(getContext(), "UserName", "");
    }

    public static void setUserName(String userName) {
        PrefUtils.putString(getContext(), "UserName", userName);
    }

    public static String getDescription() {
        return PrefUtils.getString(getContext(), "Description", "未填写");
    }

    public static void setDescription(String desc) {
        PrefUtils.putString(getContext(), "Description", desc);
    }

    public static String getAvatar() {
        return PrefUtils.getString(getContext(), "Avatar", "");
    }

    public static void setAvatar(String avatar) {
        PrefUtils.putString(getContext(), "Avatar", avatar);
    }


    //新浪微博相关

    public static String getAccessToken() {
        return PrefUtils.getString(getContext(), "access_token", "");
    }

    public static void setAccessToken(String access_token) {
        PrefUtils.putString(getContext(), "access_token", access_token);
    }

    public static String getUid() {
        return PrefUtils.getString(getContext(), "uid", "");
    }

    public static void setUid(String uid) {
        PrefUtils.putString(getContext(), "uid", uid);
    }

    public static String getRefreshToken() {
        return PrefUtils.getString(getContext(), "refresh_token", "");
    }

    public static void setRefreshToken(String refresh_token) {
        PrefUtils.putString(getContext(), "refresh_token", refresh_token);
    }

    public static long getExpiresIn() {
        return PrefUtils.getLong(getContext(), "expires_in", 0);
    }

    public static void setExpiresIn(long expires_in) {
        PrefUtils.putLong(getContext(), "expires_in", expires_in);
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     *
     * @return 返回 Token 对象
     */
    public static Oauth2AccessToken readOauth2AccessToken() {

        Oauth2AccessToken token = new Oauth2AccessToken();
        token.setUid(getUid());
        token.setToken(getAccessToken());
        token.setRefreshToken(getRefreshToken());
        token.setExpiresTime(getExpiresIn());

        return token;
    }

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param token Token 对象
     */
    public static void writeAccessToken(Oauth2AccessToken token) {

        setUid(token.getUid());
        setAccessToken(token.getToken());
        setRefreshToken(token.getRefreshToken());
        setExpiresIn(token.getExpiresTime());
    }

}
