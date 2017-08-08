package xyz.zimuju.cloudreader.surface.gank.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import rx.Subscription;
import xyz.zimuju.cloudreader.R;
import xyz.zimuju.cloudreader.adapter.WelfareAdapter;
import xyz.zimuju.cloudreader.app.Constants;
import xyz.zimuju.cloudreader.base.BaseFragment;
import xyz.zimuju.cloudreader.base.baseadapter.OnItemClickListener;
import xyz.zimuju.cloudreader.bean.GankIoDataBean;
import xyz.zimuju.cloudreader.databinding.FragmentWelfareBinding;
import xyz.zimuju.cloudreader.http.RequestImpl;
import xyz.zimuju.cloudreader.http.cache.ACache;
import xyz.zimuju.cloudreader.model.GankOtherModel;
import xyz.zimuju.cloudreader.util.DebugUtil;
import xyz.zimuju.cloudreader.widget.viewbigimage.ViewBigImageActivity;
import xyz.zimuju.http.HttpUtils;
import xyz.zimuju.xrecyclerview.XRecyclerView;

/**
 * 福利
 */
public class WelfareFragment extends BaseFragment<FragmentWelfareBinding> {

    private static final String TAG = "WelfareFragment";
    ArrayList<String> imgList = new ArrayList<>();
    private int mPage = 1;
    private WelfareAdapter mWelfareAdapter;
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private ACache aCache;
    private GankIoDataBean meiziBean;
    private GankOtherModel mModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DebugUtil.error("--WelfareFragment   ----onActivityCreated");
        mModel = new GankOtherModel();
        aCache = ACache.get(getContext());
//        meiziBean = (GankIoDataBean) aCache.getAsObject(Constants.GANK_MEIZI);

        bindingView.xrvWelfare.setPullRefreshEnabled(false);
        bindingView.xrvWelfare.clearHeader();
        mWelfareAdapter = new WelfareAdapter();

        bindingView.xrvWelfare.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadWelfareData();
            }
        });
        isPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        if (meiziBean != null && meiziBean.getResults() != null && meiziBean.getResults().size() > 0) {
            showContentView();

            imgList.clear();
            for (int i = 0; i < meiziBean.getResults().size(); i++) {
                imgList.add(meiziBean.getResults().get(i).getUrl());
            }
            meiziBean = (GankIoDataBean) aCache.getAsObject(Constants.GANK_MEIZI);
            setAdapter(meiziBean);
        } else {
            loadWelfareData();
        }
    }

    private void loadWelfareData() {
        mModel.setData("福利", mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        imgList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                        }

                        setAdapter(gankIoDataBean);
                        aCache.remove(Constants.GANK_MEIZI);
                        aCache.put(Constants.GANK_MEIZI, gankIoDataBean, 30000);

                    }
                } else {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        bindingView.xrvWelfare.refreshComplete();
                        mWelfareAdapter.addAll(gankIoDataBean.getResults());
                        mWelfareAdapter.notifyDataSetChanged();

                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                        }

                    } else {
                        bindingView.xrvWelfare.noMoreLoading();
                    }
                }
            }

            @Override
            public void loadFailed() {
                bindingView.xrvWelfare.refreshComplete();
                if (mWelfareAdapter.getItemCount() == 0) {
                    showError();
                }
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                WelfareFragment.this.addSubscription(subscription);
            }
        });
    }

    private void setAdapter(GankIoDataBean gankIoDataBean) {
//        mWelfareAdapter = new WelfareAdapter();
        mWelfareAdapter.addAll(gankIoDataBean.getResults());
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
        bindingView.xrvWelfare.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvWelfare.setAdapter(mWelfareAdapter);
        mWelfareAdapter.notifyDataSetChanged();


        mWelfareAdapter.setOnItemClickListener(new OnItemClickListener<GankIoDataBean.ResultBean>() {
            @Override
            public void onClick(GankIoDataBean.ResultBean resultsBean, int position) {
                DebugUtil.error("-----" + imgList.toString());
                DebugUtil.error("----imgList.size():  " + imgList.size());
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putStringArrayList("imageuri", imgList);
                Intent intent = new Intent(getContext(), ViewBigImageActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        // 显示成功后就不是第一次了，不再刷新
        isFirst = false;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void onRefresh() {
        loadWelfareData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--WelfareFragment   ----onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugUtil.error(TAG + "   ----onResume");
    }
}
