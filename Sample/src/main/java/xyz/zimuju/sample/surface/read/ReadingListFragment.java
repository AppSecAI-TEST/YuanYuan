package xyz.zimuju.sample.surface.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import xyz.zimuju.sample.engine.service.XianDuService;
import xyz.zimuju.sample.entity.content.XianDuItem;
import xyz.zimuju.sample.surface.gank.fragment.AbsListFragment;
import xyz.zimuju.sample.rx.RxUtils;
import xyz.zimuju.sample.R;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/18
 * Time:16:16
 * Desc:
 */

public class ReadingListFragment extends AbsListFragment {

    private String category;

    public static ReadingListFragment newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);
        ReadingListFragment fragment = new ReadingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
    }

    @Override
    protected void customConfig() {
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(ContextCompat.getColor(getContext(), R.color.list_divider_color))
                .sizeResId(R.dimen.list_divider_height)
                .build());
    }

    @Override
    public void loadData(final int pageIndex) {
        XianDuService.getData(category, pageIndex)
                .compose(RxUtils.<List<XianDuItem>>defaultSchedulers_single())
                .subscribe(new SingleObserver<List<XianDuItem>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<XianDuItem> xianDuItems) {
                        onDataSuccessReceived(pageIndex, xianDuItems);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(new Exception(e));
                    }
                });
    }

    @Override
    protected int getInitPageIndex() {
        return 1;
    }
}
