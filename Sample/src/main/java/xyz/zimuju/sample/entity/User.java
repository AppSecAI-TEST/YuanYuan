package xyz.zimuju.sample.entity;

import xyz.zimuju.common.base.BaseModel;

public class User extends BaseModel {
    private int sex; // 性别
    private String head; // 头像
    private String name; // 名字
    private String phone; // 电话号码
    private String tag; // 标签
    private boolean starred; // 星标

    public User() {

    }

    public User(long id) {
        this();
        this.id = id;
    }

    public User(long id, String name) {
        this(id);
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean getStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    @Override
    protected boolean isCorrect() {
        return id > 0;
    }

}
