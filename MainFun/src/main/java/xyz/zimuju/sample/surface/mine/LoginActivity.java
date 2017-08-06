package xyz.zimuju.sample.surface.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.zimuju.common.basal.BasalActivity;
import xyz.zimuju.sample.R;

/*
 * @description LoginActivity ：登陆界面
 * @author Nathaniel
 * @time 2017/8/4 - 9:36
 * @version 1.0.0
 */
public class LoginActivity extends BasalActivity<LoginPresenter> implements View.OnClickListener, LoginView {
    @BindView(R.id.header_back_tv)
    TextView back;

    @BindView(R.id.header_title_tv)
    TextView title;

    @BindView(R.id.login_username_tv)
    TextView username;

    @BindView(R.id.login_password_tv)
    TextView password;

    @BindView(R.id.login_submit_tv)
    TextView submit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginContract();
    }

    @Override
    protected void initData() {
        title.setText(getString(R.string.mine_login));
        back.setText(R.string.common_tip_back);
    }

    @Override
    protected void viewOption() {
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getUserInfo() {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.header_back_tv, R.id.login_submit_tv})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back_tv:
                finish();
                break;

            case R.id.login_submit_tv:
                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                presenter.login(usernameText, passwordText);
                break;
        }
    }

    @Override
    public void loginResult() {

    }
}
