package xyz.zimuju.sample.surface.home.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.entity.content.DailyHeader;
import xyz.zimuju.sample.loader.ImageLoader;
import xyz.zimuju.sample.surface.gank.activity.ViewPicActivity;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:22:23
 */
public class DailyViewItemHeaderProvider
        extends ItemViewProvider<DailyHeader, DailyViewItemHeaderProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.gank_item_daily_item_header, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final DailyHeader recentlyHeader) {
        //holder.tv_title.setText(recentlyHeader.getTitle());
        ImageLoader.displayImage(holder.iv_img, recentlyHeader.getImgUrl());
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPicActivity.start(holder.iv_img.getContext(), holder.iv_img, recentlyHeader.getImgUrl());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_img;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}