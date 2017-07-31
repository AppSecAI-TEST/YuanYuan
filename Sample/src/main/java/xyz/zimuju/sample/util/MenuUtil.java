package xyz.zimuju.sample.util;


import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.entity.Option;
import xyz.zimuju.sample.R;

public class MenuUtil {

    public static final String NAME_SEND_MESSAGE = "发信息";//信息记录显示在通话记录左边
    public static final String NAME_CALL = "呼叫";//信息记录显示在通话记录左边
    public static final String NAME_SEND = "发送";
    public static final String NAME_QRCODE = "二维码";
    public static final String NAME_ADD_TO = "添加至";
    public static final String NAME_EDIT = "编辑";
    public static final String NAME_EDIT_ALL = "编辑所有";
    public static final String NAME_DELETE = "删除";
    public static final String NAME_SEND_EMAIL = "发邮件";
    public static final String NAME_SETTING = "设置";

    public static final int INTENT_CODE_SEND_MESSAGE = 1;//信息记录显示在通话记录左边
    public static final int INTENT_CODE_CALL = 2;//信息记录显示在通话记录左边
    public static final int INTENT_CODE_SEND = 3;
    public static final int INTENT_CODE_QRCODE = 4;
    //	public static final int INTENT_CODE_SETTING = 5;
    public static final int INTENT_CODE_ADD_TO = 6;
    public static final int INTENT_CODE_EDIT = 7;
    public static final int INTENT_CODE_EDIT_ALL = 8;
    public static final int INTENT_CODE_DELETE = 9;
    public static final int INTENT_CODE_SEND_EMAIL = 10;
    public static final int CONTACT_LIST_FRAGMENT_MULTI = 1;
    public static final int USER = 1;

    public static Option FSB_SEND_MESSAGE = new Option(NAME_SEND_MESSAGE, R.mipmap.mail_light, INTENT_CODE_SEND_MESSAGE);
    public static Option FSB_CALL = new Option(NAME_CALL, R.mipmap.call_light, INTENT_CODE_CALL);
    public static Option FSB_SEND = new Option(NAME_SEND, R.mipmap.send_light, INTENT_CODE_SEND);
    public static Option FSB_QRCODE = new Option(NAME_QRCODE, R.mipmap.qrcode, INTENT_CODE_QRCODE);
    public static Option FSB_SETTING = new Option(NAME_SETTING, R.mipmap.setting_light);
    public static Option FSB_ADD_TO = new Option(NAME_ADD_TO, R.mipmap.add_light, INTENT_CODE_ADD_TO);
    public static Option FSB_EDIT = new Option(NAME_EDIT, R.mipmap.edit_light, INTENT_CODE_EDIT);
    public static Option FSB_EDIT_ALL = new Option(NAME_EDIT_ALL, R.mipmap.edit_light, INTENT_CODE_EDIT_ALL);
    public static Option FSB_DELETE = new Option(NAME_DELETE, R.mipmap.delete_light, INTENT_CODE_DELETE);
    public static Option FSB_SEND_EMAIL = new Option(NAME_SEND_EMAIL, R.mipmap.mail_light, INTENT_CODE_SEND_EMAIL);

    public static List<Option> getMenuList(int which) {
        List<Option> list = new ArrayList<Option>();
        switch (which) {
            case USER:
                list.add(FSB_SEND_MESSAGE);
                list.add(FSB_CALL);
                list.add(FSB_SEND_EMAIL);
                list.add(FSB_SEND);
                list.add(FSB_QRCODE);
                break;
            default:
                break;
        }

        return list;
    }
}
