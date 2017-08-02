package xyz.zimuju.sample.surface.gank.activity;

import xyz.zimuju.sample.util.ToastUtils;

/**
 * Created by _SOLID
 * Date:2016/6/2
 * Time:16:58
 */
public abstract class BaseMainActivity extends BaseActivity {
    private static final long MAX_DOUBLE_BACK_DURATION = 1500;
    private long lastBackKeyDownTick = 0;

    @Override
    public void onBackPressed() {
        if (beforeOnBackPressed()) {
            long currentTick = System.currentTimeMillis();
            if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
                ToastUtils.getInstance().showToast("再按一次退出");
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
