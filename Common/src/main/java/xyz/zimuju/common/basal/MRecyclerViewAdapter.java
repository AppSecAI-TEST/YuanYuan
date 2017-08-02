package xyz.zimuju.common.basal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class MRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> dataList = new ArrayList<>();
    protected OnItemClickListener onItemClickListener;
    protected Context context;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected View getView(int layoutId, ViewGroup parent) {
        context = parent.getContext();
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
