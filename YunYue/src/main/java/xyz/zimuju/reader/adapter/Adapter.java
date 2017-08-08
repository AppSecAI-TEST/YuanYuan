package xyz.zimuju.reader.adapter;

import android.view.ViewGroup;

import com.example.jingbin.cloudreader.databinding.ItemListBinding;

import xyz.zimuju.reader.R;
import xyz.zimuju.reader.base.BaseRecyclerViewAdapter;
import xyz.zimuju.reader.base.BaseRecyclerViewHolder;


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
