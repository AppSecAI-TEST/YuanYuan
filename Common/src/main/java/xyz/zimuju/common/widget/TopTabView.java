/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package xyz.zimuju.common.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import xyz.zimuju.common.R;
import xyz.zimuju.common.base.BaseView;
import xyz.zimuju.common.util.StringUtils;

/**
 * 自定义顶栏切换标签View
 *
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use <br> TopTabView modleView = new TopTabView(context, inflater);
 * <br> adapter中使用:[具体见.BaseTabActivity]
 * <br> convertView = modleView.getView();
 * <br> 或  其它类中使用
 * <br> containerView.addView(modleView.getConvertView());
 * <br> 然后
 * <br> modleView.bindView(object);
 * <br> modleView.setOnTabSelectedListener(onItemSelectedListener);
 */
public class TopTabView extends BaseView<String[]> {
    private static final String TAG = "TopTabView";
    public TextView tvTopTabViewTabFirst;
    public TextView tvTopTabViewTabLast;
    public LinearLayout llTopTabViewContainer;
    private OnTabSelectedListener onTabSelectedListener;
    private int minWidth;
    private int currentPosition = 0;
    private LayoutInflater inflater;
    private String[] names;//传进来的数据
    private int lastPosition = 1;
    private int width;
    private int maxWidth;
    private TextView[] tvTabs;

    public TopTabView(Activity context, Resources resources, int minWidth) {
        this(context, resources);
        this.minWidth = minWidth;
    }


    public TopTabView(Activity context, Resources resources) {
        super(context, resources);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    @SuppressLint("InflateParams")
    @Override
    public View createView(LayoutInflater inflater) {
        this.inflater = inflater;
        convertView = inflater.inflate(R.layout.top_tab_view, null);

        tvTopTabViewTabFirst = findViewById(R.id.tvTopTabViewTabFirst);
        tvTopTabViewTabLast = findViewById(R.id.tvTopTabViewTabLast);

        llTopTabViewContainer = findViewById(R.id.llTopTabViewContainer);

        return convertView;
    }

    @Override
    public String[] getData() {
        return names;
    }

    public int getCount() {
        return names.length;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public TextView getCurrentTab() {
        return tvTabs[getCurrentPosition()];
    }

    /**
     * @param nameList
     */
    public void bindView(List<String> nameList) {
        if (nameList != null) {
            for (int i = 0; i < nameList.size(); i++) {
                names[i] = nameList.get(i);
            }
        }
        bindView(names);
    }

    @Override
    public void bindView(String[] names) {
        if (names == null || names.length < 2) {
            Log.e(TAG, "setInerView names == null || names.length < 2 >> return; ");
            return;
        }
        this.names = names;
        this.lastPosition = getCount() - 1;

        tvTabs = new TextView[getCount()];

        tvTabs[0] = tvTopTabViewTabFirst;
        tvTabs[lastPosition] = tvTopTabViewTabLast;

        llTopTabViewContainer.removeAllViews();
        for (int i = 0; i < tvTabs.length; i++) {
            final int position = i;

            if (tvTabs[position] == null) {
                //viewgroup.addView(child)中的child相同，否则会崩溃
                tvTabs[position] = (TextView) inflater.inflate(R.layout.top_tab_tv_center, llTopTabViewContainer, false);
                llTopTabViewContainer.addView(tvTabs[position]);

                View divider = inflater.inflate(R.layout.divider_vertical_1dp, llTopTabViewContainer, false);
                divider.setBackgroundColor(getColor(R.color.white));
                llTopTabViewContainer.addView(divider);
            }
            tvTabs[position].setText(StringUtils.getTrimedString(names[position]));
            tvTabs[position].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    select(position);
                }
            });

            width = tvTabs[position].getWidth();
            if (minWidth < width) {
                minWidth = width;
            }
        }

        //防止超出
        maxWidth = llTopTabViewContainer.getMeasuredWidth() / tvTabs.length;
        if (minWidth > maxWidth) {
            minWidth = maxWidth;
        }
        for (int i = 0; i < tvTabs.length; i++) {
            //保持一致
            tvTabs[i].setMinWidth(minWidth);

            //防止超出
            if (tvTabs[i].getWidth() > maxWidth) {
                tvTabs[i].setWidth(maxWidth);
            }
        }


        select(currentPosition);
    }

    /**
     * 选择tab
     *
     * @param position
     * @param tabType
     */
    public void select(int position) {
        Log.i(TAG, "select  position = " + position);
        if (position < 0 || position >= getCount()) {
            Log.e(TAG, "select  position < 0 || position >= getCount() >> return;");
            return;
        }

        for (int i = 0; i < tvTabs.length; i++) {
            tvTabs[i].setSelected(i == position);
        }

        if (onTabSelectedListener != null) {
            onTabSelectedListener.onTabSelected(tvTabs[position]
                    , position, tvTabs[position].getId());
        }

        this.currentPosition = position;
    }

    /**
     */
    public interface OnTabSelectedListener {
        //		void beforeTabSelected(TextView tvTab, int position, int id);
        void onTabSelected(TextView tvTab, int position, int id);
    }

}
