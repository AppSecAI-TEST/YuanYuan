package xyz.zimuju.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.R;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.defination.ViewPresenter;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.common.widget.TopTabView;

public abstract class BaseTabActivity extends BaseActivity implements ViewPresenter, OnClickListener, TopTabView.OnTabSelectedListener {
    private static final String TAG = "BaseTabActivity";
    /**
     * == true >> 每次点击相应tab都加载，调用getFragment方法重新对点击的tab对应的fragment赋值。
     * 如果不希望重载，可以setOnTabSelectedListener，然后在onTabSelected内重写点击tab事件。
     */
    protected boolean needReload = false;
    /**
     * 当前显示的tab所在位置，对应fragment所在位置
     */
    protected int currentPosition = 0;
    protected TopTabView topTabView;
    @Nullable
    private TextView tvBaseTabTitle;

    //	//BaseActivity重写setContentView后这个方法一定会被调用，final有无都会导致崩溃，去掉throw Exception也会导致contentView为null而崩溃
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


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Nullable
    private View ivBaseTabReturn;
    @Nullable
    private TextView tvBaseTabReturn;
    @Nullable
    private TextView tvBaseTabForward;
    @Nullable
    private ViewGroup llBaseTabTopRightButtonContainer;
    private ViewGroup llBaseTabTabContainer;
    private Fragment[] fragments;
    @Nullable
    private List<View> topRightButtonList = new ArrayList<View>();

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
    }

    /**
     * @param savedInstanceState
     * @param layoutResID        activity全局视图view的布局资源id，默认值为R.layout.base_tab_activity
     * @return
     * @must 1.不要在子类重复这个类中onCreate中的代码;
     * 2.在子类onCreate中super.onCreate(savedInstanceState, layoutResID);
     * initView();initData();initEvent();
     */
    protected final void onCreate(Bundle savedInstanceState, int layoutResID) {
        onCreate(savedInstanceState, layoutResID, null);
    }

    /**
     * @param savedInstanceState
     * @param listener           this - 滑动返回 ; null - 没有滑动返回
     * @return
     * @must 1.不要在子类重复这个类中onCreate中的代码;
     * 2.在子类onCreate中super.onCreate(savedInstanceState, listener);
     * initView();initData();initEvent();
     */
    protected final void onCreate(Bundle savedInstanceState, OnBottomDragListener listener) {
        onCreate(savedInstanceState, 0, listener);
    }

    /**
     * @param savedInstanceState
     * @param layoutResID        activity全局视图view的布局资源id。 <= 0 ? R.layout.base_tab_activity : layoutResID
     * @param listener           == null ? 没有滑动返回 : 滑动返回
     * @return
     * @must 1.不要在子类重复这个类中onCreate中的代码;
     * 2.在子类onCreate中super.onCreate(savedInstanceState, layoutResID, listener);
     * initView();initData();initEvent();
     */
    protected final void onCreate(Bundle savedInstanceState, int layoutResID, OnBottomDragListener listener) {
        super.onCreate(savedInstanceState);
        super.setContentView(layoutResID <= 0 ? R.layout.base_tab_activity : layoutResID, listener);
    }

    /**
     * 如果在子类中调用(即super.initView());则view必须含有initView中初始化用到的id(非@Nullable标记)且id对应的View的类型全部相同；
     * 否则必须在子类initView中重写这个类中initView内的代码(所有id替换成可用id)
     */
    @Override
    public void initView() {// 必须调用

        tvBaseTabTitle = (TextView) findViewById(R.id.tvBaseTabTitle);

        ivBaseTabReturn = findViewById(R.id.ivBaseTabReturn);
        tvBaseTabReturn = (TextView) findViewById(R.id.tvBaseTabReturn);

        llBaseTabTopRightButtonContainer = (ViewGroup)
                findViewById(R.id.llBaseTabTopRightButtonContainer);

        llBaseTabTabContainer = (ViewGroup) findViewById(R.id.llBaseTabTabContainer);

    }


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 选择下一个tab和fragment
     */
    public void selectNext() {
        select((getCurrentPosition() + 1) % getCount());
    }

    /**
     * 选择tab和fragment
     *
     * @param position
     */
    public void select(int position) {
        topTabView.select(position);
    }

    /**
     * 选择并显示fragment
     *
     * @param position
     */
    public void selectFragment(int position) {
        if (currentPosition == position) {
            if (needReload) {
                if (fragments[position] != null && fragments[position].isAdded()) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.remove(fragments[position]).commit();
                    fragments[position] = null;
                }
            } else {
                if (fragments[position] != null && fragments[position].isVisible()) {
                    Log.w(TAG, "selectFragment currentPosition == position" +
                            " >> fragments[position] != null && fragments[position].isVisible()" +
                            " >> return;	");
                    return;
                }
            }
        }

        if (fragments[position] == null) {
            fragments[position] = getFragment(position);
        }

        //全局的fragmentTransaction因为already committed 崩溃
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragments[currentPosition]);
        if (fragments[position].isAdded() == false) {
            ft.add(R.id.flBaseTabFragmentContainer, fragments[position]);
        }
        ft.show(fragments[position]).commit();

        this.currentPosition = position;
    }

    @Override
    public void initData() {// 必须调用

        if (tvBaseTabTitle != null) {
            tvBaseTabTitle.setVisibility(StringUtils.isNotEmpty(getTitleName(), true) ? View.VISIBLE : View.GONE);
            tvBaseTabTitle.setText(StringUtils.getTrimedString(getTitleName()));
        }

        //返回按钮<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        String returnName = getReturnName();

        if (returnName == null) {
            if (ivBaseTabReturn != null) {
                ivBaseTabReturn.setVisibility(View.GONE);
            }
            if (tvBaseTabReturn != null) {
                tvBaseTabReturn.setVisibility(View.GONE);
            }
        } else {
            boolean isReturnButtonHasName = StringUtils.isNotEmpty(returnName, true);
            if (ivBaseTabReturn != null) {
                ivBaseTabReturn.setVisibility(isReturnButtonHasName ? View.GONE : View.VISIBLE);
            }
            if (tvBaseTabReturn != null) {
                tvBaseTabReturn.setVisibility(isReturnButtonHasName ? View.VISIBLE : View.GONE);
                tvBaseTabReturn.setText(StringUtils.getTrimedString(returnName));
            }
        }
        //返回按钮>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        //前进按钮<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        String forwardName = getForwardName();
        if (StringUtils.isNotEmpty(forwardName, true)) {
            tvBaseTabForward = addTopRightButton(newTopRightTextView(context, StringUtils.getTrimedString(forwardName)));
        }

        if (llBaseTabTopRightButtonContainer != null
                && topRightButtonList != null && topRightButtonList.size() > 0) {

            llBaseTabTopRightButtonContainer.removeAllViews();
            for (View btn : topRightButtonList) {
                llBaseTabTopRightButtonContainer.addView(btn);
            }
        }
        //前进按钮>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        //tab<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        topTabView = new TopTabView(context, getResources());
        llBaseTabTabContainer.removeAllViews();
        llBaseTabTabContainer.addView(topTabView.createView(getLayoutInflater()));
        topTabView.setCurrentPosition(currentPosition);
        topTabView.bindView(getTabNames());
        //tab>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        // fragmentActivity子界面初始化<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        fragments = new Fragment[getCount()];
        selectFragment(currentPosition);

        // fragmentActivity子界面初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }

    /**
     * 获取导航栏标题名
     *
     * @return null - View.GONE; "" - <; "xxx" - "xxx"
     */
    @Override
    @Nullable
    public abstract String getTitleName();

    //top right button <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取导航栏标题名
     *
     * @return null - View.GONE; "" - <; "xxx" - "xxx"
     */
    @Override
    @Nullable
    public abstract String getReturnName();

    /**
     * 获取导航栏标题名
     *
     * @return null - View.GONE; "" - View.GONE; "xxx" - "xxx"
     */
    @Override
    @Nullable
    public abstract String getForwardName();

    /**
     * 添加右上方导航栏按钮
     *
     * @param topRightButton 不会在这个类设置监听,需要自行设置
     * @warn 在initData前使用才有效
     */
    public <V extends View> V addTopRightButton(V topRightButton) {
        if (topRightButton != null) {
            topRightButtonList.add(topRightButton);
        }
        return topRightButton;
    }

    /**
     * 新建右上方导航栏按钮
     *
     * @param context
     * @param drawable
     * @return
     */
    public ImageView newTopRightImageView(Context context, int drawable) {
        return newTopRightImageView(context, getResources().getDrawable(drawable));
    }

    /**
     * 新建右上方导航栏按钮
     *
     * @param context
     * @param drawable
     * @return
     */
    @SuppressLint({"NewApi", "InflateParams"})
    public ImageView newTopRightImageView(Context context, Drawable drawable) {
        ImageView topRightButton = (ImageView) LayoutInflater.from(context).inflate(R.layout.top_right_iv, null);
        topRightButton.setImageDrawable(drawable);
        return topRightButton;
    }

    /**
     * 新建右上方导航栏按钮
     *
     * @param context
     * @param name
     * @return
     */
    @SuppressLint({"NewApi", "InflateParams"})
    public TextView newTopRightTextView(Context context, String name) {
        TextView topRightButton = (TextView) LayoutInflater.from(context).inflate(R.layout.top_right_tv, null);
        topRightButton.setText(name);
        return topRightButton;
    }

    //top right button >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * 获取标签名称数组
     *
     * @return
     */
    protected abstract String[] getTabNames();

    /**
     * 获取新的Fragment
     *
     * @param position
     * @return
     */
    protected abstract Fragment getFragment(int position);


    /**
     * 获取Tab(或Fragment)的数量
     *
     * @return
     */
    public int getCount() {
        return topTabView == null ? 0 : topTabView.getCount();
    }

    /**
     * 获取当前Tab(或Fragment)的位置
     *
     * @return
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    public TextView getCurrentTab() {
        return topTabView == null ? null : topTabView.getCurrentTab();
    }

    public Fragment getCurrentFragment() {
        return fragments[currentPosition];
    }


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {// 必须调用

        if (ivBaseTabReturn != null) {
            ivBaseTabReturn.setOnClickListener(this);
        }
        if (tvBaseTabReturn != null) {
            tvBaseTabReturn.setOnClickListener(this);
        }
        if (tvBaseTabForward != null) {
            tvBaseTabForward.setOnClickListener(new OnClickListener() {//没有id

                @Override
                public void onClick(View v) {
                    onForwardClick(v);
                }
            });
        }

        topTabView.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TextView tvTab, int position, int id) {
        selectFragment(position);
    }

    // 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBaseTabReturn || v.getId() == R.id.tvBaseTabReturn) {
            finish();
        }
    }


    // 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topTabView = null;
        fragments = null;

        ivBaseTabReturn = null;
        tvBaseTabReturn = null;
        llBaseTabTopRightButtonContainer = null;
        llBaseTabTabContainer = null;

        tvBaseTabTitle = null;

        currentPosition = 0;
        needReload = false;

        topRightButtonList = null;
    }

    // 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}