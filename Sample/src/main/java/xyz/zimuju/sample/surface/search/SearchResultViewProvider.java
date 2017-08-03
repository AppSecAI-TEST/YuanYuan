package xyz.zimuju.sample.surface.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.entity.content.SearchResult;
import xyz.zimuju.sample.surface.gank.activity.WebViewActivity;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:13
 * Desc:
 */
public class SearchResultViewProvider
        extends ItemViewProvider<SearchResult, SearchResultViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.gank_item_search_result, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final SearchResult searchResult) {
        holder.tv_title.setText(searchResult.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.start(holder.itemView.getContext()
                        , searchResult.getDesc(), searchResult.getUrl());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}