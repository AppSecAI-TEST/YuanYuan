package xyz.zimuju.common.transform;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/*
 * @description CropSquareTransformation : 剪裁成方形图
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-11-21-下午5:58
 * @version v1.0.0
 */
public class CropSquareTransform implements Transformation {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private int requireWidth;
    private int requireHeight;

    public CropSquareTransform(int requireWidth, int requireHeight) {
        this.requireWidth = requireWidth;
        this.requireHeight = requireHeight;
    }

    @Override
    public Bitmap transform(Bitmap source) {

        if (source.getWidth() == 0 || source.getHeight() == 0) {
            return source;
        }

        if (source.getWidth() > source.getHeight()) {
            if (source.getHeight() < requireWidth && source.getWidth() <= DEFAULT_WIDTH) {
                return source;
            } else {
                double aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                int width = (int) (requireHeight * aspectRatio);
                if (width > DEFAULT_WIDTH) {
                    width = DEFAULT_WIDTH;
                    requireWidth = (int) (width / aspectRatio);
                }
                if (width != 0 && requireHeight != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, width, requireHeight, false);
                    if (result != source) {
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }
        } else {
            if (source.getWidth() < requireWidth && source.getHeight() <= DEFAULT_HEIGHT) {
                return source;
            } else {
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int height = (int) (requireWidth * aspectRatio);
                if (height > DEFAULT_HEIGHT) {
                    height = DEFAULT_HEIGHT;
                    requireWidth = (int) (height / aspectRatio);
                }
                if (height != 0 && requireWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, requireWidth, height, false);
                    if (result != source) {
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }
        }
    }

    @Override
    public String key() {
        return "desiredWidth" + " desiredHeight";
    }
}
