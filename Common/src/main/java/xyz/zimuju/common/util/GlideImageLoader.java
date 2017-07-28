package xyz.zimuju.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 *
 */
public class GlideImageLoader {

    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }


    public static void load(Context context, String url, ImageView iv, int loadingPic) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(loadingPic)
                .into(iv);
    }


}
