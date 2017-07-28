package xyz.zimuju.sample.gif.sources;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.MultiCallback;
import xyz.zimuju.sample.R;

class GifSourceItemHolder extends RecyclerView.ViewHolder {
    final GifImageView gifImageViewOriginal;
    final GifImageView gifImageViewSampled;
    final TextView descriptionTextView;
    final MultiCallback multiCallback;

    GifSourceItemHolder(View itemView) {
        super(itemView);
        descriptionTextView = (TextView) itemView.findViewById(R.id.desc_tv);
        gifImageViewOriginal = (GifImageView) itemView.findViewById(R.id.image_original);
        gifImageViewSampled = (GifImageView) itemView.findViewById(R.id.image_subsampled);
        multiCallback= new MultiCallback(true);
    }
}
