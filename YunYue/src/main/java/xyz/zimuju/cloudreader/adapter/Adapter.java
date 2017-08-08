package xyz.zimuju.cloudreader.adapter;

import android.view.ViewGroup;

import xyz.zimuju.cloudreader.R;
import xyz.zimuju.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import xyz.zimuju.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import xyz.zimuju.cloudreader.databinding.ItemListBinding;

/**
 * Created by jingbin on 2016/11/30.
 */

public class Adapter extends BaseRecyclerViewAdapter<String> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemListBinding> {

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindViewHolder(String object, int position) {
            binding.tvText.setText("测试:  " + object);
        }
    }
}
