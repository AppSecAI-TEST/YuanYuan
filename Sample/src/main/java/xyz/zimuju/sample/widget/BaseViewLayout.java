package xyz.zimuju.sample.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import xyz.zimuju.common.base.BaseView;

public class BaseViewLayout<T> extends FrameLayout {
    private static final String TAG = "BaseViewLayout";
    public BaseView<T> bv;
    private Activity context;

    public BaseViewLayout(Context context) {
        super(context);
        init((Activity) context);
    }

    public BaseViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init((Activity) context);
    }

    public BaseViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init((Activity) context);
    }

    protected void init(Activity context) {
        this.context = context;
    }

    public void createView(BaseView<T> bv) {
        this.bv = bv;

        removeAllViews();
        super.addView(bv.createView(context.getLayoutInflater()));
        bindView(null);
    }

    @Override
    public void addView(View child) {
        throw new UnsupportedOperationException(TAG + "不支持该方法");
    }

    public void bindView(T data) {
        bv.bindView(data);
    }

}
