package xyz.zimuju.sample.entity.bomb;

import cn.bmob.v3.BmobUser;

/*
 * @description BombUser
 * @author Nathaniel
 * @time 2017/8/4 - 19:10
 * @version 1.0.0
 */
public class BombUser extends BmobUser {

    private Integer gander;
    private String nickname;
    private Integer age;

    public Integer getGander() {
        return gander;
    }

    public void setGander(Integer gander) {
        this.gander = gander;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
