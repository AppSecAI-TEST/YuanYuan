package xyz.zimuju.common.basal;

import android.app.Application;

import xyz.zimuju.common.R;
import xyz.zimuju.common.util.DataKeeper;
import xyz.zimuju.common.util.ImageLoaderUtil;
import xyz.zimuju.common.util.SettingUtil;

/*
 * @description BaseApplication
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/06/24 - 13:28
 * @version 1.0.0
 */
public class BaseApplication extends Application {
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    public void initialize(Application application) {
        if (instance == null) {
            instance = application;
        }

        DataKeeper.init(instance);
        SettingUtil.init(instance);
        ImageLoaderUtil.init(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    public String getAppName() {
        return getResources().getString(R.string.app_name);
    }

    public String getAppVersion() {
        return getResources().getString(R.string.app_version);
    }


}
