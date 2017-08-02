package xyz.zimuju.sample.application;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import xyz.zimuju.sample.util.ToastUtils;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:20:59
 */
public class SolidApplication extends Application {
    private static SolidApplication solidApplication;

    public static SolidApplication getInstance() {
        return solidApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        solidApplication = this;
        ToastUtils.init(this);
    }

    @Override
    public File getCacheDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "/YuanYuan/Cache/");
        if (!file.exists()){
            file.mkdirs();
        }

        return file;
    }
}
