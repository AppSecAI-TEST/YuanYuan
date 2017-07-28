package xyz.zimuju.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/*
 * @description InstallApk 安装APK文件
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-11-9-下午4:00
 * @version v1.0.0
 */
public class InstallApk {
    public static void startInstall(Context context, File apkFile) {
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            //Uri apkUri = FileProvider.getUriForFile(context, "com.mba.logic.main.fileprovider", file);
            Uri apkUri = FileProvider.getUriForFile(context, "com.master.service.fileprovider", apkFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            //	intent =  new Intent();
            // intent.setAction("android.intent.action.VIEW");
            // intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

    }
}
