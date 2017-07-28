/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.zimuju.common.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.zimuju.common.listener.OnItemChildCheckedChangeListener;
import xyz.zimuju.common.listener.OnItemChildClickListener;
import xyz.zimuju.common.listener.OnItemChildLongClickListener;
import xyz.zimuju.common.viewholder.ListViewHolder;

/*
 * @description MBaseAdapter ：公共的BaseAdapter
 * @author Nathaniel - nathanwriting@126.com
 * @time 2017/06/08 - 22:13
 * @version 1.0.0
 */
public abstract class MBaseAdapter<M> extends BaseAdapter {
    protected int itemLayoutId;
    protected Context context;
    protected List<M> dataList;
    protected OnItemChildClickListener mOnItemChildClickListener;
    protected OnItemChildLongClickListener mOnItemChildLongClickListener;
    protected OnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;

    public MBaseAdapter(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
        dataList = new ArrayList<>();
    }

    @Override
    public M getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder viewHolder = ListViewHolder.getViewHolder(convertView, parent, itemLayoutId);
        viewHolder.getViewHolderHelper().setPosition(position);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(mOnItemChildCheckedChangeListener);

        fillData(viewHolder.getViewHolderHelper(), position, getItem(position));
        return viewHolder.getConvertView();
    }

    // 填充item数据
    protected abstract void fillData(MViewHolderHelper viewHolderHelper, int position, M model);

    // 设置item中的子控件点击事件监听器
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    // 设置item中的子控件长按事件监听器
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    // 设置item子控件选中状态变化事件监听器
    public void setOnItemChildCheckedChangeListener(OnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    // 获取数据集合
    public List<M> getDataList() {
        return dataList;
    }

    // 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
    public void setDataList(List<M> modelList) {
        if (dataList != null) {
            dataList = modelList;
        } else {
            dataList.clear();
        }
        notifyDataSetChanged();
    }

    // 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
    public void addDataList(List<M> modelList) {
        if (modelList != null) {
            dataList.addAll(0, modelList);
            notifyDataSetChanged();
        }
    }

    //  在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
    public void addMoreDataList(List<M> modelList) {
        if (modelList != null) {
            dataList.addAll(dataList.size(), modelList);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        dataList.remove(position);
        notifyDataSetChanged();
    }

    public void removeItem(M model) {
        dataList.remove(model);
        notifyDataSetChanged();
    }


    public void addItem(int position, M model) {
        dataList.add(position, model);
        notifyDataSetChanged();
    }

    public void addFirstItem(M model) {
        addItem(0, model);
    }

    public void addLastItem(M model) {
        addItem(dataList.size(), model);
    }

    public void setItem(int location, M newModel) {
        dataList.set(location, newModel);
        notifyDataSetChanged();
    }

    public void setItem(M oldModel, M newModel) {
        setItem(dataList.indexOf(oldModel), newModel);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Collections.swap(dataList, fromPosition, toPosition);
        notifyDataSetChanged();
    }
}