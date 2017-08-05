package xyz.zimuju.sample.entity;

import xyz.zimuju.common.base.BaseModel;

public class User extends BaseModel {
    private String username;
    private String password;
    private String portrait;
    private String signature;
    private int level;
    private int gander;
    private String phone;
    private String tag;
    private boolean starred;

    public User() {

    }
    public User(long id) {
        this();
        this.id = id;
    }

    public User(long id, String username) {
        this(id);
        this.username = username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStarred() {
        return starred;
    }

    public int getGander() {
        return gander;
    }

    public void setGander(int gander) {
        this.gander = gander;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
