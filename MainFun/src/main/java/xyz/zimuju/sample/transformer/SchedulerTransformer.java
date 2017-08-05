package xyz.zimuju.sample.transformer;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

public class SchedulerTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {

    @Override
    public CompletableSource apply(@NonNull Completable upstream) {
        return null;
    }

    @Override
    public Publisher<T> apply(@NonNull Flowable<T> upstream) {
        return null;
    }

    @Override
    public MaybeSource<T> apply(@NonNull Maybe<T> upstream) {
        return null;
    }

    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return null;
    }

    @Override
    public SingleSource<T> apply(@NonNull Single<T> upstream) {
        return null;
    }
}