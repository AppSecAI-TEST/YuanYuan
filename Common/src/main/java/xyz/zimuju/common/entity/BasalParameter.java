package xyz.zimuju.common.entity;

/*
 * @description BasalParameter : 自定义的键值对
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/8/31-2:12
 * @version v1.0.0
 */
public class BasalParameter {
    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
