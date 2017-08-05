package xyz.zimuju.sample.surface.read;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import xyz.zimuju.sample.entity.content.XianDuCategory;

public class XianDuTabAdapter extends FragmentStatePagerAdapter {
    private List<XianDuCategory> list;

    public XianDuTabAdapter(FragmentManager fm, List<XianDuCategory> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadingListFragment.newInstance(list.get(position).getCategory());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
