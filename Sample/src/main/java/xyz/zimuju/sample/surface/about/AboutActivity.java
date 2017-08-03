package xyz.zimuju.sample.surface.about;

import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.gank.activity.BaseActivity;
import xyz.zimuju.sample.util.SpannableStringUtils;
import xyz.zimuju.sample.util.SystemUtils;

/**
 * Created by _SOLID
 * Date:2016/5/5
 * Time:10:30
 */
public class AboutActivity extends BaseActivity {

    private TextView tv_version;
    private TextView tv_msg;

    @Override
    protected void initView() {

        Toolbar toolbar = $(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_msg = $(R.id.tv_msg);
        tv_version = $(R.id.tv_version);
        tv_version.setText("v" + SystemUtils.getAppVersion(this));

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(getString(R.string.about_msg));
        builder.append("\n");
        builder.append("\n");
        builder.append(SpannableStringUtils.format(this,
                getString(R.string.about_author),
                R.style.AboutItemText));
        builder.append("\n");
        builder.append(SpannableStringUtils.format(this,
                getString(R.string.about_github),
                R.style.AboutItemText));
        builder.append("\n");
        builder.append(SpannableStringUtils.format(this,
                getString(R.string.about_blog),
                R.style.AboutItemText));
        builder.append("\n");
        builder.append(SpannableStringUtils.format(this,
                getString(R.string.about_weibo),
                R.style.AboutItemText));
        builder.append("\n");
        builder.append(SpannableStringUtils.format(this,
                getString(R.string.about_project),
                R.style.AboutItemText));
        tv_msg.setText(builder.subSequence(0, builder.length()));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.gank_activity_about;
    }

}
