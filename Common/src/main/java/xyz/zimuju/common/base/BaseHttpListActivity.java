package xyz.zimuju.common.base;

import android.util.Log;
import android.view.View;

import java.util.List;

import xyz.zimuju.common.defination.AdapterCallBack;
import xyz.zimuju.common.defination.OnReachViewBorderListener;
import xyz.zimuju.common.defination.OnStopLoadListener;
import xyz.zimuju.common.manager.OnHttpResponseListener;
import xyz.zimuju.common.widget.xlistview.XListView;

/**
 * 基础http获取列表的Activity
 *
 * @param <T>  数据模型(model/JavaBean)类
 * @param <BA> 管理XListView的Adapter
 * @author Lemon
 * @use extends BaseHttpListActivity 并在子类onCreate中lvBaseList.onRefresh();, 具体参考 .UserListFragment
 * @see #getListAsync(int)
 * @see #onHttpResponse(int, String, Exception)
 */
public abstract class BaseHttpListActivity<T, BA extends BaseAdapter> extends BaseListActivity<T, XListView, BA>
        implements OnHttpResponseListener, XListView.IXListViewListener, OnStopLoadListener {
    private static final String TAG = "BaseHttpListActivity";


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {
        super.initView();

        setList((List<T>) null);//ListView需要设置adapter才能显示header和footer; setAdapter调不到子类方法
    }

    /**
     * 设置列表适配器
     *
     * @param callBack
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setList(AdapterCallBack<BA> callBack) {
        super.setList(callBack);
        boolean empty = adapter == null || adapter.isEmpty();
        Log.d(TAG, "setList  adapter empty = " + empty);
        lvBaseList.showFooter(!empty);//放setAdapter中不行，adapter!=null时没有调用setAdapter

        if (adapter != null && adapter instanceof BaseAdapter) {
            adapter.setOnReachViewBorderListener(empty || lvBaseList.isFooterShowing() == false ? null : new OnReachViewBorderListener() {
                @Override
                public void onReach(int type, View v) {
                    if (type == TYPE_BOTTOM) {
                        lvBaseList.onLoadMore();
                    }
                }
            });
        }
    }

    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {
        super.initData();

    }

    /**
     * 将JSON串转为List（已在非UI线程中）
     * *直接JSON.parseArray(json, getCacheClass());可以省去这个方法，但由于可能json不完全符合parseArray条件，所以还是要保留。
     * *比如json只有其中一部分能作为parseArray的字符串时，必须先提取出这段字符串再parseArray
     */
    public abstract List<T> parseArray(String json);


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {// 必须调用
        super.initEvent();
        setOnStopLoadListener(this);

        lvBaseList.setXListViewListener(this);
    }

    /*
     * @param page 用-page作为requestCode
     */
    @Override
    public abstract void getListAsync(int page);

    @Override
    public void onStopRefresh() {
        runUiThread(new Runnable() {

            @Override
            public void run() {
                lvBaseList.stopRefresh();
            }
        });
    }

    @Override
    public void onStopLoadMore(final boolean isHaveMore) {
        runUiThread(new Runnable() {

            @Override
            public void run() {
                lvBaseList.stopLoadMore(isHaveMore);
            }
        });
    }

    /**
     * @param requestCode = -page {@link #getListAsync(int)}
     * @param resultJson
     * @param e
     */
    @Override
    public void onHttpResponse(final int requestCode, final String resultJson, final Exception e) {
        runThread(TAG + "onHttpResponse", new Runnable() {

            @Override
            public void run() {
                int page = 0;
                if (requestCode > 0) {
                    Log.w(TAG, "requestCode > 0, 应该用BaseListFragment#getListAsync(int page)中的page的负数作为requestCode!");
                } else {
                    page = -requestCode;
                }
                List<T> array = parseArray(resultJson);

                if ((array == null || array.isEmpty()) && e != null) {
                    onLoadFailed(page, e);
                } else {
                    onLoadSucceed(page, array);
                }
            }
        });
    }


    // 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    // 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    // 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}