package xyz.zimuju.sample.surface.mine;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import xyz.zimuju.common.rx.RxContract;

import static com.yanzhenjie.nohttp.NoHttp.getContext;

public class LoginContract extends RxContract<LoginView> implements LoginPresenter {
    @Override
    public void login(String... parameters) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(parameters[0]);
        bmobUser.setPassword(parameters[1]);
        bmobUser.signUp(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                basalView.showToast("注册成功");
                getUserInfo();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                basalView.showToast("errorCode=" + errorCode + ", errorMessage=" + errorMessage);
            }
        });
    }

    @Override
    public void getUserInfo() {

    }
}
