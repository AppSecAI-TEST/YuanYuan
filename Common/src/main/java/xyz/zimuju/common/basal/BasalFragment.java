package xyz.zimuju.common.basal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/*
 * @description BasalFragment ： 所有fragment的基类 如果想用 findViewById 则将 initView() 打开即可
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-9-24-下午11:08
 * @version v1.0.0
 */
public abstract class BasalFragment extends Fragment {
    protected View rootView;

    protected abstract int getLayoutId();// 获得布局文件的id

    protected abstract void initView(); // 初始化使徒控件

    protected abstract void initData(); // 初始化数据

    protected abstract void viewOption(); // 绑定使徒控件操作

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        // ButterKnife.bind(this, rootView);
        initData();
        initView();
        viewOption();
        return rootView;
    }

    public Context getContext() {
        return getActivity();
    }
}
