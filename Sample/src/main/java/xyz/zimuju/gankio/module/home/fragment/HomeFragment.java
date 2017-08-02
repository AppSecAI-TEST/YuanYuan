package xyz.zimuju.gankio.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.gankio.api.GankService;
import xyz.zimuju.gankio.bean.CategoryList;
import xyz.zimuju.gankio.bean.Daily;
import xyz.zimuju.gankio.common.constant.Category;
import xyz.zimuju.gankio.fragment.base.AbsListFragment;
import xyz.zimuju.gankio.http.HttpResult;
import xyz.zimuju.gankio.http.ServiceFactory;
import xyz.zimuju.gankio.http.subscriber.HttpResultSubscriber;
import xyz.zimuju.gankio.rx.RxUtils;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:14:48
 * Desc:
 */

public class HomeFragment extends AbsListFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
    }

    @Override
    public void loadData(final int pageIndex) {
        final List data = new ArrayList();
        if (pageIndex == getInitPageIndex()) {
            CategoryList categoryList = new CategoryList();
            categoryList.setData(Category.getGanHuoCateGory());
            data.add(categoryList);
        }

        ServiceFactory.getInstance()
                .createService(GankService.class)
                .getRecently(pageIndex)
                .compose(this.<HttpResult<List<Daily>>>bindToLifecycle())
                .compose(RxUtils.<HttpResult<List<Daily>>>defaultSchedulers_single())
                .subscribe(new HttpResultSubscriber<List<Daily>>() {
                    @Override
                    public void _onSuccess(List<Daily> dailies) {
                        data.addAll(dailies);
                        onDataSuccessReceived(pageIndex, data);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }
                });
    }

    @Override
    protected int getInitPageIndex() {
        return 1;
    }
}
