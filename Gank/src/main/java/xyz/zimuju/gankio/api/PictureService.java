package xyz.zimuju.gankio.api;

import io.reactivex.Single;
import xyz.zimuju.gankio.bean.RandomPicture;
import retrofit2.http.GET;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:17:06
 */
public interface PictureService {
    String BASE_URL = "http://lelouchcrgallery.tk/";

    @GET("rand")
    Single<RandomPicture> getRandomPicture();
}
