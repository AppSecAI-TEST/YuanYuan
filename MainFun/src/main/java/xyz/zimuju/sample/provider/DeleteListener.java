package xyz.zimuju.sample.provider;

/*
 * @description DeleteListener
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 22:48
 * @version 1.0.0
 */
public interface DeleteListener {
    void onSuccess();

    void onFailure(int code, String message);
}
