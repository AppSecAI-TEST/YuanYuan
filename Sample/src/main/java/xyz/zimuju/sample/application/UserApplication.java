package xyz.zimuju.sample.application;


import android.annotation.SuppressLint;
import android.content.Context;

import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.entity.User;
import xyz.zimuju.sample.manager.DataManager;

public class UserApplication {
    private static User currentUser = null;
    private static UserApplication userApplication;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private UserApplication(Context context) {
        UserApplication.context = context;
    }

    public static void initialize(Context context) {
        userApplication = new UserApplication(context);
    }

    public static UserApplication getInstance() {
        return userApplication;
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

    public void saveCurrentUser(User user) {
        if (user == null) {
            return;
        }
        if (user.getId() <= 0 && StringUtils.isNotEmpty(user.getUsername(), true) == false) {
            return;
        }

        DataManager.getInstance().saveCurrentUser(user);
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
