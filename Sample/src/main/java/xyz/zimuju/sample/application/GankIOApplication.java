package xyz.zimuju.sample.application;

import android.app.Application;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
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

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

        Bmob.initialize(this, ConfigConstants.BOMB_APPLICATION_ID);

        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();

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
