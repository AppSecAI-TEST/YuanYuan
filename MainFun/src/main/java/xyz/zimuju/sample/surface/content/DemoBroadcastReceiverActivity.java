package xyz.zimuju.sample.surface.content;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.base.BaseBroadcastReceiver;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.receiver.HeadsetConnectionBroadcastReceiver;


public class DemoBroadcastReceiverActivity extends BaseActivity implements OnBottomDragListener {
    public static final String STATE = "state";
    private static final String TAG = "DemoBroadcastReceiverActivity";
    private BaseBroadcastReceiver baseBroadcastReceiver;//BaseBroadcastReceiver直接使用示例
    private DemoBroadcastReceiver demoBroadcastReceiver;//内部类BaseBroadcastReceiver子类使用示例
    private HeadsetConnectionBroadcastReceiver headsetConnectionBroadcastReceiver;//外部类BaseBroadcastReceiver子类使用示例

    public static Intent createIntent(Context context) {
        return new Intent(context, DemoBroadcastReceiverActivity.class);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_broadcast_receiver_activity, this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        autoSetTitle();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {


        baseBroadcastReceiver = new BaseBroadcastReceiver(context) {
            @Override
            public BaseBroadcastReceiver register() {//支持String, String[], List<String>
                return (BaseBroadcastReceiver) register(context, this, "android.intent.action.HEADSET_PLUG");
            }

            @Override
            public void unregister() {
                unregister(context, this);
            }

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.hasExtra(STATE)) {
                    showShortToast("baseBroadcastReceiver\n" + (intent.getIntExtra(STATE, 0) == 1 ? "已插入耳机" : "请插入耳机"));
                }
            }
        }.register();

        demoBroadcastReceiver = new DemoBroadcastReceiver(context);
        demoBroadcastReceiver.register();

        headsetConnectionBroadcastReceiver = new HeadsetConnectionBroadcastReceiver(context).register(new HeadsetConnectionBroadcastReceiver.OnHeadsetConnectionChangedListener() {
            @Override
            public void onHeadsetConnectionChanged(boolean isConnected) {
                showShortToast("headsetConnectionBroadcastReceiver\n" + (isConnected ? "已插入耳机" : "请插入耳机"));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseBroadcastReceiver.unregister();
        demoBroadcastReceiver.unregister();
        headsetConnectionBroadcastReceiver.unregister();
    }

    public class DemoBroadcastReceiver extends BaseBroadcastReceiver {

        public DemoBroadcastReceiver(Context context) {
            super(context);
        }

        @Override
        public BaseBroadcastReceiver register() {//支持String, String[], List<String>
            return (BaseBroadcastReceiver) register(context, this, "android.intent.action.HEADSET_PLUG");
        }

        @Override
        public void unregister() {
            unregister(context, this);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra(STATE)) {
                showShortToast("demoBroadcastReceiver\n" + (intent.getIntExtra(STATE, 0) == 1 ? "已插入耳机" : "请插入耳机"));
            }
        }
    }
}