package xyz.zimuju.sample.http.function;

import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class StringFunction implements Function<ResponseBody, String> {

    @Override
    public String apply(@NonNull ResponseBody responseBody) throws Exception {
        String result = null;
        try {
            result = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
