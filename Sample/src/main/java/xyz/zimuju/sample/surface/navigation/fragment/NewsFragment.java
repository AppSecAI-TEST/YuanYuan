package xyz.zimuju.sample.surface.navigation.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    private GankCategoryAdapter gankCategoryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {
        gankCategoryAdapter = new GankCategoryAdapter();
        gankCategoryAdapter.setDataList(GankCategoryUtils.getCategoryList());
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setNestedScrollingEnabled(false);
        categoryRecyclerView.setAdapter(gankCategoryAdapter);
    }

    @Override
    protected void viewOption() {
        gankCategoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
