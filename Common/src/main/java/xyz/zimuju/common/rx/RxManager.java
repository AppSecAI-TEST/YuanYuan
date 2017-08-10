package xyz.zimuju.common.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxManager {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void add(Disposable m) {
        compositeDisposable.add(m);
    }

    public void clear() {
        compositeDisposable.clear();
    }

}
