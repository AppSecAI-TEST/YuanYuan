package xyz.zimuju.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import xyz.zimuju.common.R;
import xyz.zimuju.common.entity.BasalResult;

public class CommonUtils {

    private static final String TAG = "CommonUtil";
    private static DecimalFormat dec = new DecimalFormat("0.00");
    private static DecimalFormat decInt = new DecimalFormat("0");
    private static ProgressDialog progressDialog = null;

    // json 字符串先base64 解密，再zip 解压，再转换成对象T 输出
    public static <T> T gunZipJsonToData(String json, Class<T> clazz) {
        T data = null;
        if (EmptyUtil.isNotEmpty(json)) {
            long timeStart = System.currentTimeMillis();
            long size1 = json.length();
            String decompressed = ZipUtils.gunzip(json);
            if (EmptyUtil.isNotEmpty(decompressed)) {
                long size2 = decompressed.length();
                data = GsonUtil.processJson(decompressed, clazz);
                long timeEnd = System.currentTimeMillis();
                long timeDur = timeEnd - timeStart;
            }
        }
        return data;
    }

    public static List<String> getImageString(String str) {
        List<String> imgList = new ArrayList<>();
        String[] temp = null;
        temp = str.split("#");
        for (int i = 0; i < temp.length; i++) {
            if (i % 2 == 0) {
            } else {
                imgList.add(temp[i]);
            }
        }
        return imgList;
    }

