package xyz.zimuju.common.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import xyz.zimuju.common.base.BaseView.OnViewClickListener;
import xyz.zimuju.common.defination.AdapterViewPresenter;

public abstract class BaseViewAdapter<T, BV extends BaseView<T>> extends BaseAdapter<T> implements AdapterViewPresenter<T, BV> {
    public OnViewClickListener<T, BV> onViewClickListener;
    private AdapterViewPresenter<T, BV> presenter;

    public BaseViewAdapter(Activity context) {
        super(context);
    }

    /**
     * 为ItemView设置点击View的事件监听
     *
     * @param listener
     * @see OnViewClickListener
     */
    public void setOnViewClickListener(OnViewClickListener<T, BV> listener) {
        onViewClickListener = listener;
    }

    /**
     * @return presenter == null ? this : presenter;
     */
    protected final AdapterViewPresenter<T, BV> getPresenter() {
        return presenter == null ? this : presenter;
    }

    /**
     * 在子类构造方法内使用可重写AdapterViewPresenter里的方法
     *
     * @param presenter
     */
    protected final void setPresenter(AdapterViewPresenter<T, BV> presenter) {
        this.presenter = presenter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressWarnings("unchecked")
        BV bv = convertView == null ? null : (BV) convertView.getTag();
        if (bv == null) {
            bv = getPresenter().createView(position, parent);
            convertView = bv.createView(inflater, position, getItemViewType(position));
            setOnClickListener(bv);//比在bindView里调用效率高。像是小众需求，应该去掉直接在子类针对性地实现？

            convertView.setTag(bv);
        }

        getPresenter().bindView(position, bv);

        return super.getView(position, convertView, parent);
    }

    /**
     * bv的显示方法
     *
     * @param position
     * @param bv
     */
    @Override
    public void bindView(int position, BV bv) {
        bv.bindView(getItem(position), position, getItemViewType(position));
    }


    /**
     * 设置对ItemView点击事件的处理
     *
     * @param bv
     */
    protected void setOnClickListener(final BV bv) {
        if (onViewClickListener != null) {
            bv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onViewClickListener.onViewClick(v, bv);
                }
            });
        }
    }
}
