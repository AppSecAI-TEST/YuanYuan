package xyz.zimuju.sample.engine.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.zimuju.sample.entity.content.WeiBo;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:17:50
 */
public interface SinaApiService {
    String BASE_URL = "https://api.weibo.com/2/";

    @GET("users/show.json")
    Single<WeiBo> getUserInfo(@Query("access_token") String access_token, @Query("uid") String uid);
}
