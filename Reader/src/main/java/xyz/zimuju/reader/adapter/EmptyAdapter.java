package xyz.zimuju.reader.adapter;

import android.view.ViewGroup;

import com.example.jingbin.cloudreader.databinding.ItemEmptyBinding;

import xyz.zimuju.reader.R;
import xyz.zimuju.reader.base.BaseRecyclerViewAdapter;
import xyz.zimuju.reader.base.BaseRecyclerViewHolder;


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
