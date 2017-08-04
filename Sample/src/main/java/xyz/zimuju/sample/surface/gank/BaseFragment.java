package xyz.zimuju.sample.surface.gank;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;


public abstract class BaseFragment extends RxFragment {
    private View rootView;

    protected abstract int getLayoutId();

    protected abstract void initData();

    public abstract void refreshData();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView); // initView();
        initData();
        return rootView;
    }

    protected View getRootView() {
        return rootView;
    }

    public Context getContext() {
        return getActivity();
    }

}
