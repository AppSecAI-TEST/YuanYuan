package xyz.zimuju.sample.common.activity;

import android.support.v4.app.Fragment;

import xyz.zimuju.sample.common.SortFragment;
import xyz.zimuju.sample.activity.ToolbarActivity;

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
