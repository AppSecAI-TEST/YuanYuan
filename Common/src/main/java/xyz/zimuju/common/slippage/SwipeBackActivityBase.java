package xyz.zimuju.common.slippage;

public interface SwipeBackActivityBase {
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();
}
