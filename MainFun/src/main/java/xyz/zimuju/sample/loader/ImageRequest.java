package xyz.zimuju.sample.loader;

import android.widget.ImageView;

import xyz.zimuju.sample.R;

public class ImageRequest {

    private String url;
    private int placeHolder;
    private int error;
    private ImageView imageView;

    public ImageRequest(Builder builder) {
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.error = builder.error;
        this.imageView = builder.imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public static class Builder {
        private String url;
        private int placeHolder;
        private int error;
        private ImageView imageView;

        public Builder() {
            this.url = "";
            this.placeHolder = R.mipmap.default_load_img;
            this.imageView = null;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder error(int error) {
            this.error = error;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imageView = imgView;
            return this;
        }

        public ImageRequest create() {
            if (imageView == null) throw new IllegalArgumentException("the imageView required");
            if ("".equals(url)) throw new IllegalArgumentException("the url cannot be empty");
            return new ImageRequest(this);
        }
    }


}
