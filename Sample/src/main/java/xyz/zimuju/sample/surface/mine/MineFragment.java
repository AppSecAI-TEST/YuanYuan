package xyz.zimuju.sample.surface.mine;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import xyz.zimuju.common.util.ToastUtils;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.SettingCenter;
import xyz.zimuju.sample.event.LoginEvent;
import xyz.zimuju.sample.loader.ImageLoader;
import xyz.zimuju.sample.rx.RxBus;
import xyz.zimuju.sample.surface.about.AboutActivity;
import xyz.zimuju.sample.surface.gank.BaseFragment;
import xyz.zimuju.sample.surface.gank.SubActivity;
import xyz.zimuju.sample.util.AppUtils;
import xyz.zimuju.sample.util.AuthorityUtils;
import xyz.zimuju.sample.util.SpannableStringUtils;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.mine_username_tv)
    TextView username;

    @BindView(R.id.mine_portrait_iv)
    ImageView portrait;

    @BindView(R.id.mine_clear_cache_tv)
    TextView clearCache;

    @BindView(R.id.mine_feedback_tv)
    TextView feedback;

    @BindView(R.id.mine_about_tv)
    TextView aboutUs;

    @BindView(R.id.mine_logout_tv)
    TextView logout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {
        RxBus.getInstance()
                .toObservable(LoginEvent.class)
                .compose(this.<LoginEvent>bindToLifecycle())
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(@NonNull LoginEvent loginEvent) throws Exception {
                        refreshUserInfo();
                    }
                });
        SettingCenter.countDirSizeTask(new SettingCenter.CountDirSizeListener() {
            @Override
            public void onResult(long result) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(getString(R.string.mine_cache_clear)).append("  ");
                builder.append(SpannableStringUtils.format(getContext(), "(" + SettingCenter.formatFileSize(result) + ")", R.style.ByTextAppearance));
                clearCache.setText(builder);
            }
        });
    }

    @Override
    public void refreshData() {
        refreshUserInfo();
    }

    @OnClick({R.id.mine_collect_tv, R.id.mine_username_tv, R.id.mine_feedback_tv, R.id.mine_about_tv, R.id.mine_logout_tv, R.id.mine_clear_cache_tv})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_collect_tv:
                if (!AuthorityUtils.isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    SubActivity.start(getContext(), getString(R.string.mine_collect), SubActivity.TYPE_COLLECT);
                }
                break;

            case R.id.mine_username_tv:
                if (!AuthorityUtils.isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;

            case R.id.mine_feedback_tv:
                AppUtils.feedBack(getContext(), view);
                break;

            case R.id.mine_about_tv:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;

            case R.id.mine_logout_tv:
                AppUtils.logOut(getContext(), new AppUtils.OnSuccessListener() {
                    @Override
                    public void onSuccess() {
                        refreshUserInfo();
                    }
                });
                break;

            case R.id.mine_clear_cache_tv:
                AppUtils.clearCache(getContext(), new SettingCenter.ClearCacheListener() {
                    @Override
                    public void onResult() {
                        ToastUtils.showToast(getContext(), getString(R.string.cache_clear_success));
                        clearCache.setText(getString(R.string.mine_cache_clear));
                    }
                });
                break;
        }
    }

    private void refreshUserInfo() {
        if (AuthorityUtils.isLogin()) {
            username.setText(AuthorityUtils.getUserName());
            ImageLoader.displayImage(portrait, AuthorityUtils.getAvatar());
        } else {
            username.setText(getString(R.string.mine_click_login));
            portrait.setImageDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark)));
        }
    }
}