    public static String getNoImageString(String str) {
        String str2 = "";
        String[] temp = null;
        temp = str.split("#");
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            if (i % 2 == 0) {
                stringBuilder2.append(temp[i]);
            } else {
            }
        }
        str2 = stringBuilder2.toString();
        return str2;
    }

    public static <T> T parseResData(BasalResult<T> res, Class<T> clazz) {
        T data = null;

        if ("gzip".equals(res.getCompress())) {
            data = gunZipJsonToData(res.getJson(), clazz);
        } else {
            data = res.getData();
        }

        return data;
    }

    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            return -1;
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public static void writeFileFromResponseBody(ResponseBody responseBody, File file) throws IOException {

        try {
            InputStream is = responseBody.byteStream();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
            }
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String formatSpeed(long size) {
        String hrSize;
        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);
        if (t > 1) {
            hrSize = dec.format(t).concat("TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat("GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat("MB");
        } else if (k > 1) {
            hrSize = decInt.format(k).concat("KB");
        } else {
            hrSize = decInt.format(b).concat("B");
        }
        return hrSize;
    }

    public static void call(Activity context, String phone) {
        if (StringUtils.isNotEmpty(phone, true)) {
            Uri uri = Uri.parse("tel:" + phone.trim());
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            toActivity(context, intent);
            return;
        }
        showShortToast(context, "请先选择号码哦~");
    }

    public static void toMessageChat(Activity context, List<String> phoneList) {
        if (context == null || phoneList == null || phoneList.size() <= 0) {
            Log.e(TAG, "sendMessage context == null || phoneList == null || phoneList.size() <= 0 " +
                    ">> showShortToast(context, 请先选择号码哦~); return; ");
            showShortToast(context, "请先选择号码哦~");
            return;
        }

        String phones = "";
        for (int i = 0; i < phoneList.size(); i++) {
            phones += phoneList.get(i) + ";";
        }
        toMessageChat(context, phones);
    }

    public static void toMessageChat(Activity context, String phone) {
        if (context == null || StringUtils.isNotEmpty(phone, true) == false) {
            Log.e(TAG, "sendMessage  context == null || StringUtil.isNotEmpty(phone, true) == false) >> return;");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("address", phone);
        intent.setType("vnd.android-dir/mms-sms");
        toActivity(context, intent);

    }

    public static void shareInfo(Activity context, String toShare) {
        if (context == null || StringUtils.isNotEmpty(toShare, true) == false) {
            Log.e(TAG, "shareInfo  context == null || StringUtil.isNotEmpty(toShare, true) == false >> return;");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
        intent.putExtra(Intent.EXTRA_TEXT, toShare.trim());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        toActivity(context, intent, -1);
    }

    public static void sendEmail(Activity context, String emailAddress) {
        if (context == null || StringUtils.isNotEmpty(emailAddress, true) == false) {
            Log.e(TAG, "sendEmail  context == null || StringUtil.isNotEmpty(emailAddress, true) == false >> return;");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));//缺少"mailto:"前缀导致找不到应用崩溃
        intent.putExtra(Intent.EXTRA_TEXT, "内容");  //最近在MIUI7上无内容导致无法跳到编辑邮箱界面
        toActivity(context, intent, -1);
    }

    public static void openWebSite(Activity context, String webSite) {
        if (context == null || StringUtils.isNotEmpty(webSite, true) == false) {
            Log.e(TAG, "openWebSite  context == null || StringUtil.isNotEmpty(webSite, true) == false >> return;");
            return;
        }

        toActivity(context, new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtils.getCorrectUrl(webSite))));
    }


    public static void copyText(Context context, String value) {
        if (context == null || StringUtils.isNotEmpty(value, true) == false) {
            Log.e(TAG, "copyText  context == null || StringUtil.isNotEmpty(value, true) == false >> return;");
            return;
        }

        ClipData cD = ClipData.newPlainText("simple text", value);
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(cD);
        showShortToast(context, "已复制\n" + value);
    }


    public static void toActivity(final Activity context, final Intent intent) {
        toActivity(context, intent, true);
    }


    public static void toActivity(final Activity context, final Intent intent, final boolean showAnimation) {
        toActivity(context, intent, -1, showAnimation);
    }


    public static void toActivity(final Activity context, final Intent intent, final int requestCode) {
        toActivity(context, intent, requestCode, true);
    }

    public static void toActivity(final Activity context, final Intent intent, final int requestCode, final boolean showAnimation) {
        if (context == null || intent == null) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (requestCode < 0) {
                    context.startActivity(intent);
                } else {
                    context.startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    context.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    context.overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }


    public static void showProgressDialog(Activity context, int stringResId) {
        try {
            showProgressDialog(context, null, context.getResources().getString(stringResId));
        } catch (Exception e) {
            Log.e(TAG, "showProgressDialog  showProgressDialog(Context context, null, context.getResources().getString(stringResId));");
        }
    }

    public static void showProgressDialog(final Activity context, final String dialogTitle, final String dialogMessage) {
        if (context == null) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
                if (progressDialog.isShowing() == true) {
                    progressDialog.dismiss();
                }
                if (dialogTitle != null && !"".equals(dialogTitle.trim())) {
                    progressDialog.setTitle(dialogTitle);
                }
                if (dialogMessage != null && !"".equals(dialogMessage.trim())) {
                    progressDialog.setMessage(dialogMessage);
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }


    public static void dismissProgressDialog(Activity context) {
        if (context == null || progressDialog == null || progressDialog.isShowing() == false) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }


    public static void showShortToast(Context context, int stringResId) {
        try {
            showShortToast(context, context.getResources().getString(stringResId));
        } catch (Exception e) {
            Log.e(TAG, "showShortToast  context.getResources().getString(resId) >>  catch (Exception e) {" + e.getMessage());
        }
    }

    public static void showShortToast(Context context, String string) {
        showShortToast(context, string, false);
    }


    public static void showShortToast(Context context, String string, boolean isForceDismissProgressDialog) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, "" + string, Toast.LENGTH_SHORT).show();
    }

    public static void startPhotoZoom(Activity context, int requestCode, String path, int width, int height) {
        startPhotoZoom(context, requestCode, Uri.fromFile(new File(path)), width, height);
    }

    public static void startPhotoZoom(Activity context, int requestCode, Uri fileUri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fileUri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        Log.i(TAG, "startPhotoZoom" + fileUri + " uri");
        toActivity(context, intent, requestCode);
    }


    public static String savePhotoToSDCard(String path, String photoName, String formSuffix, Bitmap photoBitmap) {
        if (photoBitmap == null || StringUtils.isNotEmpty(path, true) == false
                || StringUtils.isNotEmpty(StringUtils.getTrimedString(photoName)
                + StringUtils.getTrimedString(formSuffix), true) == false) {
            Log.e(TAG, "savePhotoToSDCard photoBitmap == null || StringUtil.isNotEmpty(path, true) == false" +
                    "|| StringUtil.isNotEmpty(photoName, true) == false) >> return null");
            return null;
        }

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName + "." + formSuffix); // 在指定路径下创建文件
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream)) {
                    fileOutputStream.flush();
                    Log.i(TAG, "savePhotoToSDCard<<<<<<<<<<<<<<\n" + photoFile.getAbsolutePath() + "\n>>>>>>>>> succeed!");
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "savePhotoToSDCard catch (FileNotFoundException e) {\n " + e.getMessage());
                photoFile.delete();
                //				e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "savePhotoToSDCard catch (IOException e) {\n " + e.getMessage());
                photoFile.delete();
                //				e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "savePhotoToSDCard } catch (IOException e) {\n " + e.getMessage());
                    //					e.printStackTrace();
                }
            }

            return photoFile.getAbsolutePath();
        }

        return null;
    }


    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }


    public static boolean isExitsSdcard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }


    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        return runningTaskInfos == null ? "" : runningTaskInfos.get(0).topActivity.getClassName();
    }


    public static boolean isHaveLocationPermission(Context context) {
        return isHavePermission(context, "android.permission.ACCESS_COARSE_LOCATION") || isHavePermission(context, "android.permission.ACCESS_FINE_LOCATION");
    }


    public static boolean isHavePermission(Context context, String name) {
        try {
            return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(name, context.getPackageName());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }


    public void showProgressDialog(Activity context, String dialogMessage) {
        showProgressDialog(context, null, dialogMessage);
    }
}
