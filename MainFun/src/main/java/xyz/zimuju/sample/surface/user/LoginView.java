package xyz.zimuju.sample.surface.user;

import com.avos.avoscloud.AVUser;

import xyz.zimuju.common.basal.BasalView;

/*
 * @description LoginView 登录的View
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 8:59
 * @version 1.0.0
 */
public interface LoginView extends BasalView {
    void loginResult(AVUser avUser);
}
