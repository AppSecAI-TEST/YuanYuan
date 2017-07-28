package xyz.zimuju.common.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.R;
import xyz.zimuju.common.base.BaseView;
import xyz.zimuju.common.entity.Option;
import xyz.zimuju.common.util.CommonUtils;
import xyz.zimuju.common.util.StringUtils;

public class BottomMenuView extends BaseView<List<Option>> {
    private static final String TAG = "BottomMenuView";
    public LinearLayout llBottomMenuViewMainItemContainer;
    private OnBottomMenuItemClickListener onBottomMenuItemClickListener;
    private int toBottomMenuWindowRequestCode;
    private LayoutInflater inflater;
    private List<Option> list; // 传进来的数据
    private ArrayList<String> moreMenuNameList;
    private ArrayList<Integer> moreMenuIntentCodeList;

    public BottomMenuView(Activity context, Resources resources, int toBottomMenuWindowRequestCode) {
        super(context, resources);
        this.toBottomMenuWindowRequestCode = toBottomMenuWindowRequestCode;
    }

    public void setOnMenuItemClickListener(OnBottomMenuItemClickListener l) {
        onBottomMenuItemClickListener = l;
    }

    /**
     * 获取View
     *
     * @return
     */
    @SuppressLint("InflateParams")
    @Override
    public View createView(LayoutInflater inflater) {
        this.inflater = inflater;
        convertView = inflater.inflate(R.layout.bottom_menu_view, null);

        llBottomMenuViewMainItemContainer = (LinearLayout)
                convertView.findViewById(R.id.llBottomMenuViewMainItemContainer);

        return convertView;
    }

    @Override
    public List<Option> getData() {
        return list;
    }

    @Override
    public void bindView(final List<Option> menuList) {
        if (menuList == null || menuList.isEmpty()) {
            Log.e(TAG, "bindView  menuList == null || menuList.isEmpty() >> return;");
            return;
        }
        this.list = menuList;

        llBottomMenuViewMainItemContainer.removeAllViews();
        final int mainItemCount = list.size() > 4 ? 3 : list.size();//不包括 更多 按钮
        Option fsb;
        for (int i = 0; i < mainItemCount; i++) {
            fsb = list.get(i);
            if (fsb.getImageRes() > 0) {
                addItem(false, i, fsb);
            } else {
                break;
            }
        }

        //菜单区域外的背景及监听不好做，还是点击更多弹出BottomMenuWindow好
        if (list.size() > 4) {
            addItem(true, -1, null);

            //弹出底部菜单
            moreMenuNameList = new ArrayList<String>();
            moreMenuIntentCodeList = new ArrayList<Integer>();
            Option moreFsb;
            for (int i = 3; i < list.size(); i++) {
                moreFsb = list.get(i);
                if (moreFsb != null) {
                    moreMenuNameList.add(moreFsb.getName());
                    moreMenuIntentCodeList.add(moreFsb.getIntentCode());
                }
            }
        }
    }

    /**
     * 添加带图标的主要按钮
     *
     * @param position
     * @param fsb
     */
    @SuppressLint("InflateParams")
    private void addItem(final boolean isMoreButton, final int position, final Option fsb) {
        if (isMoreButton == false) {
            if (position < 0 || fsb == null || StringUtils.isNotEmpty(fsb.getName(), true) == false
                    || fsb.getImageRes() <= 0) {
                Log.e(TAG, "addItem isMoreButton == false >> position < 0 || fsb == null " +
                        "|| StringUtil.isNotEmpty(fsb.getName(), true) == false " +
                        "|| fsb.getImageRes() <= 0 >> return;");
                return;
            }
        }

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.icon_name_item, null);
        ImageView iv = (ImageView) ll.findViewById(R.id.ivIconNameIcon);
        TextView tv = (TextView) ll.findViewById(R.id.tvIconNameName);
        try {
            iv.setImageResource(isMoreButton ? R.mipmap.up2_light : fsb.getImageRes());
        } catch (Exception e) {
            Log.e(TAG, "addItem try {" +
                    " iv.setImageResource(fsb.getImageRes()); " + e.getMessage() + ">> return;");
            return;
        }
        tv.setText(isMoreButton ? "更多" : "" + fsb.getName());

        ll.setPadding(
                (int) getDimension(R.dimen.item_left_tv_padding),
                0,
                (int) getDimension(R.dimen.item_right_img_padding_right),
                0);
        ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoreButton) {
                    CommonUtils.toActivity(context, BottomMenuWindow.createIntent(context
                            , moreMenuNameList, moreMenuIntentCodeList)
                            .putExtra(BottomMenuWindow.INTENT_TITLE, "更多"), toBottomMenuWindowRequestCode, false);
                } else {
                    onBottomMenuItemClickListener.onBottomMenuItemClick(fsb.getIntentCode());
                }
            }
        });

        llBottomMenuViewMainItemContainer.addView(ll, position);
    }


    public interface OnBottomMenuItemClickListener {
        void onBottomMenuItemClick(int intentCode);
    }

}
