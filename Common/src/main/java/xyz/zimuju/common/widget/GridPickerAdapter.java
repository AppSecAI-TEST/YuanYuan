package xyz.zimuju.common.widget;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import xyz.zimuju.common.R;
import xyz.zimuju.common.base.BaseAdapter;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.common.util.StringUtils;


/**
 * 网格选择器adapter
 *
 * @author Lemon
 * @use new GridPickerAdapter(...); 具体参考.DemoAdapter
 */
public class GridPickerAdapter extends BaseAdapter<Entry<Integer, String>> {
    //	private static final String TAG = "GridPickerAdapter";

    public static final int TYPE_CONTNET_ENABLE = 0;
    public static final int TYPE_CONTNET_UNABLE = 1;
    public static final int TYPE_TITLE = 2;

    private OnItemSelectedListener onItemSelectedListener;
    private int currentPosition;//初始选中位置
    private int height;//item高度
    //getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private LayoutParams layoutParams;

    public GridPickerAdapter(Activity context, int currentPosition, int height) {
        super(context);
        this.currentPosition = currentPosition;
        this.height = height;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public String getCurrentItemName() {
        return StringUtils.getTrimedString(getItem(getCurrentPosition()).getValue());
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
        if (holder == null) {
            convertView = inflater.inflate(R.layout.grid_picker_item, parent, false);
            holder = new ViewHolder();

            holder.tv = (TextView) convertView.findViewById(R.id.tvGridPickerItem);

            convertView.setTag(holder);
        }

        final Entry<Integer, String> data = getItem(position);
        final int type = data.getKey();
        //		if (isEnabled == false && position == currentPosition) {
        //			currentPosition ++;
        //		}

        holder.tv.setText(StringUtils.getTrimedString(data.getValue()));
        holder.tv.setTextColor(resources.getColor(type == TYPE_CONTNET_ENABLE ? R.color.black : R.color.gray_2));
        holder.tv.setBackgroundResource(position == currentPosition
                ? R.drawable.round_green : R.drawable.null_drawable);

        convertView.setBackgroundResource(type == TYPE_TITLE ? R.color.alpha_1 : R.color.alpha_complete);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_CONTNET_ENABLE) {
                    currentPosition = position;
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(null, v, position, getItemId(position));
                    }
                    notifyDataSetChanged();
                }
            }
        });

        if (height > 0) {
            if (layoutParams == null || layoutParams.height != height) {
                layoutParams = convertView.getLayoutParams();
                layoutParams.height = height;
            }
            convertView.setLayoutParams(layoutParams);
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView tv;
    }
    //getView的常规写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
