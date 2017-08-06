package xyz.zimuju.sample.surface.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.listener.SMSCodeListener;
import xyz.zimuju.common.basal.BasalActivity;
import xyz.zimuju.common.widget.ClearEditText;
import xyz.zimuju.sample.R;

/*
 * @description RegisterActivity
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 10:21
 * @version 1.0.0
 */
public class RegisterActivity extends BasalActivity<RegisterPresenter> implements View.OnClickListener, RegisterView {
    @BindView(R.id.login_username_cet)
    ClearEditText username;

    @BindView(R.id.login_password_cet)
    ClearEditText password;

    @BindView(R.id.login_confirm_cet)
    ClearEditText confirm;

    @BindView(R.id.login_phone_cet)
    ClearEditText phone;

    @BindView(R.id.login_obtain_tv)
    TextView obtain;

    @BindView(R.id.login_code_cet)
    ClearEditText code;

    @BindView(R.id.login_register_tv)
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.avtivity_register;
    }

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterContract();
    }

    @Override
    protected void initData() {
        BmobSMS.initialize(this, "猿媛", new MSMSCodeListener());
    }

    @Override
    protected void viewOption() {
        username.addTextChangedListener(new MTextWatcher(username));
        password.addTextChangedListener(new MTextWatcher(password));
        confirm.addTextChangedListener(new MTextWatcher(confirm));
        phone.addTextChangedListener(new MTextWatcher(phone));
        code.addTextChangedListener(new MTextWatcher(code));
    }

    @OnClick({R.id.login_obtain_tv, R.id.login_register_tv})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_obtain_tv:
                presenter.obtain(phone.getText().toString().trim());
                break;

            case R.id.login_register_tv:

                break;
        }
    }

    @Override
    public void obtainResult(Integer smsId) {
        // 查询发送验证码状态
        presenter.querySmsState(smsId);
    }

    @Override
    public void registerResult() {

    }

    private class MTextWatcher implements TextWatcher {
        private EditText editText;

        MTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (editText.getId()) {
                case R.id.login_username_cet:
                    CharSequence charSequence = getContext().getString(R.string.editor_digits);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (editText.getId()) {
                case R.id.login_username_cet:
                    if (s.length() < 6) {
                        showToast("用户名长度必须是至少是6个字符");
                        return;
                    }
                    break;

                case R.id.login_password_cet:
                    if (confirm.getText().length() > 0 && password.getText().equals(confirm.getText())) {
                        showToast("两次输入的密码不一致");
                        return;
                    }
                    break;

                case R.id.login_confirm_cet:
                    if (password.getText().equals(confirm.getText())) {
                        showToast("两次输入的密码不一致");
                        return;
                    }
                    break;

                case R.id.login_phone_cet:
                    if (phone.getText().length() != 11) {
                        showToast("电话号码不合法");
                        return;
                    }
                    break;
            }
        }
    }

    private class MSMSCodeListener implements SMSCodeListener {
        @Override
        public void onReceive(String string) {
            code.setText(string);
        }
    }
}
