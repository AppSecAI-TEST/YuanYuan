package xyz.zimuju.common.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import xyz.zimuju.common.R;


public abstract class BaseBottomWindow extends BaseActivity {
    public static final String INTENT_ITEMS = "INTENT_ITEMS";
    public static final String INTENT_ITEM_IDS = "INTENT_ITEM_IDS";
    public static final String RESULT_TITLE = "RESULT_TITLE";
    public static final String RESULT_ITEM = "RESULT_ITEM";
    public static final String RESULT_ITEM_ID = "RESULT_ITEM_ID";
    private static final String TAG = "BaseBottomWindow";


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @SuppressLint("HandlerLeak")
    public Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseBottomWindow.super.finish();
        }
    };
    protected View vBaseBottomWindowRoot;//子Activity全局背景View

    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private boolean isExit = false;

    /**
     * 如果在子类中调用(即super.initView());则view必须含有initView中初始化用到的id(非@Nullable标记)且id对应的View的类型全部相同；
     * 否则必须在子类initView中重写这个类中initView内的代码(所有id替换成可用id)
     */
    @Override
    public void initView() {// 必须调用
        enterAnim = exitAnim = R.anim.null_anim;

        vBaseBottomWindowRoot = findViewById(R.id.vBaseBottomWindowRoot);

        vBaseBottomWindowRoot.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bottom_window_enter));
    }

    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {// 必须调用

    }

    /**
     * 设置需要返回的结果
     */
    protected abstract void setResult();

    @Override
    public void initEvent() {// 必须调用

        //			vBaseBottomWindowRoot.setOnClickListener(new OnClickListener() {
        //
        //				@Override
        //				public void onClick(View v) {
        //					finish();
        //				}
        //			});

    }

    // 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    // 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onForwardClick(View v) {
        setResult();
        finish();
    }

    /**
     * 带动画退出,并使退出事件只响应一次
     */
    @Override
    public void finish() {
        Log.d(TAG, "finish >>> isExit = " + isExit);
        if (isExit) {
            return;
        }
        isExit = true;

        vBaseBottomWindowRoot.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bottom_window_exit));
        vBaseBottomWindowRoot.setVisibility(View.GONE);

        exitHandler.sendEmptyMessageDelayed(0, 200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        vBaseBottomWindowRoot = null;
    }

    // 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}