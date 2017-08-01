package xyz.zimuju.sample.surface.navigation.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import butterknife.BindView;
import xyz.zimuju.common.basal.BasalFragment;
import xyz.zimuju.common.basal.MRecyclerViewAdapter;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.adapter.gank.GankCategoryAdapter;
import xyz.zimuju.sample.util.GankCategoryUtils;


public class NewsFragment extends BasalFragment implements MRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.news_category_recyclerview)
    RecyclerView categoryRecyclerView;

    @BindView(R.id.news_container_recyclerview)
    RecyclerView containerRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void viewOption() {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        GankCategoryAdapter gankCategoryAdapter = new GankCategoryAdapter();
        gankCategoryAdapter.setDataList(GankCategoryUtils.getCategoryList());
        categoryRecyclerView.setAdapter(gankCategoryAdapter);
        gankCategoryAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(ViewGroup parent, View view, int position) {

    }
}
