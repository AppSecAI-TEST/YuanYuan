package xyz.zimuju.sample.surface.guide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import xyz.zimuju.guideview.Component;
import xyz.zimuju.guideview.Guide;
import xyz.zimuju.guideview.GuideBuilder;
import xyz.zimuju.guideview.OnVisibilityChangedListener;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.component.MultiComponent;
import xyz.zimuju.sample.surface.component.SimpleComponent;

public class SimpleViewGuideActivity extends Activity {

    Guide guide;
    private TextView headerMenu;
    private LinearLayout nearbyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_guide_view);
        headerMenu = (TextView) findViewById(R.id.header_menu_tv);
        headerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SimpleViewGuideActivity.this, "show", Toast.LENGTH_SHORT).show();
            }
        });
        nearbyLayout = (LinearLayout) findViewById(R.id.guide_nearby_layout);
        headerMenu.post(new Runnable() {
            @Override
            public void run() {
                showGuideView();
            }
        });
    }

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(headerMenu)
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
                showGuideView2();
            }
        });

        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(SimpleViewGuideActivity.this);
    }

    public void showGuideView2() {
        final GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(nearbyLayout)
                .setAlpha(150)
                .setHighTargetGraphStyle(Component.CIRCLE)
                .setOverlayTarget(false)
                .setExitAnimationId(android.R.anim.fade_out)
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
        Guide guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(SimpleViewGuideActivity.this);
    }
}
