package xyz.zimuju.sample.entity.bomb;

import com.avos.avoscloud.AVObject;

public class RxOperator extends AVObject {

    private String name;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
