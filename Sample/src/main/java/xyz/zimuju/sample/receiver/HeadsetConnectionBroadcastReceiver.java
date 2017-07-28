package xyz.zimuju.sample.receiver;


import android.content.Context;
import android.content.Intent;

import xyz.zimuju.common.base.BaseBroadcastReceiver;

public class HeadsetConnectionBroadcastReceiver extends BaseBroadcastReceiver {
    public static final String STATE = "state";
    private static final String TAG = "HeadsetConnectionBroadcastReceiver";
    private OnHeadsetConnectionChangedListener onHeadsetConnectionChangedListener;

    public HeadsetConnectionBroadcastReceiver(Context context) {
        super(context);
    }

    public HeadsetConnectionBroadcastReceiver register(OnHeadsetConnectionChangedListener listener) {
        this.onHeadsetConnectionChangedListener = listener;
        register();
        return this;
    }

    @Override
    public BaseBroadcastReceiver register() {
        //TODO android.intent.action.HEADSET_PLUG改为你需要的action，//支持String, String[], List<String>
        return (BaseBroadcastReceiver) register(context, this, "android.intent.action.HEADSET_PLUG");
    }

    @Override
    public void unregister() {
        unregister(context, this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent != null && intent.hasExtra(STATE)) {
            if (onHeadsetConnectionChangedListener != null) {
                onHeadsetConnectionChangedListener.onHeadsetConnectionChanged(intent.getIntExtra(STATE, 0) == 1);
            }
        }
    }

    public interface OnHeadsetConnectionChangedListener {
        void onHeadsetConnectionChanged(boolean isConnected);
    }

}
