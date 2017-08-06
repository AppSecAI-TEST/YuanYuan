package xyz.zimuju.sample.surface.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.zimuju.common.basal.BasalActivity;
import xyz.zimuju.common.basal.BasalPresenter;
import xyz.zimuju.sample.R;

public class GuideHomeActivity extends BasalActivity implements View.OnClickListener {
    @BindView(R.id.guide_activity_tv)
    TextView guideActivity;

    @BindView(R.id.guide_activity_view_tv)
    TextView guideActivityView;

    @BindView(R.id.guide_list_tv)
    TextView guideList;

    @BindView(R.id.guide_fragment_tv)
    TextView guideFragment;

    @BindView(R.id.guide_fragmnet_view_tv)
    TextView guideFragmentView;

    @BindView(R.id.guide_more_tv)
    TextView guideMore;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_home;
    }

    @Override
    protected BasalPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void viewOption() {
        
    }

    @OnClick({R.id.guide_activity_tv, R.id.guide_activity_view_tv, R.id.guide_fragment_tv, R.id.guide_fragmnet_view_tv, R.id.guide_list_tv, R.id.guide_more_tv})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_activity_tv:
                startActivity(new Intent(getContext(), FullGuideActivity.class));
                break;

            case R.id.guide_fragment_tv:
                startActivity(new Intent(getContext(), GuideNavigateActivity.class).putExtra("fragmentId", 0));
                break;

            case R.id.guide_fragmnet_view_tv:
                startActivity(new Intent(getContext(), GuideNavigateActivity.class).putExtra("fragmentId", 1));
                break;

            case R.id.guide_list_tv:
                startActivity(new Intent(getContext(), ListGuideActivity.class));
                break;

            case R.id.guide_more_tv:
                startActivity(new Intent(getContext(), SimpleViewGuideActivity.class));
                break;

            case R.id.guide_activity_view_tv:
                startActivity(new Intent(getContext(), GuideViewActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
