package xyz.zimuju.sample.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import xyz.zimuju.sample.helper.ScaleAnimateHelper;
import xyz.zimuju.sample.helper.TranslateAnimateHelper;

public class ByeBurgerFloatButtonBehavior extends ByeBurgerBehavior {

    private ScaleAnimateHelper mAnimateHelper;

    public ByeBurgerFloatButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (isFirstMove) {
            isFirstMove = false;
            mAnimateHelper = ScaleAnimateHelper.get(child);
        }

        if (Math.abs(dy) > mTouchSlop) {
            if (dy < 0) {
                if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_HIDE) {
                    mAnimateHelper.show();
                }
            } else if (dy > 0) {
                if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_SHOW) {
                    mAnimateHelper.hide();
                }
            }
        }
    }
}
