package xyz.zimuju.common.basal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.listener.OnRVItemClickListener;
import xyz.zimuju.common.listener.OnRVItemLongClickListener;


/*
 * @description BaseRecycleViewAdapter ： RecyclerView 的适配器的基类，添加头部和底部还没有实现
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/8/16-9:59
 * @version v1.0.0
 */
public abstract class BasalRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected static final int ITEM_TYPE_HEADER = 0x001;
    protected static final int ITEM_TYPE_CONTENT = 0x002;
    protected static final int ITEM_TYPE_FOOTER = 0x003;
    protected static final int ITEM_TYPE_EMPTY = 0x004;

    protected List<T> dataList;

    private OnRVItemClickListener onRVItemClickListener;
    private OnRVItemLongClickListener onRVItemLongClickListener;

    private View headerLayout;
    private View footerLayout;
    private View emptyLayout;

    public BasalRecyclerViewAdapter(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = 0;
        if (dataList.size() <= 0) {
            return ITEM_TYPE_EMPTY;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return  dataList.size() <= 0 ? 0 : dataList.size();
    }

    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener) {
        this.onRVItemClickListener = onRVItemClickListener;
    }

    public void setOnRVItemLongClickListener(OnRVItemLongClickListener onRVItemLongClickListener) {
        this.onRVItemLongClickListener = onRVItemLongClickListener;
    }

    public List<T> getDataList() {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    protected View getView(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

}
