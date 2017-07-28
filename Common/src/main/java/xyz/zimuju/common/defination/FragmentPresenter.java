package xyz.zimuju.common.defination;

import android.app.Activity;

public interface FragmentPresenter extends Presenter {

    /**
     * 该Fragment在Activity添加的所有Fragment中的位置
     */
    String ARGUMENT_POSITION = "ARGUMENT_POSITION";
    String ARGUMENT_ID = "ARGUMENT_ID";
    String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";

    int RESULT_OK = Activity.RESULT_OK;
    int RESULT_CANCELED = Activity.RESULT_CANCELED;
}