package xyz.zimuju.sample.surface.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import xyz.zimuju.guideview.Component;
import xyz.zimuju.sample.R;

public class SimpleComponent implements Component {

    @Override
    public View getView(LayoutInflater inflater) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.layer_frends, null);
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
        return Component.FIT_END;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 10;
    }
}
