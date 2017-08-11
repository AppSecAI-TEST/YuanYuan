package xyz.zimuju.sample.http.subscriber;

import android.support.annotation.NonNull;

import java.util.concurrent.CancellationException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import xyz.zimuju.sample.entity.HttpResult;

public abstract class HttpResultSubscriber<T> implements SingleObserver<HttpResult<T>> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull HttpResult<T> result) {
        if (!result.error) {
            onSuccessful(result.results);
        } else {
            onFailed(new Throwable("error=true"));
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            // 处理RxLife取消订阅的问题,这实际上并不是一个真正的错误
            // 问题链接:https://github.com/trello/RxLifecycle/tree/2.x#unsubscription
            if (!(e instanceof CancellationException)) {
                e.printStackTrace();
                if (e.getMessage() == null) {
                    onFailed(new Throwable(e.toString()));
                } else {
                    onFailed(new Throwable(e.getMessage()));
                }
            }
        } else {
            onFailed(new Exception("null message"));
        }
    }

    public abstract void onSuccessful(T t);

    public abstract void onFailed(Throwable e);
}
