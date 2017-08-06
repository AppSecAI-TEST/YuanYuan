package xyz.zimuju.sample.surface.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.zimuju.common.basal.BasalActivity;
import xyz.zimuju.common.basal.BasalPresenter;
import xyz.zimuju.common.util.ToastUtils;
import xyz.zimuju.guideview.Guide;
import xyz.zimuju.guideview.GuideBuilder;
import xyz.zimuju.guideview.OnVisibilityChangedListener;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.component.SimpleComponent;

public class FullGuideActivity extends BasalActivity implements View.OnClickListener {

    Guide guide;

    @BindView(R.id.header_menu_tv)
    TextView headerMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_guide_view;
    }

    @Override
    protected BasalPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        headerMenu.post(new Runnable() {
            @Override
            public void run() {
                showGuideView();
            }
        });
    }

    @Override
    protected void viewOption() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.header_menu_tv)
    @Override
    public void onClick(View view) {
        ToastUtils.showToast(getContext(), "menu");
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
            }
        });

        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(this);
    }
}