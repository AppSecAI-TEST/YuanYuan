package xyz.zimuju.common.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Scroller;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.R;
import xyz.zimuju.common.util.GlideImageLoader;


/**
 * 带数字指示器的轮播控件
 */
@SuppressLint({"HandlerLeak", "NewApi"})
public class FlashDigitalView extends FrameLayout {
    private ImageHandler imageHandler = new ImageHandler(new WeakReference<FlashDigitalView>(this));
    private Context context;
    private List<ImageView> imageViewsList;
    private List<String> imageUriList;
    private ViewPager mViewPager;
    private FlashViewListener mFlashViewListener;// 向外提供接口
    private int index; // 指示器坐标
    private int effect; // 图片切换的动画效果
    private TextView indicator; // 数字指示器

    public FlashDigitalView(Context context) {
        this(context, null);
    }

    public FlashDigitalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlashDigitalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 读取该自定义控件自定义的属性
        this.context = context;
        effect = 0;
        initUI(context);
        if (imageUriList.size() > 0) {
            setImageUriList(imageUriList);
        }
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // 设置监听
    public void setOnPageClickListener(FlashViewListener mFlashViewListener) {
        this.mFlashViewListener = mFlashViewListener;
    }

    // 初始化控件
    private void initUI(Context context) {
        imageViewsList = new ArrayList<>();
        imageUriList = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.common_slide_digital_layout, this, true);
        indicator = (TextView) findViewById(R.id.flash_indicator);
        mViewPager = (ViewPager) findViewById(R.id.flash_pager);
    }

    // 将图片地址填充到控件中
    public void setImageUriList(List<String> imageUris) {
        // 判断是否有内容，如果有则直接将原有的内容清除
        if (imageUriList.size() > 0) {
            imageUriList.clear();
            imageViewsList.clear();
        }

        // 如果得到的图片张数为0，则增加一张默认的图片，该图片为预加载图片
        if (imageUris.size() <= 0) {
            imageUriList.add("drawable://" + R.mipmap.common_ic_picture_loading);
        } else {
            imageUriList.addAll(imageUris);
        }

        for (int i = 0; i < imageUriList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            // X和Y方向都填满
            imageView.setScaleType(ScaleType.FIT_XY);
            // ImageLoader.getInstance().displayImage(imageUriList.get(i), imageView, options);
            GlideImageLoader.load(context, imageUriList.get(i), imageView);
            imageViewsList.add(imageView);
        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
        // mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        setPageTransformer(true, new DefaultTransformer());
        // 图片小于等于1张时，不轮播
        if (imageUriList.size() > 1) {
            // 利用反射修改自动轮播的动画持续时间
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());
                field.set(mViewPager, scroller);
                scroller.setmDuration(250);
                mViewPager.setCurrentItem(100 * imageViewsList.size());
                imageHandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 设置指示的坐标
    private void setIndicatorText(int position) {
        int visibility = imageViewsList.size() >= 1 ? View.VISIBLE : View.GONE;
        if (visibility == View.VISIBLE) {
            int temp = (position % imageViewsList.size()) + 1;
            indicator.setText(String.format(context.getResources().getString(R.string.flash_indicator_text), String.valueOf(temp), String.valueOf(imageViewsList.size())));
        }
        indicator.setVisibility(visibility);
    }

    @SuppressWarnings("unused")
    private void destoryBitmaps() {
        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
        }
    }

    // 设置切换效果
    public void setPageTransformer(boolean b, PageTransformer rotateTransformer) {
        mViewPager.setPageTransformer(b, rotateTransformer);
    }

    // 功能：向外提供点击事件的接口，其中indexition代表的是图片的索引，即第几张图片
    public interface FlashViewListener {
        void onClick(int index);
    }

    private static class ImageHandler extends Handler {

        protected static final int MSG_UPDATE_IMAGE = 1;

        protected static final int MSG_KEEP_SILENT = 2;

        protected static final int MSG_BREAK_SILENT = 3;

        protected static final int MSG_PAGE_CHANGED = 4;

        protected static final long MSG_DELAY = 4000;

        private WeakReference<FlashDigitalView> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<FlashDigitalView> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            FlashDigitalView activity = weakReference.get();
            if (activity == null) {
                return;
            }
            if (activity.imageHandler.hasMessages(MSG_UPDATE_IMAGE)) {
                // 这里必须加入currentItem>0的判断，否则不能完美的自动轮播
                if (currentItem > 0) {
                    activity.imageHandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.mViewPager.setCurrentItem(currentItem);
                    activity.imageHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;

                case MSG_KEEP_SILENT:
                    break;

                case MSG_BREAK_SILENT:
                    activity.imageHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;

                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    activity.imageHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
            }
        }
    }

    // 数据适配器
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int index, Object object) {

        }

        @Override
        public Object instantiateItem(View container, int indexation) {

            indexation = indexation % imageViewsList.size();

            if (indexation < 0) {
                indexation = indexation + imageViewsList.size();
            }

            View view = imageViewsList.get(indexation);
            view.setTag(indexation);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFlashViewListener != null) {
                        mFlashViewListener.onClick(index);
                    }
                }
            });

            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewPager pager = (ViewPager) vp;
                pager.removeView(view);
            }
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (imageViewsList.size() <= 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class MyPageChangeListener implements OnPageChangeListener {
        //当页面的滑动状态改变的时候
        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    imageHandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                    break;

                case ViewPager.SCROLL_STATE_IDLE:
                    imageHandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            imageHandler.sendMessage(Message.obtain(imageHandler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
            setIndicatorText(position);
        }
    }

    /**
     * FixedSpeedScroller类的源码来源于网络，在此谢过贡献此代码的道友
     */
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 1500;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public int getmDuration() {
            return mDuration;
        }

        public void setmDuration(int time) {
            mDuration = time;
        }
    }

    public class DefaultTransformer implements PageTransformer {
        @Override
        public void transformPage(View view, float arg1) {
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setTranslationY(0);
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(view.getHeight() / 2);
            view.setScaleX(1);
            view.setScaleY(1);
            view.setRotation(0);
        }
    }
}
