package xyz.zimuju.common.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.util.CommonUtils;


public abstract class BaseView<T> {
    public Activity context;
    public Resources resources;
    public OnDataChangedListener onDataChangedListener;//数据改变回调监听回调的实例
    public OnTouchListener onTouchListener;//接触View回调监听回调的实例
    public OnClickListener onClickListener;//点击View回调监听回调的实例
    public OnLongClickListener onLongClickListener;//长按View回调监听回调的实例
    /**
     * 子类整个视图,可在子类直接使用
     *
     * @must createView方法内对其赋值且不能为null
     */
    protected View convertView = null;
    protected List<View> onClickViewList;
    /**
     * 视图类型，部分情况下需要根据viewType使用不同layout，对应Adapter的itemViewType
     */
    protected int viewType = 0;
    /**
     * data在列表中的位置
     *
     * @must 只使用bindView(int position, T data)方法来设置position，保证position与data对应正确
     */
    protected int position = 0;
    protected T data = null;

    public BaseView(Activity context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    /**
     * 设置数据改变事件监听回调
     *
     * @param listener
     */
    public void setOnDataChangedListener(OnDataChangedListener listener) {
        onDataChangedListener = listener;
    }

    /**
     * 设置接触View事件监听回调
     *
     * @param listener
     */
    public void setOnTouchListener(OnTouchListener listener) {
        onTouchListener = listener;
    }

    /**
     * 设置点击View事件监听回调
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
        if (onClickViewList != null) {
            for (View v : onClickViewList) {
                if (v != null) {
                    v.setOnClickListener(listener);
                }
            }
        }
    }

    /**
     * 设置长按View事件监听回调
     *
     * @param listener
     */
    public void setOnLongClickListener(OnLongClickListener listener) {
        onLongClickListener = listener;
    }

    /**
     * 通过id查找并获取控件，使用时不需要强转
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) convertView.findViewById(id);
    }

    /**
     * 通过id查找并获取控件，并setOnClickListener
     *
     * @param id
     * @param listener
     * @return
     */
    public <V extends View> V findViewById(int id, OnClickListener listener) {
        V v = findViewById(id);
        v.setOnClickListener(listener);
        if (onClickViewList == null) {
            onClickViewList = new ArrayList<View>();
        }
        onClickViewList.add(v);
        return v;
    }

    /**
     * 获取data在列表中的位置
     */
    public int getPosition() {
        return position;
    }

    /**
     * 创建一个新的View
     *
     * @param inflater - @NonNull，布局解释器
     * @param viewType - 视图类型，部分情况下需要根据viewType使用不同layout
     * @return
     */
    public View createView(LayoutInflater inflater, int position, int viewType) {
        this.position = position;
        this.viewType = viewType;
        return createView(inflater);
    }

    /**
     * 创建一个新的View
     *
     * @param inflater - @NonNull，布局解释器
     * @return
     */
    public abstract View createView(LayoutInflater inflater);

    /**
     * 获取convertView的宽度
     *
     * @return
     * @warn 只能在createView后使用
     */
    public int getWidth() {
        return convertView.getWidth();
    }

    /**
     * 获取convertView的高度
     *
     * @return
     * @warn 只能在createView后使用
     */
    public int getHeight() {
        return convertView.getHeight();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * 设置并显示内容，建议在子类bindView内this.data = data;
     *
     * @param data     - 传入的数据
     * @param position - data在列表中的位置
     * @param viewType - 视图类型，部分情况下需要根据viewType使用不同layout
     * @warn 只能在createView后使用
     */
    public void bindView(T data, int position, int viewType) {
        this.position = position;
        this.viewType = viewType;
        bindView(data);
    }

    /**
     * 设置并显示内容，建议在子类bindView内this.data = data;
     *
     * @param data - 传入的数据
     * @warn 只能在createView后使用
     */
    public abstract void bindView(T data);

    /**
     * 获取可见性
     *
     * @return 可见性 (View.VISIBLE, View.GONE, View.INVISIBLE);
     * @warn 只能在createView后使用
     */
    public int getVisibility() {
        return convertView.getVisibility();
    }

    /**
     * 设置可见性
     *
     * @param visibility - 可见性 (View.VISIBLE, View.GONE, View.INVISIBLE);
     * @warn 只能在createView后使用
     */
    public void setVisibility(int visibility) {
        convertView.setVisibility(visibility);
    }

    /**
     * 设置背景
     *
     * @param resId
     * @warn 只能在createView后使用
     */
    public void setBackground(int resId) {
        if (resId > 0 && convertView != null) {
            try {
                convertView.setBackgroundResource(resId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final Resources getResources() {
        if (resources == null) {
            resources = context.getResources();
        }
        return resources;
    }

    public String getString(int id) {
        return getResources().getString(id);
    }


    //	/**性能不好
    //	 * @param id
    //	 * @param s
    //	 */
    //	public void setText(int id, String s) {
    //		TextView tv = (TextView) findViewById(id);
    //		tv.setText(s);
    //	}


    //resources方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public int getColor(int id) {
        return getResources().getColor(id);
    }

    public Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    public float getDimension(int id) {
        return getResources().getDimension(id);
    }

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param stringResId
     */
    public void showShortToast(int stringResId) {
        CommonUtils.showShortToast(context, stringResId);
    }

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param string
     */
    public void showShortToast(String string) {
        CommonUtils.showShortToast(context, string);
    }
    //resources方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //show short toast 方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(final Intent intent) {
        CommonUtils.toActivity(context, intent);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final boolean showAnimation) {
        CommonUtils.toActivity(context, intent, showAnimation);
    }
    //show short toast 方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(final Intent intent, final int requestCode) {
        CommonUtils.toActivity(context, intent, requestCode);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        CommonUtils.toActivity(context, intent, requestCode, showAnimation);
    }

    /**
     * 销毁并回收内存，建议在对应的View占用大量内存时使用
     *
     * @warn 只能在UI线程中调用
     */
    public void onDestroy() {
        if (convertView != null) {
            try {
                convertView.destroyDrawingCache();
            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView = null;
        }

        onDataChangedListener = null;
        onTouchListener = null;
        onClickListener = null;
        onLongClickListener = null;
        onClickViewList = null;

        data = null;
        position = 0;

        context = null;
    }

    /**
     * 点击View的事件监听回调，主要是为了activity或fragment间接通过adapter接管baseView的点击事件
     *
     * @param <T>
     * @param <BV>
     * @must 子类重写setOnClickListener方法且view.setOnClickListener(listener)事件统一写在这个方法里面
     */
    public interface OnViewClickListener<T, BV extends BaseView<T>> {
        /**
         * onClick(v)事件由这个方法接管
         *
         * @param v
         * @param bv
         */
        void onViewClick(View v, BV bv);
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 数据改变回调接口
     * (Object) getData() - 改变的数据
     */
    public interface OnDataChangedListener {
        void onDataChanged();
    }

}
