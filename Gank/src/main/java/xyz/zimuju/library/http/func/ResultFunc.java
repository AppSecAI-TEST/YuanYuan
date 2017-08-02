package xyz.zimuju.library.http.func;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import xyz.zimuju.library.http.HttpResult;
import xyz.zimuju.library.utils.json.JsonConvert;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:11:04
 */
public class ResultFunc<T> implements Function<String, HttpResult<T>> {


    @Override
    public HttpResult<T> apply(@NonNull String result) throws Exception {
        JsonConvert<HttpResult<T>> convert = new JsonConvert<HttpResult<T>>() {
        };
        return convert.parseData(result);
    }
}
