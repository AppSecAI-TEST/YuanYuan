package xyz.zimuju.sample.surface.content;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import xyz.zimuju.common.base.BaseTabActivity;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.widget.WebViewActivity;
import xyz.zimuju.sample.R;

public class DemoTabActivity extends BaseTabActivity implements View.OnClickListener, OnBottomDragListener {

    public static Intent createIntent(Context context) {
        return new Intent(context, DemoTabActivity.class);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this);

        //		needReload = true;

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //示例代码<<<<<<<<
    //示例代码>>>>>>>>
    @Override
    public void initView() {//必须在onCreate方法内调用
        super.initView();

        //示例代码<<<<<<<<
        addTopRightButton(newTopRightImageView(context, R.mipmap.add_small)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShortToast("添加");
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    @Nullable
    public String getTitleName() {
        return "账单";
    }

    @Override
    @Nullable
    public String getReturnName() {
        return "";
    }

    @Override
    @Nullable
    public String getForwardName() {
        return "了解";
    }

    @Override
    protected String[] getTabNames() {
        return new String[]{"全部", "收入", "支出"};
    }

    @Override
    protected Fragment getFragment(int position) {
        DemoListFragment fragment = DemoListFragment.createInstance();
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(DemoListFragment.ARGUMENT_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void initEvent() {
        super.initEvent();
        topTabView.setOnTabSelectedListener(this);
        for (int i = 0; i < getCount(); i++) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (isRunning()) {
                        selectNext();
                    }
                }
            }, 1000 * (i + 1));
        }
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            toActivity(WebViewActivity.createIntent(context, "百度首页", "www.baidu.com"));
            return;
        }
        finish();
    }

    @Override
    public void onTabSelected(TextView tvTab, int position, int id) {
        super.onTabSelected(tvTab, position, id);
        showShortToast("onTabSelected  position = " + position);
    }
}