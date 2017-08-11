package xyz.zimuju.sample.engine;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.zimuju.sample.entity.HttpResult;
import xyz.zimuju.sample.entity.content.Daily;
import xyz.zimuju.sample.entity.content.DailyList;
import xyz.zimuju.sample.entity.content.GanHuoData;
import xyz.zimuju.sample.entity.content.SearchResult;

public interface GankService {

    String BASE_URL = "http://www.gank.io/api/";

    // 获取发布干货的日期
    @GET("day/history")
    Single<HttpResult<List<String>>> getRecentlyDate();

    // 根据类别查询干货
    @GET("data/{category}/20/{pageIndex}")
    Single<HttpResult<List<GanHuoData>>> getGanHuo(@Path("category") String category, @Path("pageIndex") int pageIndex);

    // 获取某天的干货
    @GET("day/{date}")
    Single<HttpResult<DailyList>> getRecentlyGanHuo(@Path("date") String date);

    // 搜索
    @GET("search/query/{keyword}/category/{category}/count/20/page/{pageIndex}")
    Single<HttpResult<List<SearchResult>>> search(@Path("category") String category, @Path("keyword") String keyword, @Path("pageIndex") int pageIndex);

    // 历史
    @GET("history/content/10/{pageIndex}")
    Single<HttpResult<List<Daily>>> getRecently(@Path("pageIndex") int pageIndex);
}
