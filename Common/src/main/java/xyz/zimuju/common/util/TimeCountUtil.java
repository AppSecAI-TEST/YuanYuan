package xyz.zimuju.common.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;


/**
 * 倒计时工具类
 */
public class TimeCountUtil extends CountDownTimer {
    private ChangeListener changeListener;
    private boolean clickable = true;
    private TextView textView;

    public TimeCountUtil(Context ctx, long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    public TimeCountUtil(long millisInFuture, long countDownInterval, Button textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    public void setChangeListener(ChangeListener changeListener, boolean clickable) {
        this.changeListener = changeListener;
        this.clickable = clickable;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        if (clickable) {
            textView.setText("重新发送");
            textView.setClickable(true);
        } else {
            textView.setText("00");
            textView.setClickable(false);
            changeListener.finishLinstener();
        }
    }

    public void stop() {
        super.cancel();
        textView.setText("获取验证码");
        textView.setClickable(true);
    }

    public void close() {
        super.cancel();
        textView.setText("00");
        textView.setClickable(false);
    }

    public interface ChangeListener {
        void finishLinstener();
    }
}
