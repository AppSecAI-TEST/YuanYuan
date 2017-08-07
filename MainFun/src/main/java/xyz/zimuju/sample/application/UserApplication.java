package xyz.zimuju.sample.application;


import android.content.Context;

import xyz.zimuju.common.helper.InitializeHelper;
import xyz.zimuju.sample.entity.User;
import xyz.zimuju.sample.manager.DataManager;

public class UserApplication implements InitializeHelper {
    private static User currentUser = null;
    private static UserApplication userApplication;

    public static UserApplication getInstance() {
        if (userApplication == null) {
            userApplication = new UserApplication();
        }
        return userApplication;
    }

    @Override
    public void initialize(Context context) {
        DataManager.getInstance().initialize(context);
    }

    public long getCurrentUserId() {
        currentUser = getCurrentUser();
        return currentUser == null ? 0 : currentUser.getId();
    }

    public String getCurrentUserPhone() {
        currentUser = getCurrentUser();
        return currentUser == null ? null : currentUser.getPhone();
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = DataManager.getInstance().getCurrentUser();
        }
        return currentUser;
    }

    public void logout() {
        DataManager.getInstance().saveCurrentUser(null);
    }

    public boolean isCurrentUser(long userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return getCurrentUserId() > 0;
    }
}
