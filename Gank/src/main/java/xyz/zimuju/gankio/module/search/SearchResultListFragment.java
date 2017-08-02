package xyz.zimuju.gankio.module.search;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import xyz.zimuju.gankio.api.GankService;
import xyz.zimuju.gankio.bean.SearchResult;
import xyz.zimuju.library.fragment.base.AbsListFragment;
import xyz.zimuju.library.http.HttpResult;
import xyz.zimuju.library.http.ServiceFactory;
import xyz.zimuju.library.http.subscriber.HttpResultSubscriber;
import xyz.zimuju.library.rx.RxUtils;
import xyz.zimuju.library.widget.LinearDecoration;

/**
 * Created by _SOLID
 * Date:2016/11/22
 * Time:9:56
 * Desc:
 */

public class SearchResultListFragment extends AbsListFragment {
    private String keyWord = "Android";
    private String category = "all";

    public static SearchResultListFragment newInstance(String category, String keyWord) {

        SearchResultListFragment fragment = new SearchResultListFragment();
        fragment.keyWord = keyWord;
        fragment.category = category;
        return fragment;
    }

    public void refresh(String category, String keyWord) {
        this.category = category;
        this.keyWord = keyWord;
        showLoading();
        refreshData();
    }

    @Override
    protected void customConfig() {
        addItemDecoration(new LinearDecoration(getContext(), RecyclerView.VERTICAL));
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance().createService(GankService.class)
                .search(category, keyWord, pageIndex)
                .compose(this.<HttpResult<List<SearchResult>>>bindToLifecycle())
                .compose(RxUtils.<HttpResult<List<SearchResult>>>defaultSchedulers_single())
                .subscribe(new HttpResultSubscriber<List<SearchResult>>() {
                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void _onSuccess(List<SearchResult> searchResults) {
                        onDataSuccessReceived(pageIndex, searchResults);
                    }


                });
    }


}
