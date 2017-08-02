package xyz.zimuju.sample.util;


import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.entity.Parameter;
import xyz.zimuju.common.manager.OnHttpResponseListener;
import xyz.zimuju.common.util.MD5Util;
import xyz.zimuju.common.util.SettingUtil;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.application.UserApplication;
import xyz.zimuju.sample.manager.HttpManager;

public class HttpRequest {
    public static final String URL_BASE = SettingUtil.getCurrentServerAddress();
    public static final String PAGE_NUM = "pageNum";
    public static final String RANGE = "range";


    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String CURRENT_USER_ID = "currentUserId";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String AUTH_CODE = "authCode";
    public static final String SEX = "sex";
    public static final int SEX_MAIL = 1;
    public static final int SEX_FEMAIL = 2;
    public static final int SEX_ALL = 3;
    public static final int RESULT_GET_USER_SUCCEED = 100;
    public static final int USER_LIST_RANGE_ALL = 0;
    public static final int USER_LIST_RANGE_RECOMMEND = 1;
    public static final int RESULT_GET_USER_LIST_SUCCEED = 110;

    /**
     * 添加请求参数，value为空时不添加
     *
     * @param list
     * @param key
     * @param value
     */
    public static void addExistParameter(List<Parameter> list, String key, Object value) {
        if (list == null) {
            list = new ArrayList<Parameter>();
        }
        if (StringUtils.isNotEmpty(key, true) && StringUtils.isNotEmpty(value, true)) {
            list.add(new Parameter(key, value));
        }
    }

    /**
     * 翻译，根据有道翻译API文档请求
     * http://fanyi.youdao.com/openapi?path=data-mode
     * <br > 本Demo中只有这个是真正可用，其它需要自己根据接口文档新增或修改
     *
     * @param word
     * @param requestCode
     * @param listener
     */
    public static void translate(String word, final int requestCode, OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, "q", word);
        addExistParameter(paramList, "keyfrom", "ZBLibrary");
        addExistParameter(paramList, "key", 1430082675);
        addExistParameter(paramList, "type", "data");
        addExistParameter(paramList, "doctype", "json");
        addExistParameter(paramList, "version", "1.1");

        HttpManager.getInstance().get(paramList, "http://fanyi.youdao.com/openapi.do", requestCode, listener);
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param listener
     */
    public static void register(final String phone, final String password, final int requestCode, OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, PHONE, phone);
        addExistParameter(paramList, PASSWORD, MD5Util.MD5(password));

        HttpManager.getInstance().post(paramList, URL_BASE + "user/register", requestCode, listener);
    }

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @param listener
     */
    public static void login(final String phone, final String password, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, PHONE, phone);
        addExistParameter(paramList, PASSWORD, MD5Util.MD5(password));
        HttpManager.getInstance().post(paramList, URL_BASE + "user/login", requestCode, listener);
    }

    /**
     * 获取用户
     *
     * @param userId
     * @param requestCode
     * @param listener
     */
    public static void getUser(long userId, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, CURRENT_USER_ID, UserApplication.getInstance().getCurrentUserId());
        addExistParameter(paramList, USER_ID, userId);

        HttpManager.getInstance().get(paramList, URL_BASE + "user/information", requestCode, listener);
    }

    /**
     * 获取用户列表
     *
     * @param range
     * @param pageNum
     * @param requestCode
     * @param listener
     */
    public static void getUserList(int range, int pageNum, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, CURRENT_USER_ID, UserApplication.getInstance().getCurrentUserId());
        addExistParameter(paramList, RANGE, range);
        addExistParameter(paramList, PAGE_NUM, pageNum);

        HttpManager.getInstance().get(paramList, URL_BASE + "user/list", requestCode, listener);
    }
}