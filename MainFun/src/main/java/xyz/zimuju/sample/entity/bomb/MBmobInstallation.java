package xyz.zimuju.sample.entity.bomb;

import cn.bmob.v3.BmobInstallation;

/*
 * @description MBmobInstallation
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 23:18
 * @version 1.0.0
 */
public class MBmobInstallation extends BmobInstallation {
    private String uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
