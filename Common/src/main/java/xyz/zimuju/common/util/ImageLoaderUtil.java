package xyz.zimuju.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
 * @description ImageLoader : 图片加载
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-9-16-下午7:40
 * @version v1.0.0
 */
public class ImageLoaderUtil {
    public static final int TYPE_DEFAULT = 0;//矩形
    public static final int TYPE_ROUND_CORNER = 1;//圆角矩形
    public static final int TYPE_OVAL = 2;//圆形
    public static final String FILE_PATH_PREFIX = StringUtils.FILE_PATH_PREFIX;
    private static final String TAG = "ImageLoaderUtil";
    /**
     * 如果需要加载小图请修改为小图实际标识
     */
    public static String URL_SUFFIX_SMALL = "!common";
    private static int threadCount = 3; // default value is 1
    private static ImageLoaderUtil imageLoader;
    private LruCache<String, Bitmap> lruCache;
    private ExecutorService threadPool;
    private Type type = Type.LIFO; // default
    private LinkedList<Runnable> taskList;
    private Thread loopThread;
    private Handler loopThreadHandler;
    private Handler uiHandler; // used for set image to imageView
    private volatile Semaphore semaphore = new Semaphore(0);
    private volatile Semaphore semaphorePool;

    private ImageLoaderUtil(int threadCount, Type type) {
        loopThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                loopThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        threadPool.execute(getTask());
                        try {
                            semaphorePool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                semaphore.release();
                Looper.loop();
            }
        };
        loopThread.start();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        threadPool = Executors.newFixedThreadPool(threadCount);
        semaphorePool = new Semaphore(threadCount);
        taskList = new LinkedList<>();
        type = (type == null) ? Type.LIFO : type;
    }

    public static ImageLoaderUtil getInstance() {
        if (imageLoader == null) {
            synchronized (ImageLoaderUtil.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoaderUtil(threadCount, Type.LIFO);
                }
            }
        }
        return imageLoader;
    }

    public static ImageLoaderUtil getInstance(int threadCount, Type type) {
        if (imageLoader == null) {
            synchronized (ImageLoaderUtil.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoaderUtil(threadCount, type);
                }
            }
        }
        return imageLoader;
    }

    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 初始化方法
     *
     * @param context
     * @must 使用其它方法前必须调用，建议在自定义Application的onCreate方法中调用
     */
    public static void init(Context context) {
        if (context == null) {
            Log.e(TAG, "\n\n\n\n\n !!!!!!  <<<<<< init  context == null >> return; >>>>>>>> \n\n\n\n");
            return;
        }
        imageLoader = ImageLoaderUtil.getInstance();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(getOption(0))
                // .threadPoolSize(5)
                // //.threadPriority(Thread.MIN_PRIORITY + 3)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .discCacheSize((int)(Runtime.getRuntime().maxMemory()/2))
                // .discCache(new UnlimitedDiscCache(getCachePath()))
                // .memoryCacheSize(2 * 1024 * 1024)
                // .memoryCacheExtraOptions(147, 147)
                // .writeDebugLogs()
                // .httpConnectTimeout(5000)
                // .httpReadTimeout(20000)
                .diskCacheExtraOptions(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context), null)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                // .displayer(new RoundedBitmapDisplayer(5))
                .build();

        init(context);
    }

    /**
     * 加载图片
     * 加载小图应再调用该方法前使用getSmallUri处理uri
     * type = TYPE_DEFAULT
     *
     * @param iv
     * @param uri 网址url或本地路径path
     */
    public static void loadImage(ImageView iv, String uri) {
        loadImage(iv, uri, TYPE_DEFAULT);
    }

    /**
     * 加载图片
     * 加载小图应再调用该方法前使用getSmallUri处理uri
     *
     * @param type 图片显示类型
     * @param iv
     * @param uri  网址url或本地路径path
     */
    public static void loadImage(final ImageView iv, String uri, final int type) {
        if (iv == null) {// || iv.getWidth() <= 0) {
            Log.i(TAG, "loadImage  iv == null >> return;");
            return;
        }
        Log.i(TAG, "loadImage  iv" + (iv == null ? "==" : "!=") + "null; uri=" + uri);

        uri = getCorrectUri(uri);

        //新的加载图片
        imageLoader.displayImage(uri, iv, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View arg1) {
                //XML代码直接设置src属性更直观方便
                //				switch (type) {
                //				case TYPE_OVAL:
                //					iv.setImageResource(R.drawable.oval_alpha);
                //					break;
                //				case TYPE_ROUND_CORNER:
                //					iv.setImageResource(R.drawable.round_alpha);
                //					break;
                //				default:
                //					iv.setImageResource(R.drawable.square_alpha);
                //					break;
                //				}
            }

            @Override
            public void onLoadingFailed(String imageUri, View arg1, FailReason arg2) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
                if (loadedImage == null) {
                    Log.e(TAG, "loadImage  imageLoader.displayImage.onLoadingComplete  loadedImage == null >> return;");
                    return;
                }
                switch (type) {
                    case TYPE_OVAL:
                        iv.setImageBitmap(toRoundCorner(loadedImage, loadedImage.getWidth() / 2));
                        break;
                    case TYPE_ROUND_CORNER:
                        iv.setImageBitmap(toRoundCorner(loadedImage, loadedImage.getWidth() / 10));
                        break;
                    default:
                        iv.setImageBitmap(loadedImage);
                        break;
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View arg1) {
            }
        });

    }

    /**
     * 获取可用的uri
     *
     * @param uri
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getCorrectUri(String uri) {
        Log.i(TAG, "<<<<  getCorrectUri  uri = " + uri);
        uri = StringUtils.getNoBlankString(uri);

        if (uri.toLowerCase().startsWith(StringUtils.HTTP) == false) {
            uri = uri.startsWith(FILE_PATH_PREFIX) ? uri : FILE_PATH_PREFIX + uri;
        }

        Log.i(TAG, "getCorrectUri  return uri = " + uri + " >>>>> ");
        return uri;
    }

    /**
     * 获取小图url或path
     * path不加URL_SUFFIX_SMALL
     *
     * @param uri
     * @return
     */
    public static String getSmallUri(String uri) {
        return getSmallUri(uri, false);
    }

    /**
     * 获取小图url或path
     * path不加URL_SUFFIX_SMALL
     *
     * @param uri
     * @param isLocalPath
     * @return
     */
    public static String getSmallUri(String uri, boolean isLocalPath) {
        if (uri == null) {
            Log.e(TAG, "getSmallUri  uri == null >> return null;");
            return null;
        }

        if (uri.startsWith("/") || uri.startsWith(FILE_PATH_PREFIX) || StringUtils.isFilePathExist(FILE_PATH_PREFIX + uri)) {
            isLocalPath = true;
        }
        return isLocalPath || uri.endsWith(URL_SUFFIX_SMALL)
                ? uri : uri + URL_SUFFIX_SMALL;
    }

    /**
     * 获取配置
     *
     * @param cornerRadiusSize
     * @return
     */
    @SuppressWarnings("deprecation")
    private static DisplayImageOptions getOption(int cornerRadiusSize) {
        BitmapFactory.Options options0 = new BitmapFactory.Options();
        options0.inPreferredConfig = Bitmap.Config.RGB_565;

        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        if (cornerRadiusSize > 0) {
            builder.displayer(new RoundedBitmapDisplayer(cornerRadiusSize));
        }

        return builder.cacheInMemory(true).cacheOnDisc(true).decodingOptions(options0).build();
    }

    /**
     * 将图片改为圆角类型
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private void displayImage(String uri, ImageView iv, ImageLoadingListener imageLoadingListener) {

    }

    public void loadImage(final String path, final ImageView imageView) {
        imageView.setTag(path);
        if (uiHandler == null) {
            uiHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bitmap = holder.bitmap;
                    String path = holder.path;
                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            };
        }

        Bitmap bitmap = getBitmapFormLruCache(path);
        if (bitmap != null) {
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bitmap;
            holder.imageView = imageView;
            holder.path = path;
            Message message = Message.obtain();
            message.obj = holder;
            uiHandler.sendMessage(message);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = getImageViewWidth(imageView);
                    int reqWidth = imageSize.width;
                    int reqHeight = imageSize.height;
                    Bitmap bitmap = decodeSampledBitmapFromResource(path, reqWidth, reqHeight);
                    addBitmapToLruCache(path, bitmap);
                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.bitmap = getBitmapFormLruCache(path);
                    holder.imageView = imageView;
                    holder.path = path;
                    Message message = Message.obtain();
                    message.obj = holder;
                    uiHandler.sendMessage(message);
                    semaphorePool.release();
                }
            });
        }
    }

    private synchronized void addTask(Runnable runnable) {
        try {
            if (loopThreadHandler == null) {
                semaphore.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskList.add(runnable);
        loopThreadHandler.sendEmptyMessage(0x110);
    }

    private synchronized Runnable getTask() {
        if (type == Type.FIFO) {
            return taskList.removeFirst();
        } else if (type == Type.LIFO) {
            return taskList.removeLast();
        }
        return null;
    }

    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        final LayoutParams params = imageView.getLayoutParams();

        int width = params.width == LayoutParams.WRAP_CONTENT ? 0 : imageView.getWidth(); // Get actual image width
        if (width <= 0) {
            width = params.width;
        }

        if (width <= 0) {
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
        }

        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = params.height == LayoutParams.WRAP_CONTENT ? 0 : imageView.getHeight(); // Get actual image height
        if (height <= 0) {
            height = params.height; // Get layout height parameter
        }

        if (height <= 0) {
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
        }

        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;

    }

    private Bitmap getBitmapFormLruCache(String key) {
        return lruCache.get(key);
    }

    private void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (getBitmapFormLruCache(key) == null) {
            if (bitmap != null)
                lruCache.put(key, bitmap);
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight) {
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    private Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        return bitmap;
    }

    public enum Type {
        FIFO, LIFO
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }
}
