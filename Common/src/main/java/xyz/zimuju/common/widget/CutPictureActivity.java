package xyz.zimuju.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import xyz.zimuju.common.R;
import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.util.CommonUtils;
import xyz.zimuju.common.util.DataKeeper;
import xyz.zimuju.common.util.StringUtils;


public class CutPictureActivity extends BaseActivity {
    public static final String INTENT_ORIGINAL_PICTURE_PATH = "INTENT_ORIGINAL_PICTURE_PATH";

    public static final String INTENT_CUTTED_PICTURE_PATH = "INTENT_CUTTED_PICTURE_PATH";
    public static final String INTENT_CUTTED_PICTURE_NAME = "INTENT_CUTTED_PICTURE_NAME";
    public static final String INTENT_CUT_WIDTH = "INTENT_CUT_WIDTH";
    public static final String INTENT_CUT_HEIGHT = "INTENT_CUT_HEIGHT";
    public static final int REQUEST_CODE_CAMERA = 18;
    public static final int REQUEST_CODE_LOCAL = 19;
    public static final int REQUEST_CUT_PHOTO = 20;

    public static final String RESULT_PICTURE_PATH = "RESULT_PICTURE_PATH";
    private static final String TAG = "CutPictureActivity";
    private String originalPicturePath;
    private String cuttedPicturePath;
    private String cuttedPictureName;
    private int cuttedWidth;
    private int cuttedHeight;

    public static Intent createIntent(Context context, String originalPath, String cuttedPath, String cuttedName, int cuttedSize) {
        return createIntent(context, originalPath, cuttedPath, cuttedName, cuttedSize, cuttedSize);
    }


    public static Intent createIntent(Context context, String originalPath, String cuttedPath, String cuttedName, int cuttedWidth, int cuttedHeight) {
        Intent intent = new Intent(context, CutPictureActivity.class);
        intent.putExtra(INTENT_ORIGINAL_PICTURE_PATH, originalPath);
        intent.putExtra(INTENT_CUTTED_PICTURE_PATH, cuttedPath);
        intent.putExtra(INTENT_CUTTED_PICTURE_NAME, cuttedName);
        intent.putExtra(INTENT_CUT_WIDTH, cuttedWidth);
        intent.putExtra(INTENT_CUT_HEIGHT, cuttedHeight);
        return intent;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        originalPicturePath = intent.getStringExtra(INTENT_ORIGINAL_PICTURE_PATH);
        cuttedWidth = intent.getIntExtra(INTENT_CUT_WIDTH, 0);
        cuttedHeight = intent.getIntExtra(INTENT_CUT_HEIGHT, 0);
        if (cuttedWidth <= 0) {
            cuttedWidth = cuttedHeight;
        }
        if (cuttedHeight <= 0) {
            cuttedHeight = cuttedWidth;
        }

        if (StringUtils.isNotEmpty(originalPicturePath, true) == false || cuttedWidth <= 0) {
            Log.e(TAG, "onCreate  StringUtil.isNotEmpty(originalPicturePath, true)" +
                    " == false || cuttedWidth <= 0 >> finish(); return;");
            showShortToast("图片不存在，请先选择图片");
            finish();
            return;
        }
        initData();
        initView();
        initEvent();
    }


    @Override
    public void initView() {

    }

    public void startPhotoZoom(String path, int width, int height) {
        startPhotoZoom(Uri.fromFile(new File(path)), width, height);
    }


    public void startPhotoZoom(Uri fileUri, int width, int height) {

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fileUri, "image/*");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);

        if (Build.VERSION.SDK_INT >= 23) {
            File outputImage = new File(DataKeeper.imagePath, "output_image" + System.currentTimeMillis() + ".jpg");
            cuttedPicturePath = outputImage.getAbsolutePath();
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
        } else {
            intent.putExtra("crop", "true");// crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("return-data", true);
        }
        Log.i(TAG, "startPhotoZoom  fileUri = " + fileUri);
        toActivity(intent, REQUEST_CUT_PHOTO);
    }

    @Override
    public void initData() {
        startPhotoZoom(originalPicturePath, cuttedWidth, cuttedHeight);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CUT_PHOTO: //发送本地图片
                    if (data != null) {
                        if (Build.VERSION.SDK_INT < 23 || new File(cuttedPicturePath).exists() == false) {
                            Bundle bundle = data.getExtras();
                            if (bundle != null) {
                                Bitmap photo = bundle.getParcelable("data");
                                //photo.
                                if (photo != null) {
                                    //照片的路径
                                    setCuttedPicturePath();
                                    cuttedPicturePath = CommonUtils.savePhotoToSDCard(cuttedPicturePath, cuttedPictureName, "jpg", photo);
                                }
                            }
                        }
                        setResult(RESULT_OK, new Intent().putExtra(RESULT_PICTURE_PATH, cuttedPicturePath));
                    }
                    break;
                default:
                    break;
            }
        }

        finish();
    }

    private String setCuttedPicturePath() {
        //oringlePicturePath 不对
        cuttedPicturePath = intent.getStringExtra(INTENT_CUTTED_PICTURE_PATH);
        if (StringUtils.isFilePath(cuttedPicturePath) == false) {
            cuttedPicturePath = DataKeeper.fileRootPath + DataKeeper.imagePath;
        }
        cuttedPictureName = intent.getStringExtra(INTENT_CUTTED_PICTURE_NAME);
        if (StringUtils.isFilePath(cuttedPictureName) == false) {
            cuttedPictureName = "photo" + System.currentTimeMillis();
        }
        return cuttedPicturePath;
    }

    @Override
    public void finish() {
        exitAnim = enterAnim = R.anim.null_anim;
        super.finish();
    }

}