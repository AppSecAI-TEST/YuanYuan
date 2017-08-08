package xyz.zimuju.cloudreader.adapter;

import android.view.ViewGroup;


import xyz.zimuju.cloudreader.R;
import xyz.zimuju.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import xyz.zimuju.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import xyz.zimuju.cloudreader.databinding.ItemEmptyBinding;

/**
 * Created by jingbin on 2016/12/24.
 */

public class EmptyAdapter extends BaseRecyclerViewAdapter<String> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_empty);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemEmptyBinding> {

        public ViewHolder(ViewGroup parent, int item_empty) {
            super(parent, item_empty);
        }

        @Override
        public void onBindViewHolder(String object, int position) {
            binding.setString(object);
        }
    }
}
