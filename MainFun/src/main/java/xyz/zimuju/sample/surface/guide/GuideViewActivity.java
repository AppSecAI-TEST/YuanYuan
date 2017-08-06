package xyz.zimuju.sample.surface.guide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import xyz.zimuju.guideview.Guide;
import xyz.zimuju.guideview.GuideBuilder;
import xyz.zimuju.guideview.OnVisibilityChangedListener;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.component.MultiComponent;


public class GuideViewActivity extends Activity {
    Guide guide;
    LinearLayout nearbyLayout, containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_guide_view);
        nearbyLayout = (LinearLayout) findViewById(R.id.guide_nearby_layout);
        containerLayout = (LinearLayout) findViewById(R.id.guide_container_layout);
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuideViewActivity.this, "show", Toast.LENGTH_SHORT).show();
            }
        });
        nearbyLayout.post(new Runnable() {
            @Override
            public void run() {
                showGuideView();
            }
        });
    }

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(nearbyLayout)
                .setFullingViewId(R.id.guide_container_layout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder.addComponent(new MultiComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(this);
    }
}
