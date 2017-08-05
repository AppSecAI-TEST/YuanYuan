package xyz.zimuju.sample.surface.content;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.zimuju.common.base.BaseTabFragment;
import xyz.zimuju.common.util.PlaceUtil;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.common.widget.PlacePickerWindow;
import xyz.zimuju.sample.R;


public class DemoTabFragment extends BaseTabFragment implements View.OnClickListener {
    public static final String ARGUMENT_CITY = "ARGUMENT_CITY";
    private static final int REQUEST_TO_PLACE_PICKER = 10;
    private String city;
    private TextView tvDemoTabLeft;

    public static DemoTabFragment createInstance(String city) {
        DemoTabFragment fragment = new DemoTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_CITY, city);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.demo_tab_fragment);
        argument = getArguments();
        if (argument != null) {
            city = argument.getString(ARGUMENT_CITY, city);
        }

        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        tvDemoTabLeft = findViewById(R.id.tvDemoTabLeft);
    }


    public void selectPlace() {
        toActivity(PlacePickerWindow.createIntent(context, context.getPackageName(), 2), REQUEST_TO_PLACE_PICKER, false);
    }

    public void selectMan() {
        toActivity(DemoListActivity.createIntent(context, 0).putExtra(DemoTabActivity.INTENT_TITLE, "筛选"));
    }

    @Override
    public void initData() {
        super.initData();
        tvDemoTabLeft.setText(StringUtils.isNotEmpty(city, true) ? StringUtils.getTrimedString(city) : "杭州");
    }

    @Override
    @Nullable
    public String getTitleName() {
        return null;
    }

    @Override
    @Nullable
    public String getReturnName() {
        return null;
    }

    @Override
    protected String[] getTabNames() {
        return new String[]{"附近", "热门"};
    }

    @Override
    protected Fragment getFragment(int position) {

        DemoListFragment fragment = DemoListFragment.createInstance();
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(DemoListFragment.ARGUMENT_POSITION, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        tvDemoTabLeft.setOnClickListener(this);
        findViewById(R.id.tvDemoTabRight).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvDemoTabLeft:
                selectPlace();
                break;
            case R.id.tvDemoTabRight:
                selectMan();
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_TO_PLACE_PICKER:
                if (data != null) {
                    ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
                    if (placeList != null && placeList.size() > PlaceUtil.LEVEL_CITY) {
                        tvDemoTabLeft.setText(StringUtils.getTrimedString(placeList.get(PlaceUtil.LEVEL_CITY)));
                    }
                }
                break;
            default:
                break;
        }
    }
}