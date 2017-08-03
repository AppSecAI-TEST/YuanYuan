package xyz.zimuju.sample.surface.gank.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import xyz.zimuju.sample.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.gank_activity_splash;
    }

    @Override
    protected void setUpView() {
        ObjectAnimator rotate = ObjectAnimator.ofFloat($(R.id.iv_logo), "rotationY", 180, 360);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotate);
        animatorSet.setDuration(2000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}