package xyz.zimuju.common.defination;

import android.support.annotation.Nullable;

public interface ViewPresenter {

    /**
     * 获取导航栏标题名
     *
     * @return null - View.GONE; "" - View.GONE; "xxx" - "xxx"
     */
    @Nullable
    String getTitleName();

    /**
     * 获取导航栏返回按钮名
     *
     * @return null - default; "" - default; "xxx" - "xxx"
     */
    @Nullable
    String getReturnName();

    /**
     * 获取导航栏前进按钮名
     *
     * @return null - default; "" - default; "xxx" - "xxx"
     */
    @Nullable
    String getForwardName();

}