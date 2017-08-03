package xyz.zimuju.sample.surface.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import xyz.zimuju.guideview.Component;
import xyz.zimuju.sample.R;

public class MultiComponent implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout linearLayout = new LinearLayout(inflater.getContext());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(param);
        TextView textView = new TextView(inflater.getContext());
        textView.setText(R.string.nearby);
        //noinspection deprecation
        textView.setTextColor(inflater.getContext().getResources().getColor(R.color.color_white));
        textView.setTextSize(20);
        ImageView imageView = new ImageView(inflater.getContext());
        imageView.setImageResource(R.mipmap.icon_arrow_green);
        linearLayout.removeAllViews();
        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        return linearLayout;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_CENTER;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 20;
    }
}
