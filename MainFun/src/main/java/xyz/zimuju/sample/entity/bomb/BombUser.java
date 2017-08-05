package xyz.zimuju.sample.entity.bomb;

import cn.bmob.v3.BmobUser;

/*
 * @description BombUser
 * @author Nathaniel
 * @time 2017/8/4 - 19:10
 * @version 1.0.0
 * ----------- BmobUser 原有的字段
 * username: 用户的用户名（必需）。
 * password: 用户的密码（必需）。
 * email: 用户的电子邮件地址（可选）。
 * emailVerified:邮箱认证状态（可选）。
 * mobilePhoneNumber：手机号码（可选）。
 * mobilePhoneNumberVerified：手机号码的认证状态（可选）。
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
