package xyz.zimuju.cloudreader.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import xyz.zimuju.cloudreader.R;
import xyz.zimuju.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import xyz.zimuju.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import xyz.zimuju.cloudreader.bean.moviechild.PersonBean;
import xyz.zimuju.cloudreader.databinding.ItemMovieDetailPersonBinding;
import xyz.zimuju.cloudreader.util.PerfectClickListener;
import xyz.zimuju.cloudreader.widget.webview.WebViewActivity;

/**
 * Created by jingbin on 2016/12/10.
 */

public class MovieDetailAdapter extends BaseRecyclerViewAdapter<PersonBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_movie_detail_person);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<PersonBean, ItemMovieDetailPersonBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final PersonBean bean, int position) {
            binding.setPersonBean(bean);
            binding.llItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (bean != null && !TextUtils.isEmpty(bean.getAlt())) {
                        WebViewActivity.loadUrl(v.getContext(), bean.getAlt(), bean.getName());
                    }
                }
            });
        }
    }
}
