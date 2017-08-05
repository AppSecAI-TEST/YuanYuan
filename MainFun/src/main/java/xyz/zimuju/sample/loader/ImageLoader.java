package xyz.zimuju.sample.loader;

import android.widget.ImageView;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.provider.GlideImageLoaderProvider;
import xyz.zimuju.sample.provider.ImageLoaderProvider;

public class ImageLoader {

    private static volatile ImageLoaderProvider imageLoaderProvider;

    private static ImageLoaderProvider getProvider() {
        if (imageLoaderProvider == null) {
            synchronized (ImageLoader.class) {
                if (imageLoaderProvider == null) {
                    imageLoaderProvider = new GlideImageLoaderProvider();
                }
            }
        }
        return imageLoaderProvider;
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
