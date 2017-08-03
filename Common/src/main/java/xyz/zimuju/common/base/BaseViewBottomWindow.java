package xyz.zimuju.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.zimuju.common.R;
import xyz.zimuju.common.defination.ViewPresenter;
import xyz.zimuju.common.util.StringUtils;

public abstract class BaseViewBottomWindow<T, BV extends BaseView<T>> extends BaseBottomWindow implements ViewPresenter {



    protected ViewGroup llBaseViewBottomWindowContainer;
    @Nullable
    protected TextView tvBaseViewBottomWindowReturn;

    //	//重写setContentView后这个方法一定会被调用，final有无都会导致崩溃，去掉throw Exception也会导致contentView为null而崩溃
    //	//防止子类中setContentView <<<<<<<<<<<<<<<<<<<<<<<<
    //	/**
    //	 * @warn 不支持setContentView，传界面布局请使用onCreate(Bundle savedInstanceState, int layoutResID)等方法
    //	 */
    //	@Override
    //	public final void setContentView(int layoutResID) {
    //		setContentView(null);
    //	}
    //	/**
    //	 * @warn 不支持setContentView，传界面布局请使用onCreate(Bundle savedInstanceState, int layoutResID)等方法
    //	 */
    //	@Override
    //	public final void setContentView(View view) {
    //		setContentView(null, null);
    //	}
    //	/**
    //	 * @warn 不支持setContentView，传界面布局请使用onCreate(Bundle savedInstanceState, int layoutResID)等方法
    //	 */
    //	@Override
    //	public final void setContentView(View view, LayoutParams params) {
    //		throw new UnsupportedOperationException(TAG + "不支持setContentView" +
    //				"，传界面布局请使用onCreate(Bundle savedInstanceState, int layoutResID)等方法");
    //	}
    //	//防止子类中setContentView >>>>>>>>>>>>>>>>>>>>>>>>>


    @Nullable
    protected TextView tvBaseViewBottomWindowForward;
    protected T data;
    protected BV containerView;

    /**
     * @param savedInstanceState
     * @return
     * @must 1.不要在子类重复这个类中onCreate中的代码;
     * 2.在子类onCreate中super.onCreate(savedInstanceState);
     * initView();initData();initEvent();
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, 0);
        super.onCreate(savedInstanceState);
    }



    /**
     * @param savedInstanceState
     * @param layoutResID        activity全局视图view的布局资源id。 <= 0 ? R.layout.base_view_bottom_window : layoutResID
     * @return
     * @must 1.不要在子类重复这个类中onCreate中的代码;
     * 2.在子类onCreate中super.onCreate(savedInstanceState, layoutResID, listener);
     * initView();initData();initEvent();
     */
    protected final void onCreate(Bundle savedInstanceState, int layoutResID) {
        super.onCreate(savedInstanceState);
        super.setContentView(layoutResID <= 0 ? R.layout.base_view_bottom_window : layoutResID);
    }

    /**
     * 如果在子类中调用(即super.initView());则view必须含有initView中初始化用到的id(非@Nullable标记)且id对应的View的类型全部相同；
     * 否则必须在子类initView中重写这个类中initView内的代码(所有id替换成可用id)
     */
    @Override
    public void initView() {// 必须调用
        super.initView();
        autoSetTitle();

        llBaseViewBottomWindowContainer = (ViewGroup) findViewById(R.id.llBaseViewBottomWindowContainer);

        tvBaseViewBottomWindowReturn = (TextView) findViewById(R.id.tvBaseViewBottomWindowReturn);
        tvBaseViewBottomWindowForward = (TextView) findViewById(R.id.tvBaseViewBottomWindowForward);
    }

    @Override
    public void initData() {
        super.initData();

        if (tvBaseTitle != null) {
            String title = getIntent().getStringExtra(INTENT_TITLE);
            if (StringUtils.isNotEmpty(title, true) == false) {
                title = getTitleName();
            }
            tvBaseTitle.setVisibility(StringUtils.isNotEmpty(title, true) ? View.VISIBLE : View.GONE);
            tvBaseTitle.setText(StringUtils.getTrimedString(title));
        }

        if (tvBaseViewBottomWindowReturn != null && StringUtils.isNotEmpty(getReturnName(), true)) {
            tvBaseViewBottomWindowReturn.setText(StringUtils.getCurrentString());
        }
        if (tvBaseViewBottomWindowForward != null && StringUtils.isNotEmpty(getForwardName(), true)) {
            tvBaseViewBottomWindowForward.setText(StringUtils.getCurrentString());
        }


        llBaseViewBottomWindowContainer.removeAllViews();
        if (containerView == null) {
            containerView = createView();
            llBaseViewBottomWindowContainer.addView(containerView.createView(inflater));
        }
        containerView.bindView(null);
    }

    /**
     * 创建新的内容View
     *
     * @return
     */
    protected abstract BV createView();


    @Override
    public void initEvent() {// 必须调用
        super.initEvent();

    }



    @Override
    protected void onDestroy() {
        data = null;
        llBaseViewBottomWindowContainer.removeAllViews();
        if (containerView != null) {
            containerView.onDestroy();
        }

        super.onDestroy();

        llBaseViewBottomWindowContainer = null;
        containerView = null;
    }
}