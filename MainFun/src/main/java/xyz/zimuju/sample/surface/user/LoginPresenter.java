package xyz.zimuju.sample.surface.user;

import xyz.zimuju.common.basal.BasalPresenter;

/*
 * @description LoginPresenter 登录的Presenter
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 9:00
 * @version 1.0.0
 */
public interface LoginPresenter extends BasalPresenter<LoginView> {
    void login(String... parameters);

    void getUserInfo();
}
