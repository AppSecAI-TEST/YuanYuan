package xyz.zimuju.sample.adapter.navigation;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*
 * @description NavigationFragmentPagerAdapter
 * @author Nathaniel
 * @time 2017/8/1 - 11:43
 * @version 1.0.0
 */
public class NavigationFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public NavigationFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}