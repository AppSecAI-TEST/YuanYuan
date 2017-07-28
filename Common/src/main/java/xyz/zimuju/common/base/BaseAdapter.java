package xyz.zimuju.common.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.defination.OnReachViewBorderListener;
import xyz.zimuju.common.util.CommonUtils;
import xyz.zimuju.common.util.SettingUtil;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    public Activity context;
    public LayoutInflater inflater;
    public Resources resources;
    public List<T> list;
    protected OnReachViewBorderListener onReachViewBorderListener;
    protected int preloadCount = 0;

    public BaseAdapter(Activity context) {
        this.context = context;

        inflater = context.getLayoutInflater();
        resources = context.getResources();
    }

    public List<T> getList() {
        return list;
    }

    public synchronized void refresh(List<T> list) {
        this.list = list == null ? null : new ArrayList<T>(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnReachViewBorderListener(OnReachViewBorderListener onReachViewBorderListener) {
        this.onReachViewBorderListener = onReachViewBorderListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (SettingUtil.preload && onReachViewBorderListener != null && position >= getCount() - 1 - preloadCount) {
            onReachViewBorderListener.onReach(OnReachViewBorderListener.TYPE_BOTTOM, parent);
        }
        return convertView;
    }

    public void showShortToast(int stringResId) {
        CommonUtils.showShortToast(context, stringResId);
    }

    public void showShortToast(String string) {
        CommonUtils.showShortToast(context, string);
    }

    public void toActivity(final Intent intent) {
        CommonUtils.toActivity(context, intent);
    }

    public void toActivity(final Intent intent, final boolean showAnimation) {
        CommonUtils.toActivity(context, intent, showAnimation);
    }

    public void toActivity(final Intent intent, final int requestCode) {
        CommonUtils.toActivity(context, intent, requestCode);
    }

    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        CommonUtils.toActivity(context, intent, requestCode, showAnimation);
    }
}
