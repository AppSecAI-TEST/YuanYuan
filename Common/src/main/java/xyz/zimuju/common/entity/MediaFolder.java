package xyz.zimuju.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * @description LocalMediaFolder 本地文件夹实体类
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/7/1-13:47
 * @version v1.0.0
 */
public class MediaFolder implements Serializable {
    private String name;
    private String path;
    private String firstImagePath;
    private int imageNum;
    private List<LocalMedia> images = new ArrayList<LocalMedia>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public List<LocalMedia> getImages() {
        return images;
    }

    public void setImages(List<LocalMedia> images) {
        this.images = images;
    }
}
