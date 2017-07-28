package xyz.zimuju.common.defination;

import android.view.View;

public interface OnReachViewBorderListener {
    int TYPE_TOP = 0;
    int TYPE_BOTTOM = 1;
    int TYPE_LEFT = 2;
    int TYPE_RIGHT = 3;

    /**
     * 到达（接触到）v的某个边界（type）
     *
     * @param type 边界类型
     * @param v    目标View，一般为ViewGroup（ListView，GridView等）
     */
    void onReach(int type, View v);
}
