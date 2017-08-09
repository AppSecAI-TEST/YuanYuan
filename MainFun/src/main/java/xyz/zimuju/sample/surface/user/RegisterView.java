package xyz.zimuju.sample.surface.user;

import cn.bmob.v3.BmobUser;
import xyz.zimuju.common.basal.BasalView;

/*
 * @description RegisterView
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 12:02
 * @version 1.0.0
 */
public interface RegisterView extends BasalView {
    void obtainResult(Integer smsId);

    void verifyCodeResult(boolean verified);

    void registerResult(String... parameters);

    void loginResult(BmobUser bmobUser);
}
