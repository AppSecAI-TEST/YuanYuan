/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package xyz.zimuju.sample.surface.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.manager.TimeRefresher;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.R;


public class DemoTimeRefresherActivity extends BaseActivity implements View.OnClickListener, OnBottomDragListener, TimeRefresher.OnTimeRefreshListener {
    private static final String TAG = "DemoTimeRefresherActivity";
    private TextView tvDemoTimeRefresherCount;
    private EditText etDemoTimeRefresher;
    private boolean isToStop = false;
    private int count = 0;

    public static Intent createIntent(Context context) {
        return new Intent(context, DemoTimeRefresherActivity.class);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_time_refresher_activity, this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvDemoTimeRefresherCount = (TextView) findViewById(R.id.tvDemoTimeRefresherCount);
        etDemoTimeRefresher = (EditText) findViewById(R.id.etDemoTimeRefresher);
    }

    private void clear() {
        TimeRefresher.getInstance().removeTimeRefreshListener(TAG);
        count = 0;
        tvDemoTimeRefresherCount.setText("0");
    }

    private void stopOrContinu() {
        isToStop = !isToStop;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        tvDemoTimeRefresherCount.setOnClickListener(this);
        findViewById(R.id.ibtnDemoTimeRefresher).setOnClickListener(this);
    }

    @Override
    public void onTimerStart() {
        showShortToast("start");
    }

    @Override
    public void onTimerRefresh() {
        if (isToStop == false) {
            count++;
            tvDemoTimeRefresherCount.setText("" + count);
        }
    }

    @Override
    public void onTimerStop() {
        showShortToast("stop");
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            clear();
            return;
        }

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDemoTimeRefresherCount:
                stopOrContinu();
                break;
            case R.id.ibtnDemoTimeRefresher:
                clear();
                isToStop = false;

                String number = StringUtils.getNumber(etDemoTimeRefresher);
                if (StringUtils.isNotEmpty(number, true)) {
                    TimeRefresher.getInstance().addTimeRefreshListener(TAG
                            , 0 + Integer.valueOf(number), this);
                }
                break;
            default:
                break;
        }
    }
    //示例代码>>>>>>>>>>>>>>>>>>>


    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    protected void onDestroy() {
        TimeRefresher.getInstance().removeTimeRefreshListener(TAG);
        super.onDestroy();
    }

    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}