package xyz.zimuju.sample.widget.custom;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.zimuju.common.base.BaseModel;
import xyz.zimuju.common.base.BaseView;
import xyz.zimuju.common.util.ImageLoaderUtil;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.common.widget.WebViewActivity;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.constant.UserConstants;
import xyz.zimuju.sample.entity.User;

public class UserView extends BaseView<User> implements View.OnClickListener {
    private static final String TAG = "UserView";
    public ImageView ivUserViewHead;
    public ImageView ivUserViewStar;
    public TextView tvUserViewSex;
    public TextView tvUserViewName;
    public TextView tvUserViewId;
    public TextView tvUserViewNumber;

    public UserView(Activity context, Resources resources) {
        super(context, resources);
    }

    @SuppressLint("InflateParams")
    @Override
    public View createView(LayoutInflater inflater) {
        convertView = inflater.inflate(R.layout.user_view, null);

        ivUserViewHead = findViewById(R.id.ivUserViewHead, this);
        ivUserViewStar = findViewById(R.id.ivUserViewStar, this);

        tvUserViewSex = findViewById(R.id.tvUserViewSex, this);

        tvUserViewName = findViewById(R.id.tvUserViewName);
        tvUserViewId = findViewById(R.id.tvUserViewId);
        tvUserViewNumber = findViewById(R.id.tvUserViewNumber);

        return convertView;
    }

    @Override
    public void bindView(User data) {
        if (data == null) {
            Log.e(TAG, "bindView data == null >> data = new User(); ");
            data = new User();
        }
        this.data = data;

        ImageLoaderUtil.loadImage(ivUserViewHead, data.getPortrait(), ImageLoaderUtil.TYPE_OVAL);
        ivUserViewStar.setImageResource(data.getStarred() ? R.mipmap.star_light : R.mipmap.star);

        tvUserViewSex.setBackgroundResource(data.getGander() == UserConstants.SEX_FEMALE
                ? R.drawable.circle_pink : R.drawable.circle_blue);
        tvUserViewSex.setText(data.getGander() == UserConstants.SEX_FEMALE ? "女" : "男");
        tvUserViewSex.setTextColor(getColor(data.getGander() == UserConstants.SEX_FEMALE ? R.color.pink : R.color.blue));

        tvUserViewName.setText(StringUtils.getTrimedString(data.getUsername()));
        tvUserViewId.setText("ID:" + data.getId());
        tvUserViewNumber.setText("Phone:" + StringUtils.getNoBlankString(data.getPhone()));
    }

    @Override
    public void onClick(View v) {
        if (BaseModel.isCorrect(data) == false) {
            return;
        }
        switch (v.getId()) {
            case R.id.ivUserViewHead:
                toActivity(WebViewActivity.createIntent(context, data.getUsername(), data.getPortrait()));
                break;
            default:
                switch (v.getId()) {
                    case R.id.ivUserViewStar:
                        data.setStarred(!data.getStarred());
                        break;
                    case R.id.tvUserViewSex:
                        data.setGander(data.getGander() == UserConstants.SEX_FEMALE ? UserConstants.SEX_MALE : UserConstants.SEX_FEMALE);
                        break;
                }
                bindView(data);
                break;
        }
    }
}