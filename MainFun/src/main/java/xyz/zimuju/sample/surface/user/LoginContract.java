package xyz.zimuju.sample.surface.user;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import xyz.zimuju.common.rx.RxContract;

public class LoginContract extends RxContract<LoginView> implements LoginPresenter {
    @Override
    public void login(String... parameters) {
        AVUser.logInInBackground(parameters[0], parameters[1], new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    if (e == null) {
                        basalView.showToast("登陆成功");
                        basalView.loginResult(avUser);
                    } else {
                        basalView.showToast("errorCode=" + e.getCode() + ", errorMessage=" + e.getMessage());
                    }
                }
            }
        });
    }
}
