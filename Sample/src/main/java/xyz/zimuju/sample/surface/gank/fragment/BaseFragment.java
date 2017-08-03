package xyz.zimuju.sample.surface.gank.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;


public abstract class BaseFragment extends RxFragment {

    private View rootView;
    private Context context;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutResourceID(), container, false);
        initialize();
        return rootView;
    }

    protected abstract int setLayoutResourceID();

    protected void initialize() {
        context = getContext();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int id) {
        return (T) rootView.findViewById(id);
    }


    protected View getContentView() {
        return rootView;
    }

    public Context getContext() {
        return context;
    }

    public void refresh() {
    }

    protected ProgressDialog getProgressDialog() {
        return progressDialog;
    }
}
