package xyz.zimuju.common.defination;

public interface OnStopLoadListener {
    /**
     * 停止刷新
     */
    void onStopRefresh();

    /**
     * 停止加载更多
     *
     * @param isHaveMore 还有未加载的数据
     */
    void onStopLoadMore(boolean isHaveMore);
}
