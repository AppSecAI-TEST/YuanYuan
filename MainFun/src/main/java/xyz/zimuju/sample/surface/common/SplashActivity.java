package xyz.zimuju.sample.surface.common;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.zimuju.sample.R;

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
        setTheme(R.style.SplashTheme);
        setContentView(R.layout.activity_splash);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(findViewById(R.id.splash_logo_iv), "rotationY", 180, 360);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotate);
        animatorSet.setDuration(3000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // startActivity(new Intent(SplashActivity.this, MainActivity.class));
                Intent splashIntent = new Intent();
                splashIntent.setClass(SplashActivity.this, NavigationActivity.class);
                startActivity(splashIntent);
                finish();
            }
        });

        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}