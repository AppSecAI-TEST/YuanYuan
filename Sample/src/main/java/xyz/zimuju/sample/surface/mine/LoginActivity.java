package xyz.zimuju.sample.surface.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import xyz.zimuju.common.util.ToastUtils;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.constant.ConfigConstants;
import xyz.zimuju.sample.engine.api.SinaApiService;
import xyz.zimuju.sample.entity.content.Weibo;
import xyz.zimuju.sample.event.LoginEvent;
import xyz.zimuju.sample.factory.ServiceFactory;
import xyz.zimuju.sample.rx.RxBus;
import xyz.zimuju.sample.rx.RxUtils;
import xyz.zimuju.sample.util.AuthorityUtils;

public class LoginActivity extends AppCompatActivity implements WeiboAuthListener {

    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private TextSwitcher mTextSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_activity_login);
        setUpToolBar();
        findViewById(R.id.ll_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        setTitle(getString(R.string.mine_login));
    }

    private void setUpToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressWarnings("deprecation")
            @Override
            public View makeView() {
                Context context = LoginActivity.this;
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
    }


    private void login() {
        mAuthInfo = new AuthInfo(this, ConfigConstants.SINA_APP_KEY, ConfigConstants.SINA_REDIRECT_URL, ConfigConstants.SINA_SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorize(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    public void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", AuthorityUtils.getAccessToken());
        params.put("uid", AuthorityUtils.getUid());

        SinaApiService service = ServiceFactory.getInstance().createService(SinaApiService.class);
        service.getUserInfo(AuthorityUtils.getAccessToken(), AuthorityUtils.getUid()).compose(RxUtils.<Weibo>defaultSchedulersSingle())
                .subscribe(new Consumer<Weibo>() {
                    @Override
                    public void accept(@NonNull Weibo result) throws Exception {
                        if (result != null) {
                            AuthorityUtils.setUserName(result.getName());
                            AuthorityUtils.login(result);
                            RxBus.getInstance().send(new LoginEvent(1));
                            BmobUser user = new BmobUser();
                            user.setUsername(result.getName());
                            user.setPassword("123456");
                            try {
                                user.signUp(LoginActivity.this, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            finish();
                        }
                    }
                });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
        mTextSwitcher.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onComplete(Bundle values) {
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {
            ToastUtils.showToast(this, "登录成功");
            AuthorityUtils.setUid(mAccessToken.getUid());
            AuthorityUtils.setAccessToken(mAccessToken.getToken());
            AuthorityUtils.setRefreshToken(mAccessToken.getRefreshToken());
            AuthorityUtils.setExpiresIn(mAccessToken.getExpiresTime());
            getUserInfo();
        } else {
            ToastUtils.showToast(this, values.getString("code", ""));
        }
    }

    @Override
    public void onCancel() {
        ToastUtils.showToast(this, "onCancel");
    }

    @Override
    public void onWeiboException(WeiboException e) {
        ToastUtils.showToast(this, "微博授权异常");
    }
}
