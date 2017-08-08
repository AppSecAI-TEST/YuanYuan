package xyz.zimuju.cloudreader.surface.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import xyz.zimuju.cloudreader.R;
import xyz.zimuju.cloudreader.base.BaseActivity;
import xyz.zimuju.cloudreader.databinding.ActivityNavDownloadBinding;
import xyz.zimuju.cloudreader.util.PerfectClickListener;
import xyz.zimuju.cloudreader.util.QRCodeUtil;
import xyz.zimuju.cloudreader.util.ShareUtils;

public class NavDownloadActivity extends BaseActivity<ActivityNavDownloadBinding> {

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();

        setTitle("扫码下载");
        String url = "https://fir.im/cloudreader";
        QRCodeUtil.showThreadImage(this, url, bindingView.ivErweima, R.drawable.ic_cloudreader_mip);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }
}
