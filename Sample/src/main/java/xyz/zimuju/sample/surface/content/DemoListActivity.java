package xyz.zimuju.sample.surface.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.base.BaseListActivity;
import xyz.zimuju.common.defination.AdapterCallBack;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.common.widget.GridAdapter;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.util.TestUtil;

public class DemoListActivity extends BaseListActivity<Entry<String, String>, GridView, GridAdapter> implements OnBottomDragListener {
    public static final String INTENT_RANGE = "INTENT_RANGE";
    public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";
    private int range = 0;

    public static Intent createIntent(Context context, int range) {
        return new Intent(context, DemoListActivity.class).putExtra(INTENT_RANGE, range);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO demo_list_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
        setContentView(R.layout.demo_list_activity, this);
        intent = getIntent();
        range = intent.getIntExtra(INTENT_RANGE, range);
        initView();
        initData();
        initEvent();
        onRefresh();
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void setList(final List<Entry<String, String>> list) {
        setList(new AdapterCallBack<GridAdapter>() {

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }

            @Override
            public GridAdapter createAdapter() {
                return new GridAdapter(context);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        tvBaseTitle.setText("" + lvBaseList.getClass().getSimpleName());
        showShortToast("range = " + range);
    }

    @Override
    public void getListAsync(int page) {
        showProgressDialog(R.string.loading);
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
        for (int i = 0; i < 6; i++) {
            list.add(new Entry<String, String>(getPictureUrl(i + 6 * page), "联系人" + i + 6 * page));
        }
        onLoadSucceed(page, list);
    }


    private String getPictureUrl(int userId) {
        return TestUtil.getPicture(userId % 6);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        lvBaseList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showShortToast("选择了 " + adapter.getItem(position).getValue());
                setResult(RESULT_OK, new Intent().putExtra(RESULT_CLICKED_ITEM, position));
                finish();
            }
        });
    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }
}