package xyz.zimuju.sample.surface.content;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import xyz.zimuju.common.widget.custom.CustomToast;
import xyz.zimuju.sample.R;


public class CustomToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toast);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_text:
                CustomToast.showText(this, "简单提示");
                break;

            case R.id.btn_text_true:
                CustomToast.showText(this, "简单提示 正确图标", true);
                break;

            case R.id.btn_text_false:
                CustomToast.showText(this, "简单提示 错误图标", false);
                break;

            case R.id.btn_text_long:
                CustomToast.showText(this, "简单提示 长~ ", Toast.LENGTH_LONG);
                break;

            case R.id.btn_text_true_long:
                CustomToast.showText(this, "简单提示 正确图标 长~ ", Toast.LENGTH_LONG, true);
                break;

            case R.id.btn_text_false_long:
                CustomToast.showText(this, "简单提示 错误图标 长~ ", Toast.LENGTH_LONG, false);
                break;
        }
    }

}
