package xyz.zimuju.sample.adapter.guide;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.zimuju.guideview.Guide;
import xyz.zimuju.guideview.GuideBuilder;
import xyz.zimuju.guideview.OnVisibilityChangedListener;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.component.MultiComponent;

public class GuideAdapter extends BaseAdapter {
    private Context context;
    private int showTimes = 0;
    private List<String> stringList;

    public GuideAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public int getCount() {
        return stringList == null ? 0 : stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_guide, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_list_guide_text_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(stringList.get(position));
        if (position == 0 && showTimes == 0) {
            final View finalView = convertView;
            convertView.post(new Runnable() {
                @Override
                public void run() {
                    showGuideView(finalView);
                }
            });
        }
        return convertView;
    }

    public void showGuideView(View targetView) {
        showTimes++;
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(targetView)
                .setFullingViewId(R.id.guide_container_layout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder.addComponent(new MultiComponent());
        Guide guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show((Activity) context);
    }

    class ViewHolder {
        TextView textView;
    }
}