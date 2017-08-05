package xyz.zimuju.sample.application;

import android.app.Application;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;
import xyz.zimuju.sample.MultiTypeInstaller;
import xyz.zimuju.sample.constant.ConfigConstants;
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

        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId(ConfigConstants.BOMB_APPLICATION_ID)
                .setConnectTimeout(30) //请求超时时间（单位为秒）：默认15s
                .setUploadBlockSize(1024 * 1024) //文件分片上传时每片的大小（单位字节），默认512*1024
                .setFileExpiration(2500) //文件的过期时间(单位为秒)：默认1800s
                .build();
        Bmob.initialize(config);

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

        // 初始化UserApplication
        UserApplication.getInstance().initialize(this);
    }

    @Override
    public File getCacheDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "/YuanYuan/Cache/");
        if (! file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
