package xyz.zimuju.sample.surface.navigation.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import xyz.zimuju.common.basal.BasalFragment;
import xyz.zimuju.common.basal.MRecyclerViewAdapter;
import xyz.zimuju.common.widget.custom.CustomRecyclerView;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.adapter.gank.GankCategoryAdapter;
import xyz.zimuju.sample.util.GankCategoryUtils;


public class NewsFragment extends BasalFragment implements MRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.news_category_recyclerview)
    CustomRecyclerView categoryRecyclerView;

    @BindView(R.id.news_container_recyclerview)
    CustomRecyclerView containerRecyclerView;

    private GankCategoryAdapter gankCategoryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {
        gankCategoryAdapter = new GankCategoryAdapter();
        gankCategoryAdapter.setDataList(GankCategoryUtils.getCategoryList());
    }

    @Override
    protected void viewOption() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setNestedScrollingEnabled(false);
        categoryRecyclerView.setAdapter(gankCategoryAdapter);
        gankCategoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, int position) {
       if (parent.getId() == categoryRecyclerView.getId()){
           gankCategoryAdapter.setIndex(position);
           gankCategoryAdapter.notifyDataSetChanged();
       }
    }
}
