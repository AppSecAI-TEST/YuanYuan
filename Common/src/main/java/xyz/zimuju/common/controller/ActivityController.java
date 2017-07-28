package xyz.zimuju.common.controller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
 * @description ActivityController
 *      控制Activity的状态
 * @author Nathaniel - nathanwriting@126.com
 * @time 2017/06/08 - 22:56
 * @version 1.0.0
 */
public class ActivityController {
    private static ActivityController activityController;
    private List<Activity> activityList;

    private ActivityController() {
        activityList = new ArrayList<>();
    }

    public static ActivityController initController() {
        if (activityController == null) {
            activityController = new ActivityController();
        }
        return activityController;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null && activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public void finishAll() {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }
}
