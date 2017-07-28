package xyz.zimuju.sample.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.zimuju.common.base.BaseAdapter;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.R;

public class DemoAdapter extends BaseAdapter<Entry<String, String>> {

    public DemoAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
        if (holder == null) {
            //TODO demo_item改为你所需要的layout文件
            convertView = inflater.inflate(R.layout.demo_item, parent, false);
            holder = new ViewHolder();

            holder.ivDemoItemHead = (ImageView) convertView.findViewById(R.id.ivDemoItemHead);
            holder.tvDemoItemName = (TextView) convertView.findViewById(R.id.tvDemoItemName);
            holder.tvDemoItemNumber = (TextView) convertView.findViewById(R.id.tvDemoItemNumber);

            convertView.setTag(holder);
        }

        final Entry<String, String> data = getItem(position);

        holder.tvDemoItemName.setText(StringUtils.getTrimedString(data.getValue()));
        holder.tvDemoItemNumber.setText(StringUtils.getNoBlankString(data.getKey()));

        return super.getView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


    static class ViewHolder {
        public ImageView ivDemoItemHead;
        public TextView tvDemoItemName;
        public TextView tvDemoItemNumber;
    }


}
