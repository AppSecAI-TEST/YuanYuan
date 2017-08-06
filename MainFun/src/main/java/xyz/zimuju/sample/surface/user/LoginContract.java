package xyz.zimuju.sample.surface.user;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import xyz.zimuju.common.rx.RxContract;

public class LoginContract extends RxContract<LoginView> implements LoginPresenter {
    @Override
    public void login(String... parameters) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(parameters[0]);
        bmobUser.setPassword(parameters[1]);
        bmobUser.login(new SaveListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    basalView.showToast("注册成功");
                } else {
                    basalView.showToast("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void getUserInfo() {
        BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        basalView.getUserInfoResult(bmobUser);
    }
}
