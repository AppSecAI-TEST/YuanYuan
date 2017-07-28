package xyz.zimuju.common.util;

import com.google.gson.Gson;

import xyz.zimuju.common.entity.BasalBean;
import xyz.zimuju.common.entity.BasalResult;

public class GsonUtil {
    private static Gson gson = new Gson();

    /**
     * 解析json数据   T:泛型
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T processJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> boolean buildDataResult(BasalResult<T> res, String json, BasalBean stateBean) {
        if (res == null) {
            return false;
        }
        if (res.getCode() == 0) {
            return true;
        } else {
            stateBean.setCode(res.getCode());
        }
        return false;
    }

    public static <T> BasalResult<T> fromJsonToData(String resJson, java.lang.reflect.Type type) {
        try {
            return gson.fromJson(resJson, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
