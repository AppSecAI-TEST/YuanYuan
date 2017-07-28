package xyz.zimuju.common.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.zimuju.common.base.MViewHolderHelper;

public class ListViewHolder {
    protected View convertView;
    protected MViewHolderHelper mViewHolderHelper;
    protected Context context;

    private ListViewHolder(ViewGroup parent, int layoutId) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutId, parent, false);
        convertView.setTag(this);
        mViewHolderHelper = new MViewHolderHelper(parent, convertView);
    }

    public static ListViewHolder getViewHolder(View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ListViewHolder(parent, layoutId);
        }
        return (ListViewHolder) convertView.getTag();
    }

    public MViewHolderHelper getViewHolderHelper() {
        return mViewHolderHelper;
    }

    public View getConvertView() {
        return convertView;
    }
}