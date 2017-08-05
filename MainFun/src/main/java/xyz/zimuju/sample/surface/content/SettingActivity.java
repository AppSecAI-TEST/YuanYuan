package xyz.zimuju.sample.surface.content;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.util.SettingUtil;
import xyz.zimuju.sample.R;

public class SettingActivity extends BaseActivity implements OnBottomDragListener {
    private static final String TAG = "SettingActivity";
    private ImageView[] ivSettings;
    private boolean[] settings;
    private int[] switchResIds = new int[]{R.mipmap.off, R.mipmap.on};
    private boolean isSettingChanged = false;

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity, this);
        initView();
        initData();
        initEvent();

    }

    @Override
    public void initView() {

        ivSettings = new ImageView[7];
        ivSettings[0] = (ImageView) findViewById(R.id.ivSettingCache);
        ivSettings[1] = (ImageView) findViewById(R.id.ivSettingPreload);

        ivSettings[2] = (ImageView) findViewById(R.id.ivSettingVoice);
        ivSettings[3] = (ImageView) findViewById(R.id.ivSettingVibrate);
        ivSettings[4] = (ImageView) findViewById(R.id.ivSettingNoDisturb);

        ivSettings[5] = (ImageView) findViewById(R.id.ivSettingTestMode);
        ivSettings[6] = (ImageView) findViewById(R.id.ivSettingFirstStart);

    }


    private void setSwitch(int which, boolean isToOn) {
        if (ivSettings == null || which < 0 || which >= ivSettings.length) {
            Log.e(TAG, "ivSettings == null || which < 0 || which >= ivSettings.length >> reutrn;");
            return;
        }

        ivSettings[which].setImageResource(switchResIds[isToOn ? 1 : 0]);
        settings[which] = isToOn;
    }


    @Override
    public void initData() {
        showProgressDialog(R.string.loading);

        runThread(TAG + "initData", new Runnable() {

            @Override
            public void run() {

                settings = SettingUtil.getAllBooleans(context);
                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        dismissProgressDialog();
                        if (settings == null || settings.length <= 0) {
                            finish();
                            return;
                        }
                        for (int i = 0; i < settings.length; i++) {
                            setSwitch(i, settings[i]);
                        }
                    }
                });
            }
        });


    }

    @Override
    public void initEvent() {

        for (int i = 0; i < ivSettings.length; i++) {
            final int which = i;
            ivSettings[which].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    isSettingChanged = true;
                    setSwitch(which, !settings[which]);
                }
            });
        }
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            SettingUtil.restoreDefault();
            initData();
            return;
        }

        finish();
    }

    @Override
    public void finish() {
        if (isSettingChanged) {
            showProgressDialog("正在保存设置，请稍后...");
            runThread(TAG, new Runnable() {

                @Override
                public void run() {

                    SettingUtil.putAllBoolean(settings);
                    isSettingChanged = false;
                    runUiThread(new Runnable() {

                        @Override
                        public void run() {
                            SettingActivity.this.finish();
                        }
                    });
                }
            });
            return;
        }

        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ivSettings = null;
        settings = null;
    }
}
