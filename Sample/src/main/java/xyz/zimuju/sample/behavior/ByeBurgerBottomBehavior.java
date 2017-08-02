package xyz.zimuju.sample.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/*
 * @description ByeBurgerBottomBehavior ：Bye Bye Burger Navigation Bar Behavior
 * @author Nathaniel
 * @time 2017/8/2 - 18:54
 * @version 1.0.0
 */
public class ByeBurgerBottomBehavior extends ByeBurgerBehavior {

    private TranslateAnimateHelper mAnimateHelper;

    public ByeBurgerBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (isFirstMove) {
            isFirstMove = false;
            mAnimateHelper = TranslateAnimateHelper.get(child);
            mAnimateHelper.setStartY(child.getY());
            mAnimateHelper.setMode(TranslateAnimateHelper.MODE_BOTTOM);
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
