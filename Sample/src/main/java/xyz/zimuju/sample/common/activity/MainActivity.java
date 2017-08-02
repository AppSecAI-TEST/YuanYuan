package xyz.zimuju.sample.common.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.bmob.v3.update.BmobUpdateAgent;
import xyz.zimuju.sample.module.home.fragment.HomeFragment;
import xyz.zimuju.sample.module.mine.MineFragment;
import xyz.zimuju.sample.module.read.ReadingTabFragment;
import xyz.zimuju.sample.module.search.SearchActivity;
import xyz.zimuju.sample.activity.base.BaseMainActivity;
import xyz.zimuju.sample.fragment.base.AbsListFragment;
import xyz.zimuju.sample.fragment.base.BaseFragment;
import xyz.zimuju.sample.util.ViewUtils;
import xyz.zimuju.sample.R;

public class MainActivity extends BaseMainActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private AppBarLayout mAppBarLayout;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.gank_activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        BmobUpdateAgent.update(this);
        mFragmentManager = getSupportFragmentManager();
        switchFragment(0);
    }

    @Override
    protected void setUpView() {
        mAppBarLayout = $(R.id.appbar_layout);
        mBottomNavigationView = $(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        showAppBar();
                        switchFragment(0);
                        break;
                    case R.id.item_reading:
                        hideAppBar();
                        switchFragment(1);
                        break;
                    case R.id.item_collect:
                        hideAppBar();
                        switchFragment(2);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        ViewCompat.setElevation(mAppBarLayout, ViewUtils.dp2px(this, 4));

        $(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(MainActivity.this);
            }
        });
    }

    private void hideAppBar() {
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        if (layoutParams.height == 0) return;
        layoutParams.height = 0;
        mAppBarLayout.setLayoutParams(layoutParams);
    }

    private void showAppBar() {
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        if (layoutParams.height != 0) return;
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.app_bar_height);
        mAppBarLayout.setLayoutParams(layoutParams);
    }

    @Override
    protected void setUpData() {
    }

    private void switchFragment(int index) {
        Fragment to = mFragmentManager.findFragmentByTag(index + "");
        if (to == null) {
            if (index == 0)
                to = ViewUtils.createFragment(HomeFragment.class);
            else if (index == 1)
                to = ViewUtils.createFragment(ReadingTabFragment.class);
            else if (index == 2)
                to = ViewUtils.createFragment(MineFragment.class);
            else
                to = ViewUtils.createFragment(HomeFragment.class);
        }
        if (to == mCurrentFragment && mCurrentFragment instanceof BaseFragment) {
            ((BaseFragment) mCurrentFragment).refresh();
        } else if (to.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commit();
        } else {
            to.setUserVisibleHint(true);
            if (mCurrentFragment != null)
                mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to, index + "").commit();
            else
                mFragmentManager.beginTransaction().add(R.id.frame_content, to, index + "").commit();
        }
        mCurrentFragment = to;
    }

    @Override
    protected boolean beforeOnBackPressed() {
        if (mCurrentFragment instanceof AbsListFragment) {
            AbsListFragment listFragment = (AbsListFragment) mCurrentFragment;
            return listFragment.scrollToTop();
        } else if (mCurrentFragment instanceof ReadingTabFragment) {
            ReadingTabFragment readingTabFragment = (ReadingTabFragment) mCurrentFragment;
            return readingTabFragment.scrollToTop();
        }

        return true;
    }
}
