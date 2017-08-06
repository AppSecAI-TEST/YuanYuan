package xyz.zimuju.common.basal;

import android.content.Context;

/*
 * @description BasicView View 的基类
 * @author Nathaniel - nathanwriting@126.com
 * @time 2017/06/08 - 23:14
 * @version 1.0.0
 */
public interface BasalView {
    void showToast(String message);

    Context getContext();
}
