package xyz.zimuju.sample.surface.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.loader.ImageLoader;
import xyz.zimuju.sample.surface.gank.ViewPicActivity;

public class PictureDialog extends Dialog {

    private ImageView imageView;

    public PictureDialog(Context context) {
        super(context);
    }

    public PictureDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PictureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_dialog_picture);
        setCanceledOnTouchOutside(true);
        imageView = (ImageView) findViewById(R.id.iv_picture);
    }

    public void setPicture(final String url) {
        show();
        ImageLoader.displayImage(imageView, url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPicActivity.start(view.getContext(), view, url);
            }
        });
    }
}
