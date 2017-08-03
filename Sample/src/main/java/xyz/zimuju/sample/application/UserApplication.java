package xyz.zimuju.sample.application;


import xyz.zimuju.common.basal.BaseApplication;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.entity.User;
import xyz.zimuju.sample.manager.DataManager;

public class UserApplication extends BaseApplication {

	private static User currentUser = null;
	private static UserApplication context;


	public static UserApplication getInstance() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

	}


	/**
	 * 获取当前用户id
	 *
	 * @return
	 */
	public long getCurrentUserId() {
		currentUser = getCurrentUser();
		return currentUser == null ? 0 : currentUser.getId();
	}

	/**
	 * 获取当前用户phone
	 *
	 * @return
	 */
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
		if (user.getId() <= 0 && StringUtils.isNotEmpty(user.getName(), true) == false) {
			return;
		}

		currentUser = user;
		DataManager.getInstance().saveCurrentUser(currentUser);
	}

	public void logout() {
		currentUser = null;
		DataManager.getInstance().saveCurrentUser(currentUser);
	}

	/**
	 * 判断是否为当前用户
	 *
	 * @param userId
	 * @return
	 */
	public boolean isCurrentUser(long userId) {
		return DataManager.getInstance().isCurrentUser(userId);
	}

	public boolean isLoggedIn() {
		return getCurrentUserId() > 0;
	}


}
