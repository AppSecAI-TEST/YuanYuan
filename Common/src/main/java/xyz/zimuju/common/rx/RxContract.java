package xyz.zimuju.common.rx;


import android.content.Context;

import xyz.zimuju.common.basal.BasalPresenter;
import xyz.zimuju.common.basal.BasalView;

public abstract class RxContract<T extends BasalView> implements BasalPresenter<T> {
    protected Context context;
    protected RxManager rxManager = new RxManager();
    protected T basalView;

    @Override
    public void initialize(){
        this.context = basalView.getContext();
    }

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
