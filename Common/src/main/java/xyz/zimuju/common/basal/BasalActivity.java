package xyz.zimuju.common.basal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import xyz.zimuju.common.controller.ActivityController;
import xyz.zimuju.common.util.ToastUtils;

/*
 * @description BasalActivity :Activity的基类 如果不想用ButterKnife 而是用findViewById 则打开 initView()
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/9/10-16:29
 * @version v1.0.0
 */
public abstract class BasalActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void viewOption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityController.initController().addActivity(this);
        ButterKnife.bind(this);
        initData();
        viewOption();
    }

    protected Context getContext() {
        return getActivity();
    }

    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        ActivityController.initController().removeActivity(this);
        super.onDestroy();
    }

    protected void showToast(String message) {
        ToastUtils.showToast(getContext(), message);
    }

}
