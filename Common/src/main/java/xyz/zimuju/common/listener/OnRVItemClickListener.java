package xyz.zimuju.common.listener;

import android.view.View;
import android.view.ViewGroup;

/*
 * @description OnRVItemClickListener RecyclerView 的 item 点击事件监听器
 * @author Nathaniel - nathanwriting@126.com
 * @time 2017/06/06 - 22:07
 * @version 1.0.0
 */
public interface OnRVItemClickListener {
    /**
     * position : The position of the view in the adapter.
     * id : The row id of the item that was clicked.
     */
    void onRVItemClick(ViewGroup parent, View itemView, int position);
}