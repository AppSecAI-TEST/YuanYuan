package xyz.zimuju.sample.surface.user;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;

import xyz.zimuju.common.rx.RxContract;

/*
 * @description RegisterContract
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 12:05
 * @version 1.0.0
 */
public class RegisterContract extends RxContract<RegisterView> implements RegisterPresenter {

    @Override
    public void obtain(String phone) {
        AVSMSOption option = new AVSMSOption();
        option.setTtl(10);                     // 验证码有效时间为 10 分钟
        option.setApplicationName("应用名称");
        option.setOperation("某种操作");
        AVSMS.requestSMSCodeInBackground(phone, option, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    basalView.showToast("验证码发送成功，请注意查收");
                    basalView.obtainResult();
                } else {
                    basalView.showToast("验证码发送失败，原因：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void register(final String... parameters) {
        AVUser user = new AVUser();
        user.setUsername(parameters[0]);
        user.setPassword(parameters[1]);
        user.setMobilePhoneNumber(parameters[3]);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    basalView.showToast("注册成功");
                    basalView.registerResult(parameters);
                } else {
                    basalView.showToast("注册失败：原因：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void login(String... parameters) {
        
    }

    @Override
    public void querySmsState(String phone, String code) {
        AVSMS.verifySMSCodeInBackground(code, phone, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    basalView.verifyCodeResult(true);
                } else {
                    basalView.showToast("验证码失效");
                    Log.d("Nathaniel", "验证失败：code =" + e.getCode() + ",msg = " + e.getLocalizedMessage());
                }
            }
        });
    }
}
