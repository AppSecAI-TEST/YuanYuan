package xyz.zimuju.common.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import xyz.zimuju.common.R;
import xyz.zimuju.common.base.BaseBottomWindow;
import xyz.zimuju.common.util.StringUtils;

public class BottomMenuWindow extends BaseBottomWindow implements OnItemClickListener {
    private static final String TAG = "BottomMenuWindow";

    private ListView lvBottomMenu;
    private String title;
    private ArrayList<String> nameList = null;
    private ArrayList<Integer> idList = null;
    private ArrayAdapter<String> adapter;

    public static Intent createIntent(Context context, String[] names) {
        return createIntent(context, names, new ArrayList<Integer>());
    }

    public static Intent createIntent(Context context, ArrayList<String> nameList) {
        return createIntent(context, nameList, null);
    }

    public static Intent createIntent(Context context, String[] names, int[] ids) {
        return new Intent(context, BottomMenuWindow.class).
                putExtra(INTENT_ITEMS, names).
                putExtra(INTENT_ITEM_IDS, ids);
    }

    public static Intent createIntent(Context context, String[] names, ArrayList<Integer> idList) {
        return new Intent(context, BottomMenuWindow.class).
                putExtra(INTENT_ITEMS, names).
                putExtra(INTENT_ITEM_IDS, idList);
    }

    public static Intent createIntent(Context context, ArrayList<String> nameList, ArrayList<Integer> idList) {
        return new Intent(context, BottomMenuWindow.class).
                putStringArrayListExtra(INTENT_ITEMS, nameList).
                putIntegerArrayListExtra(INTENT_ITEM_IDS, idList);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_menu_window);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        super.initView();
        lvBottomMenu = (ListView) findViewById(R.id.lvBottomMenu);
    }

    @Override
    public void initData() {
        super.initData();
        intent = getIntent();
        title = intent.getStringExtra(INTENT_TITLE);
        if (StringUtils.isNotEmpty(title, true)) {
            tvBaseTitle.setVisibility(View.VISIBLE);
            tvBaseTitle.setText(StringUtils.getCurrentString());
        } else {
            tvBaseTitle.setVisibility(View.GONE);
        }


        int[] ids = intent.getIntArrayExtra(INTENT_ITEM_IDS);
        if (ids == null || ids.length <= 0) {
            idList = intent.getIntegerArrayListExtra(INTENT_ITEM_IDS);
        } else {
            idList = new ArrayList<Integer>();
            for (int id : ids) {
                idList.add(id);
            }
        }

        String[] menuItems = intent.getStringArrayExtra(INTENT_ITEMS);
        if (menuItems == null || menuItems.length <= 0) {
            nameList = intent.getStringArrayListExtra(INTENT_ITEMS);
        } else {
            nameList = new ArrayList<String>(Arrays.asList(menuItems));
        }
        if (nameList == null || nameList.size() <= 0) {
            Log.e(TAG, "init   nameList == null || nameList.size() <= 0 >> finish();return;");
            finish();
            return;
        }

        adapter = new ArrayAdapter<String>(this, R.layout.bottom_menu_item, R.id.tvBottomMenuItem, nameList);
        lvBottomMenu.setAdapter(adapter);

    }

    @Override
    public void initEvent() {
        super.initEvent();
        lvBottomMenu.setOnItemClickListener(this);
        vBaseBottomWindowRoot.setOnTouchListener(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent()
                .putExtra(RESULT_TITLE, StringUtils.getTrimedString(tvBaseTitle))
                .putExtra(RESULT_ITEM_ID, position);
        if (idList != null && idList.size() > position) {
            intent.putExtra(RESULT_ITEM_ID, idList.get(position));
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void setResult() {

    }
}