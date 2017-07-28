package xyz.zimuju.common.entity;

import java.io.Serializable;

/*
 * @description LocalMedia 本地文件实体类
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/7/1-13:48
 * @version v1.0.0
 */
public class LocalMedia implements Serializable {
    private String path;
    private long duration;
    private long lastUpdateAt;

    public LocalMedia(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(long lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
