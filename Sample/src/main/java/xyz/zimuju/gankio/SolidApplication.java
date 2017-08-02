package xyz.zimuju.gankio;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import xyz.zimuju.gankio.utils.ToastUtils;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:20:59
 */
public class SolidApplication extends Application {
    private static SolidApplication mInstance;

    public static SolidApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ToastUtils.init(this);
    }

    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getDownloadCacheDirectory();
        } else {
            return super.getCacheDir();
        }
    }
}
