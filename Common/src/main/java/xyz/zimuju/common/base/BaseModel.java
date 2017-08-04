package xyz.zimuju.common.base;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    public long id;

    public static boolean isCorrect(BaseModel data) {
        return data != null && data.isCorrect();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected abstract boolean isCorrect(); //public 会导致 JSON.toJSONString 会添加 correct 字段

}
