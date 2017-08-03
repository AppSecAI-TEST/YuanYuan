package xyz.zimuju.sample.application;

import android.app.Application;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import xyz.zimuju.sample.MultiTypeInstaller;
import xyz.zimuju.sample.util.PrefUtils;

public class GankIOApplication extends Application {
    private static GankIOApplication gankIOApplication;

    public static GankIOApplication getInstance() {
        if (gankIOApplication == null) {
            gankIOApplication = new GankIOApplication();
        }
        return gankIOApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "caed915330178bff62bfd281b627c77f");

        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        MultiTypeInstaller.install();

        // 初始化SharedPreferences
        PrefUtils.initialize(this);
    }

    @Override
    public File getCacheDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "/YuanYuan/Cache/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
