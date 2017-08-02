package xyz.zimuju.sample.adapter.content;


import android.app.Activity;
import android.view.ViewGroup;

import xyz.zimuju.common.base.BaseViewAdapter;
import xyz.zimuju.sample.entity.User;
import xyz.zimuju.sample.widget.custom.UserView;


public class UserAdapter extends BaseViewAdapter<User, UserView> {
    //	private static final String TAG = "UserAdapter";

    public UserAdapter(Activity context) {
        super(context);
    }

    @Override
    public UserView createView(int position, ViewGroup parent) {
        return new UserView(context, resources);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

}