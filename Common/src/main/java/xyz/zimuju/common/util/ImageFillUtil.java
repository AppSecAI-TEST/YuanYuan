package xyz.zimuju.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import xyz.zimuju.common.R;
import xyz.zimuju.common.transform.CropSquareTransform;
import xyz.zimuju.common.transform.GlideCircleTransform;
import xyz.zimuju.common.transform.GlideRoundTransform;
import xyz.zimuju.common.transform.PicassoCircleTransform;
import xyz.zimuju.common.transform.PicassoRoundTransform;

/*
 * @description ImageFillUtil : 图片的填充工具类：
 *                              注意Picasso占内存且在加载的时候会有闪烁，而Glide不占内存不闪烁，但会引起图片的失真
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/8/17-22:45
 * @version v1.0.0
 */
public class ImageFillUtil {
    public static final int TYPE_NORMAL = 0; // 正常，不做任何处理
    public static final int TYPE_CIRCLE = 1; // 圆形图片
    public static final int TYPE_ROUND = 2; // 圆角图片
    private static final int IMAGE_DEFAULT_SCALE_SIZE = 400;
    private static final int IMAGE_DEFAULT_RADIUS = 10; // 圆角半径
    private static final int IMAGE_DEFAULT_MARGIN = 0; // 外边距
    private static RequestManager glideRequest;
    private static Picasso picassoRequest;

    /*
     * @function 加载资源文件
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-12-20-下午2:52
     * @parameters  Context context 上下文对象
     *              ImageView imageView 图片加载的ImageView 对象
     *              int drawableResId 图片的资源Id
     *              int type 加载的类型
     *              int radius 圆角的半径
     *              int margin 图片的外边距
     * @return void
     * @version v1.0.0
     */
    public static void setPicassoImage(Context context, ImageView imageView, int drawableResId, int type, int radius, int margin) {
        picassoRequest = Picasso.with(context);
        switch (type) {
            case TYPE_NORMAL:
                picassoRequest.load(drawableResId);
                break;

            case TYPE_CIRCLE:
                picassoRequest.load(drawableResId)
                        .transform(new PicassoCircleTransform())
                        .into(imageView);
                break;

            case TYPE_ROUND:
                picassoRequest.load(drawableResId)
                        .transform(new PicassoRoundTransform(radius, margin))
                        .into(imageView);
                break;
        }
    }

    /*
     * @function 加载资源文件
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-12-20-下午2:52
     * @parameters  Context context 上下文对象
     *              ImageView imageView 图片加载的ImageView 对象
     *              int drawableResId 图片的资源Id
     *              int type 加载的类型
     *              int radius 圆角的半径
     * @return void
     * @version v1.0.0
     */
    public static void setPicassoImage(Context context, ImageView imageView, int drawableResId, int type) {
        picassoRequest = Picasso.with(context);
        switch (type) {
            case TYPE_NORMAL:
                picassoRequest.load(drawableResId);
                break;

            case TYPE_CIRCLE:
                picassoRequest.load(drawableResId)
                        .transform(new PicassoCircleTransform())
                        .into(imageView);
                break;

            case TYPE_ROUND:
                picassoRequest.load(drawableResId)
                        .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                        .into(imageView);
                break;
        }
    }

    public static void setPicassoImage(Context context, ImageView imageView, String imageUrl, int type, int radius, int margin) {
        picassoRequest = Picasso.with(context);
        switch (type) {
            case TYPE_NORMAL:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .into(imageView);
                    }
                }
                break;

