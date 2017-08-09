package xyz.zimuju.sample.surface.user;

import xyz.zimuju.common.basal.BasalPresenter;

/*
 * @description SmsPresenter
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/10 - 1:11
 * @version 1.0.0
 */
public interface SmsPresenter extends BasalPresenter<RegisterView> {
    void obtain(String phone); // 获取验证码

    void querySmsState(Integer smsId); // 查询验证码状态

    void verifySmsCode(String phone, String code); // 查询验证状态
}
