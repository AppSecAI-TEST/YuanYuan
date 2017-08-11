package xyz.zimuju.sample.engine;

import io.reactivex.Single;
import retrofit2.http.GET;
import xyz.zimuju.sample.entity.content.RandomPicture;

public interface PictureService {
    String BASE_URL = "http://lelouchcrgallery.tk/";

    @GET("rand")
    Single<RandomPicture> getRandomPicture();
}
