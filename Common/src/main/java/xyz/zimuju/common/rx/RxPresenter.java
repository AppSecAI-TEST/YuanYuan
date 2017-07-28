package xyz.zimuju.common.rx;


import xyz.zimuju.common.basal.BasalPresenter;
import xyz.zimuju.common.basal.BasalView;

public class RxPresenter<T extends BasalView> implements BasalPresenter<T> {

    public RxManager rxManager = new RxManager();
    protected T mvpView;

    @Override
    public void attachView(T view) {
        this.mvpView = view;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    @Override
    public void onDestroy() {
        rxManager.clear();
    }
}
