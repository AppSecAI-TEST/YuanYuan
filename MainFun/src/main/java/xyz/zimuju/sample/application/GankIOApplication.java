package xyz.zimuju.sample.application;

import android.app.Application;
import android.os.Environment;

import com.avos.avoscloud.AVOSCloud;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import xyz.zimuju.sample.component.MultiTypeInstaller;
import xyz.zimuju.sample.constant.ConfigConstants;
import xyz.zimuju.sample.constant.LeanCloudConstants;
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

        Bmob.initialize(this, ConfigConstants.BOMB_APPLICATION_ID);

        BmobSMS.initialize(this, ConfigConstants.BOMB_APPLICATION_ID);

        // 使用推送服务时的初始化操作
        // BmobInstallation.getCurrentInstallation().save();

        // 启动推送服务
        // BmobPush.startWork(this);

        // 内存泄露检测
        if (ConfigConstants.debugEnable) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }

        MultiTypeInstaller.install();

        // 初始化SharedPreferences
        PrefUtils.initialize(this);

        // 初始化UserApplication
        UserApplication.getInstance().initialize(this);

        // ----------------------------------------
        // 启用北美节点, 需要在 initialize 之前调用
        // AVOSCloud.useAVCloudUS();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, LeanCloudConstants.APPLICATION_ID, LeanCloudConstants.APPLICATION_KEY);
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
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
