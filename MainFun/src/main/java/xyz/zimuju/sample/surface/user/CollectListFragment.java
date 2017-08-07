package xyz.zimuju.sample.surface.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.entity.bomb.CollectTable;
import xyz.zimuju.sample.provider.CollectViewProvider;
import xyz.zimuju.sample.surface.gank.AbsListFragment;
import xyz.zimuju.sample.util.AuthorityUtils;

public class CollectListFragment extends AbsListFragment {

    public static CollectListFragment newInstance() {
        Bundle args = new Bundle();
        CollectListFragment fragment = new CollectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void registerItemProvider(MultiTypeAdapter adapter) {
        adapter.register(CollectTable.class, new CollectViewProvider(mRecyclerView));
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
        mPageSize = 10;
        AVQuery<CollectTable> query = new AVQuery<>("Todo");
        query.whereEqualTo("username", AuthorityUtils.getUserName());
        query.setLimit(mPageSize);
        query.setSkip(mPageSize * (pageIndex - 1));
        query.order("-createdAt");
        if (AuthorityUtils.isLogin()) {
            query.findInBackground(new FindCallback<CollectTable>() {
                @Override
                public void done(List<CollectTable> list, AVException e) {
                    if (e == null) {
                        onDataSuccessReceived(pageIndex, list);
                    } else {
                        showError(e);
                    }
                }
            });
        } else {
            showEmpty(getString(R.string.mine_no_login));
        }
    }

    @NonNull
    @Override
    protected String getEmptyMsg() {
        return getString(R.string.tips_no_collect);
    }
}
