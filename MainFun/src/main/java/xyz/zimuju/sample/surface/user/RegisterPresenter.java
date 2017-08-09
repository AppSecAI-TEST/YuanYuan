package xyz.zimuju.sample.surface.user;

/*
 * @description RegisterPresenter
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 12:02
 * @version 1.0.0
 */
public interface RegisterPresenter extends SmsPresenter {
    void register(String... parameters);

    void login(String... parameters);
}
