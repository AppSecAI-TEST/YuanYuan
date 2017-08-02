package xyz.zimuju.gankio.common.activity;

import android.support.v4.app.Fragment;

import xyz.zimuju.gankio.common.SortFragment;
import xyz.zimuju.library.activity.ToolbarActivity;

/**
 * Created by _SOLID
 * Date:2016/5/17
 * Time:15:49
 */
public class SortActivity extends ToolbarActivity {
    @Override
    protected String getToolbarTitle() {
        return "排序";
    }

    @Override
    protected Fragment getFragment() {
        return new SortFragment();
    }
}
