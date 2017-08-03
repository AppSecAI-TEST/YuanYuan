package xyz.zimuju.sample.surface.gank.activity;

import xyz.zimuju.common.util.ToastUtils;

/*
 * @description BaseMainActivity ：主界面的基类
 * @author Nathaniel
 * @time 2017/8/3 - 10:49
 * @version 1.0.0
 */
public abstract class BaseMainActivity extends BaseActivity {
    private static final long MAX_DOUBLE_BACK_DURATION = 1500;
    private long lastBackKeyDownTick = 0;

    @Override
    public void onBackPressed() {
        if (beforeOnBackPressed()) {
            long currentTick = System.currentTimeMillis();
            if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
                ToastUtils.showToast(getContext(), "再按一次退出");
                lastBackKeyDownTick = currentTick;
            } else {
                finish();
            }
        }
    }

    protected boolean beforeOnBackPressed() {
        return true;
    }
}
