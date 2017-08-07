package xyz.zimuju.sample.application;

import android.app.Application;
import android.os.Environment;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import xyz.zimuju.sample.component.MultiTypeInstaller;
import xyz.zimuju.sample.constant.ConfigConstants;
import xyz.zimuju.sample.constant.LeanCloudConstants;
import xyz.zimuju.sample.surface.user.LoginActivity;
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

        AVInstallation.getCurrentInstallation().saveInBackground();


        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    // 关联  installationId 到用户表等操作……
                } else {
                    // 保存失败，输出错误信息
                }
            }
        });

        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, LoginActivity.class);
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
