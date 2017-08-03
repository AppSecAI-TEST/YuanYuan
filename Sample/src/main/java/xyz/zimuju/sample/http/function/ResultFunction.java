package xyz.zimuju.sample.http.function;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import xyz.zimuju.sample.entity.HttpResult;
import xyz.zimuju.sample.json.JsonConvert;

public class ResultFunction<T> implements Function<String, HttpResult<T>> {

    @Override
    public HttpResult<T> apply(@NonNull String result) throws Exception {
        JsonConvert<HttpResult<T>> convert = new JsonConvert<HttpResult<T>>() {
        };
        return convert.parseData(result);
    }
}
