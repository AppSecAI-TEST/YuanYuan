package xyz.zimuju.sample.surface.content;


/**
 * 使用方法：复制>粘贴>改名>改代码
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.base.BaseListFragment;
import xyz.zimuju.common.defination.AdapterCallBack;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.adapter.content.DemoAdapter;

public class DemoListFragment extends BaseListFragment<Entry<String, String>, ListView, DemoAdapter> {
    public static DemoListFragment createInstance() {
        return new DemoListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.demo_list_fragment);
        initView();
        initData();
        initEvent();
        onRefresh();
        return view;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void setList(final List<Entry<String, String>> list) {
        setList(new AdapterCallBack<DemoAdapter>() {
            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }

            @Override
            public DemoAdapter createAdapter() {
                return new DemoAdapter(context);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

    }


    @Override
    public void getListAsync(int page) {
        showProgressDialog(R.string.loading);
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
        for (int i = 0; i < 64; i++) {
            list.add(new Entry<String, String>("联系人" + i, String.valueOf(1311736568 + i * i)));
        }
        onLoadSucceed(page, list);
    }


    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        super.initEvent();
        lvBaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
            }
        });
    }
}