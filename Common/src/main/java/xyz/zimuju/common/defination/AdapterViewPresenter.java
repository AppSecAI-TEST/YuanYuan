package xyz.zimuju.common.defination;

import android.view.ViewGroup;

public interface AdapterViewPresenter<T, V> {

    /**
     * 生成新的BV
     *
     * @param position
     * @param parent
     * @return
     */
    V createView(int position, ViewGroup parent);

    /**
     * 设置BV显示
     *
     * @param position
     * @param bv
     * @return
     */
    void bindView(int position, V bv);

}
