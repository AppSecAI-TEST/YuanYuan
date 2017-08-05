package xyz.zimuju.sample.surface.guide;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import xyz.zimuju.sample.R;

public class GuideNavigateActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_navigate);
        switch (getIntent().getIntExtra("fragmentId", -1)) {
            case 0:
                getSupportFragmentManager().beginTransaction().add(R.id.frame, new FirstGuideFragment()).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().add(R.id.frame, new SecondGuideFragment()).commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().add(R.id.frame, new FirstGuideFragment()).commit();
        }
    }
}