            case TYPE_CIRCLE:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .transform(new PicassoCircleTransform())
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoCircleTransform())
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoCircleTransform())
                                .into(imageView);
                    }
                }
                break;

            case TYPE_ROUND:
                if (radius == 0) {
                    radius = 10;
                }

                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .transform(new PicassoRoundTransform(radius, margin))
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoRoundTransform(radius, margin))
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoRoundTransform(radius, margin))
                                .into(imageView);
                    }
                }
                break;
        }
    }

    public static void setPicassoImage(Context context, ImageView imageView, String imageUrl, int type) {
        picassoRequest = Picasso.with(context);
        switch (type) {
            case TYPE_NORMAL:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .into(imageView);
                    }
                }
                break;

            case TYPE_CIRCLE:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_load_failed)
                            .transform(new PicassoCircleTransform())
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoCircleTransform())
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoCircleTransform())
                                .into(imageView);
                    }
                }
                break;

            case TYPE_ROUND:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                                .into(imageView);
                    }
                }
                break;
        }

    }

    /*
         * @function 加载网络图片并对其进行压缩
         * @author Nathaniel-nathanwriting@126.com
         * @time 16-12-20-下午2:50
         * @parameters  Context context 上下文对象
         *              ImageView imageView 图片加载对象
         *              String imageUrl 图片的路径
         * @return void
         * @version v1.0.0
         */
    public static void setPicassoImage(Context context, ImageView imageView, String imageUrl) {
        picassoRequest = Picasso.with(context);
        if (TextUtils.isEmpty(imageUrl)) {
            picassoRequest.load(R.mipmap.icon_image_error)
                    .into(imageView);
        } else {
            // 这里用正则表达式来匹配
            if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                picassoRequest.load(imageUrl)
                        .placeholder(R.mipmap.icon_image_error)
                        .error(R.mipmap.icon_image_error)
                        .into(imageView);
            } else {
                picassoRequest.load("file://" + imageUrl)
                        .placeholder(R.mipmap.icon_image_error)
                        .into(imageView);
            }
        }
    }

    /*
     * @function 加载网络图片并对其进行压缩
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-12-20-下午2:50
     * @parameters  Context context 上下文对象
     *              ImageView imageView 图片加载对象
     *              String imageUrl 图片的路径
     *              int type 加载类型
     *              boolean isScale 是否压缩
     * @return void
     * @version v1.0.0
     */
    public static void setPicassoImage(Context context, ImageView imageView, String imageUrl, int type, boolean isScale) {
        picassoRequest = Picasso.with(context);
        switch (type) {
            case TYPE_NORMAL:
                if (isScale) {
                    if (TextUtils.isEmpty(imageUrl)) {
                        picassoRequest.load(imageUrl)
                                .transform(new CropSquareTransform(IMAGE_DEFAULT_SCALE_SIZE, IMAGE_DEFAULT_SCALE_SIZE))
                                .into(imageView);
                    } else {
                        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                            picassoRequest.load(imageUrl)
                                    .placeholder(R.mipmap.icon_image_error)
                                    .error(R.mipmap.icon_image_error)
                                    .transform(new CropSquareTransform(IMAGE_DEFAULT_SCALE_SIZE, IMAGE_DEFAULT_SCALE_SIZE))
                                    .into(imageView);
                        } else {
                            picassoRequest.load("file://" + imageUrl)
                                    .placeholder(R.mipmap.icon_image_error)
                                    .error(R.mipmap.icon_image_error)
                                    .transform(new CropSquareTransform(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context)))
                                    .into(imageView);
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(imageUrl)) {
                        picassoRequest.load(R.mipmap.icon_image_error)
                                .into(imageView);
                    } else {
                        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                            picassoRequest.load(imageUrl)
                                    .placeholder(R.mipmap.icon_image_error)
                                    .error(R.mipmap.icon_image_error)
                                    .into(imageView);
                        } else {
                            picassoRequest.load("file://" + imageUrl)
                                    .placeholder(R.mipmap.icon_image_error)
                                    .error(R.mipmap.icon_image_error)
                                    .into(imageView);
                        }
                    }
                }
                break;

            case TYPE_CIRCLE:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .transform(new PicassoCircleTransform())
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoCircleTransform())
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .transform(new PicassoCircleTransform())
                                .error(R.mipmap.icon_image_error)
                                .into(imageView);
                    }
                }
                break;

            case TYPE_ROUND:
                if (TextUtils.isEmpty(imageUrl)) {
                    picassoRequest.load(R.mipmap.icon_image_error)
                            .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        picassoRequest.load(imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                                .into(imageView);
                    } else {
                        picassoRequest.load("file://" + imageUrl)
                                .placeholder(R.mipmap.icon_image_error)
                                .error(R.mipmap.icon_image_error)
                                .transform(new PicassoRoundTransform(IMAGE_DEFAULT_RADIUS, IMAGE_DEFAULT_MARGIN))
                                .into(imageView);
                    }
                }
                break;
        }

    }

    /*
     * @function 用Picasso加载图片，根据是否压缩填充到ImageView
     * @author Nathaniel-nathanwriting@126.com
     * @time 16-12-12-上午10:11
     * @parameters  Context context ： 上下文
     *              ImageView imageView ： 要填充的ImageView对象
     *              String imageUrl：图片的路径
     *              boolean isScale ： 是否压缩，如果传入的true，表示是小图标，此时宽高是200*200，否则以屏幕宽高填充
     * @return void
     * @version v1.0.0
     */
    public static void setPicassoImage(Context context, ImageView imageView, String imageUrl, boolean isScale) {
        picassoRequest = Picasso.with(context);
        // 这里用正则表达式来匹配
        if (isScale) {
            if (TextUtils.isEmpty(imageUrl)) {
                picassoRequest.load(R.mipmap.icon_image_error)
                        .into(imageView);
            } else if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                picassoRequest.load(imageUrl)
                        .placeholder(R.mipmap.icon_image_error)
                        .error(R.mipmap.icon_image_error)
                        .transform(new CropSquareTransform(400, 400))
                        .into(imageView);
            } else {
                picassoRequest.load("file://" + imageUrl)
                        .placeholder(R.mipmap.icon_image_error)
                        .error(R.mipmap.icon_image_error)
                        .into(imageView);
            }
        } else {
            if (TextUtils.isEmpty(imageUrl)) {
                picassoRequest.load(R.mipmap.icon_image_error)
                        .into(imageView);
            } else if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                picassoRequest.load(imageUrl)
                        .placeholder(R.mipmap.icon_image_error)
                        .error(R.mipmap.icon_image_error)
                        .into(imageView);
            } else {
                picassoRequest.load("file://" + imageUrl)
                        .placeholder(R.mipmap.icon_image_error)
                        .error(R.mipmap.icon_image_error)
                        .into(imageView);
            }
        }

    }

    public static void setGlideImage(Context context, ImageView imageView, String imageUrl, int type, int radius) {
        glideRequest = Glide.with(context);
        switch (type) {
            case TYPE_NORMAL: // 不做任何操作
                if (TextUtils.isEmpty(imageUrl)) {
                    glideRequest.load(R.mipmap.icon_image_load_failed)
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        glideRequest.load(imageUrl)
                                .error(R.mipmap.icon_image_load_failed)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    } else {
                        glideRequest.load("file://" + imageUrl)
                                .into(imageView);
                    }
                }
                break;

            case TYPE_CIRCLE: // 圆形图片
                if (TextUtils.isEmpty(imageUrl)) {
                    glideRequest.load(R.mipmap.icon_image_error)
                            .transform(new GlideCircleTransform(context))
                            .centerCrop()
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        glideRequest.load(imageUrl)
                                .error(R.mipmap.icon_image_load_failed)
                                .transform(new GlideCircleTransform(context))
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    } else {
                        glideRequest.load("file://" + imageUrl)
                                .transform(new GlideCircleTransform(context))
                                .centerCrop()
                                .into(imageView);
                    }
                }
                break;

            case TYPE_ROUND: // 圆角
                if (TextUtils.isEmpty(imageUrl)) {
                    glideRequest.load(R.mipmap.icon_image_load_failed)
                            .transform(new GlideRoundTransform(context, radius))
                            .centerCrop()
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        glideRequest.load(imageUrl)
                                .transform(new GlideRoundTransform(context, radius))
                                .centerCrop()
                                .error(R.mipmap.icon_image_load_failed)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    } else {
                        glideRequest.load("file://" + imageUrl)
                                .transform(new GlideRoundTransform(context, radius))
                                .centerCrop()
                                .into(imageView);
                    }
                }
                break;
        }
    }


    public static void setGlideImage(Context context, ImageView imageView, String imageUrl, int type) {
        glideRequest = Glide.with(context);
        switch (type) {
            case TYPE_NORMAL: // 不做任何操作
                if (TextUtils.isEmpty(imageUrl)) {
                    glideRequest.load(R.mipmap.icon_image_load_failed)
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        glideRequest.load(imageUrl)
                                .error(R.mipmap.icon_image_load_failed)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    } else {
                        glideRequest.load("file://" + imageUrl)
                                .into(imageView);
                    }
                }
                break;

            case TYPE_CIRCLE: // 圆形图片
                if (TextUtils.isEmpty(imageUrl)) {
                    glideRequest.load(R.mipmap.icon_image_load_failed)
                            .transform(new GlideCircleTransform(context))
                            .centerCrop()
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        glideRequest.load(imageUrl)
                                .error(R.mipmap.icon_image_load_failed)
                                .transform(new GlideCircleTransform(context))
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    } else {
                        glideRequest.load("file://" + imageUrl)
                                .transform(new GlideCircleTransform(context))
                                .centerCrop()
                                .into(imageView);
                    }
                }
                break;

            case TYPE_ROUND: // 圆角
                if (TextUtils.isEmpty(imageUrl)) {
                    glideRequest.load(R.mipmap.icon_image_load_failed)
                            .transform(new GlideRoundTransform(context, IMAGE_DEFAULT_RADIUS))
                            .centerCrop()
                            .into(imageView);
                } else {
                    // 这里用正则表达式来匹配
                    if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                        glideRequest.load(imageUrl)
                                .transform(new GlideRoundTransform(context, IMAGE_DEFAULT_RADIUS))
                                .centerCrop()
                                .error(R.mipmap.icon_image_load_failed)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    } else {
                        glideRequest.load("file://" + imageUrl)
                                .transform(new GlideRoundTransform(context, IMAGE_DEFAULT_RADIUS))
                                .centerCrop()
                                .into(imageView);
                    }
                }
                break;
        }
    }

    public static void clearGlide(Context context) {
        Glide.get(context).clearDiskCache();
        Glide.get(context).clearMemory();
    }

    public static void clearGlide(View target) {
        Glide.clear(target);
    }

}