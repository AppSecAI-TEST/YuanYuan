package xyz.zimuju.sample.surface.gank.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

/*
 * @description BaseActivity ：Activity的基类
 * @author Nathaniel
 * @time 2017/8/3 - 10:49
 * @version 1.0.0
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Context context;

    // 用于在初始化View之前做一些事
    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        context = this;
        super.onCreate(savedInstanceState);
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }


    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected Context getContext() {
        return context;
    }
}
