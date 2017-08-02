
package xyz.zimuju.sample.provider;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import xyz.zimuju.sample.engine.service.CommonService;
import xyz.zimuju.sample.http.HttpResult;
import xyz.zimuju.sample.factory.ServiceFactory;
import xyz.zimuju.sample.http.function.ResultFunction;
import xyz.zimuju.sample.http.function.StringFunction;
import xyz.zimuju.sample.http.subscriber.DownLoadSubscribe;
import xyz.zimuju.sample.rx.RxUtils;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:9:22
 */
public class ObservableProvider {

    private CommonService mCommonService;

    private static class DefaultHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    private ObservableProvider() {
        mCommonService = ServiceFactory.getInstance().createService(CommonService.class);

    }

    public static ObservableProvider getDefault() {
        return DefaultHolder.INSTANCE;
    }

    public Single<String> loadString(String url) {
        return mCommonService
                .loadString(url)
                .map(new StringFunction());
    }

    public <T> Single<HttpResult<T>> loadResult(String url) {
        return loadString(url).map(new ResultFunction<T>());
    }

    public void download(String url, final DownLoadSubscribe subscribe) {
        mCommonService
                .download(url)
                .compose(RxUtils.<ResponseBody>all_io_single())
                .subscribe(subscribe);

    }


}
