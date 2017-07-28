package xyz.zimuju.sample.surface.content;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.guide.GuideHomeActivity;

/*
 * @description SplashActivity : 启动页面
 * @author Nathaniel 
 * @email nathanwriting@126.com
 * @time 2017/7/16 - 23:05
 * @version 1.0.0
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent splashIntent = new Intent();
                splashIntent.setClass(SplashActivity.this, GuideHomeActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }, 3 * 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

}