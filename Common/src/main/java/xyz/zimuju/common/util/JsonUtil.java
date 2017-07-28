package xyz.zimuju.common.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    /**
     * 字符串转JSONArray类型
     *
     * @param JsonStr Json字符串
     * @return JSONArray实例，为NULL表示转换失败
     */
    public static JSONObject Str2Json(String JsonStr) {
        JSONObject Json = null;

        if (!JsonStr.equals("")) {
            try {
                Json = new JSONObject(JsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Json;
    }

    public static JSONArray Str2JsonArray(String JsonStr) {
        JSONArray Json = null;

        if (!JsonStr.equals("")) {
            try {
                Json = new JSONArray(JsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Json;
    }

}
