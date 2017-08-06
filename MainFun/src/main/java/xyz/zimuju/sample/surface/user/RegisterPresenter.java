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
    void obtain(String phone);

    void register(String parameters);

    void querySmsState(Integer smsId);
}
