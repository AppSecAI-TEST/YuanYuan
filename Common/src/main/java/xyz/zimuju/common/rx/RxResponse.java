package xyz.zimuju.common.rx;

import io.reactivex.functions.Function;
import xyz.zimuju.common.entity.BasalResult;
import xyz.zimuju.common.util.EmptyUtil;
import xyz.zimuju.common.util.GsonUtil;
import xyz.zimuju.common.util.ZipUtils;

public class RxResponse<T> implements Function<BasalResult<T>, T> {

    private Class<T> clazz;

    public RxResponse(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> T parseBasicResult(BasalResult<T> res, Class<T> clazz) {
        T data;

        if ("gzip".equals(res.getCompress())) {
            data = zipJsonToData(res.getJson(), clazz);
        } else {
            data = res.getData();
        }

        return data;
    }

    // json字符串先base64解密，再zip解压，再转换成对象T输出
    public static <T> T zipJsonToData(String json, Class<T> clazz) {
        T data = null;
        if (EmptyUtil.isNotEmpty(json)) {
            long timeStart = System.currentTimeMillis();
            long size1 = json.length();
            String decompressed = ZipUtils.gunzip(json);
            if (EmptyUtil.isNotEmpty(decompressed)) {
                long size2 = decompressed.length();
                data = GsonUtil.processJson(decompressed, clazz);
                long timeEnd = System.currentTimeMillis();
                long timeDur = timeEnd - timeStart;
            }
        }
        return data;
    }

    @Override
    public T apply(BasalResult<T> tBasicResult) throws Exception {

        if (tBasicResult.getCode() != 0) {
            throw new RxResponseException(tBasicResult.getCode());
        }
        T objRes = parseBasicResult(tBasicResult, clazz);
        if (EmptyUtil.isEmpty(objRes)) {
            throw new RxResponseException(999);
        }
        return objRes;
    }
}
