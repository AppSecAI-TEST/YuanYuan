package xyz.zimuju.sample.surface.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.common.widget.PageScroller;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.adapter.content.DemoAdapter2;

public class DemoActivity extends BaseActivity implements OnBottomDragListener {
    public static final String INTENT_USER_ID = "INTENT_USER_ID";
    public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";
    private static final String TAG = "DemoActivity";
    private long userId = 0;
    private ListView lvDemo;
    private DemoAdapter2 adapter;
    private List<Entry<String, String>> list;

    public static Intent createIntent(Context context, long userId) {
        return new Intent(context, DemoActivity.class).putExtra(INTENT_USER_ID, userId);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO demo_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
        setContentView(R.layout.demo_activity, this);

        intent = getIntent();
        userId = intent.getLongExtra(INTENT_USER_ID, userId);

        initView();
        initData();
        initEvent();

        Toast.makeText(context, "这是一个分页列表，中速滑动直接滚动一页。\n如果不需要则把PageScroller相关代码去掉"
                , Toast.LENGTH_LONG).show();
    }

    @Override
    public void initView() {
        autoSetTitle();
        lvDemo = (ListView) findViewById(R.id.lvDemo);
    }


    private void setList(List<Entry<String, String>> list) {
        if (adapter == null) {
            adapter = new DemoAdapter2(context);
            lvDemo.setAdapter(adapter);
        }
        adapter.refresh(list);
    }


    @Override
    public void initData() {

        showProgressDialog(R.string.loading);

        runThread(TAG + "initData", new Runnable() {
            @Override
            public void run() {

                list = getList(userId);
                runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        setList(list);
                    }
                });
            }
        });

    }


    /**
     * 示例方法：获取列表
     *
     * @param userId
     * @return
     * @author Lemon
     */
    protected List<Entry<String, String>> getList(long userId) {
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
        for (int i = 0; i < 64; i++) {
            list.add(new Entry<String, String>("联系人" + i + userId, String.valueOf(1311736568 + i * i + userId)));
        }
        return list;
    }

    /**
     * 示例方法：添加列表
     *
     * @author Lemon
     */
    private void addList() {
        int formerCout = adapter == null ? 0 : list.size() - 1;

        if (list == null) {
            list = new ArrayList<Entry<String, String>>();
        }
        userId += list.size();
        list.addAll(getList(userId));

        if (adapter != null) {
            adapter.refresh(list);
        }
        lvDemo.smoothScrollToPosition(formerCout);
    }


    @Override
    public void initEvent() {//必须在onCreate方法内调用

        lvDemo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setResult(RESULT_OK, new Intent().putExtra(RESULT_CLICKED_ITEM, position));
                finish();
            }
        });

        new PageScroller(lvDemo).init();
    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            addList();

            return;
        }

        finish();
    }

}