package xyz.zimuju.common.rx;


import xyz.zimuju.common.basal.BasalPresenter;
import xyz.zimuju.common.basal.BasalView;

public class RxContract<T extends BasalView> implements BasalPresenter<T> {
    protected RxManager rxManager = new RxManager();
    protected T basalView;

    @Override
    public void attachView(T view) {
        this.basalView = view;
    }

    @Override
    public void detachView() {
        this.basalView = null;
        rxManager.clear();
    }
}
