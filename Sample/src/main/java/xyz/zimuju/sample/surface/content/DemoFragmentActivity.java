package xyz.zimuju.sample.surface.content;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.sample.R;

public class DemoFragmentActivity extends BaseActivity implements OnBottomDragListener {
    public static final String INTENT_USER_ID = "INTENT_USER_ID";
    private DemoFragment demoFragment;

    public static Intent createIntent(Context context, long userId) {
        return new Intent(context, DemoFragmentActivity.class).putExtra(INTENT_USER_ID, userId);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO demo_fragment_activity改为你所需要的layout文件；传this是为了全局滑动返回
        setContentView(R.layout.demo_fragment_activity, this);

        initView();
        initData();
        initEvent();

    }

    @Override
    public void initView() {
        autoSetTitle();

    }

    @Override
    public void initData() {

        demoFragment = DemoFragment.createInstance(getIntent().getLongExtra(INTENT_USER_ID, 0));

        fragmentManager.beginTransaction()
                .add(R.id.flDemoFragmentActivityContainer, demoFragment)
                .show(demoFragment)
                .commit();
    }


    @Override
    public void initEvent() {//必须在onCreate方法内调用

    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }
}