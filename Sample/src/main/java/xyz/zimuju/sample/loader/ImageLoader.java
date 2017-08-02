package xyz.zimuju.sample.loader;

import android.widget.ImageView;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.provider.GlideImageLoaderProvider;
import xyz.zimuju.sample.provider.ImageLoaderProvider;

public class ImageLoader {

    private static volatile ImageLoaderProvider mProvider;

    private static ImageLoaderProvider getProvider() {
        if (mProvider == null) {
            synchronized (ImageLoader.class) {
                if (mProvider == null) {
                    mProvider = new GlideImageLoaderProvider();
                }
            }
        }
        return mProvider;
    }

    public static void displayImage(ImageView iv, String url) {
        ImageRequest request = new ImageRequest.Builder()
                .url(url)
                .imgView(iv)
                .placeHolder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .create();
        getProvider().loadImage(request);
    }

}
