package xyz.zimuju.sample.surface.user;

import xyz.zimuju.common.basal.BasalPresenter;

/*
 * @description RegisterPresenter
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 12:02
 * @version 1.0.0
 */
public interface RegisterPresenter extends BasalPresenter<RegisterView> {
    void obtain(String phone); // 获取验证码

    void querySmsState(String phone, String code); // 查询验证码状态

    void register(String... parameters);

    void login(String... parameters);
}
