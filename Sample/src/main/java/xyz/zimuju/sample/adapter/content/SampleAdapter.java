package xyz.zimuju.sample.adapter.content;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.zimuju.common.base.BaseView;
import xyz.zimuju.common.base.BaseViewAdapter;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.content.DemoFragmentActivity;

public class SampleAdapter extends BaseViewAdapter<Entry<String, String>, SampleAdapter.ItemView> {

    public SampleAdapter(Activity context) {
        super(context);
    }

    @Override
    public ItemView createView(int position, ViewGroup parent) {
        return new ItemView(context, resources);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


    class ItemView extends BaseView<Entry<String, String>> implements View.OnClickListener {

        ImageView ivDemoViewHead;
        TextView tvDemoViewName;
        TextView tvDemoViewNumber;

        ItemView(Activity context, Resources resources) {
            super(context, resources);
        }


        @SuppressLint("InflateParams")
        @Override
        public View createView(LayoutInflater inflater) {
            //TODO demo_view改为你所需要的layout文件，可以根据viewType使用不同layout
            convertView = inflater.inflate(R.layout.demo_view, null);
            ivDemoViewHead = findViewById(R.id.ivDemoViewHead, this);
            tvDemoViewName = findViewById(R.id.tvDemoViewName, this);
            tvDemoViewNumber = findViewById(R.id.tvDemoViewNumber);
            return convertView;
        }

        @Override
        public void bindView(Entry<String, String> data) {

            this.data = data;

            tvDemoViewName.setText(StringUtils.getTrimedString(data.getKey()));
            tvDemoViewNumber.setText(StringUtils.getNoBlankString(data.getValue()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivDemoViewHead:
                    toActivity(DemoFragmentActivity.createIntent(context, position)
                            .putExtra(DemoFragmentActivity.INTENT_TITLE, data.getKey()));
                    break;
                case R.id.tvDemoViewName:
                    data.setKey("New " + data.getKey());
                    bindView(data);
                    break;
                default:
                    break;
            }
        }
    }
}
