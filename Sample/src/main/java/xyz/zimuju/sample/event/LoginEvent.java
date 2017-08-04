
package xyz.zimuju.sample.event;

/*
 * @description LoginEvent : 登陆事件
 * @author Nathaniel-nathanwriting@126.com
 * @time 17-8-5-上午1:33
 * @version v1.0.0
 */
public class LoginEvent {

    int type = 1; //1:登录成功 0:登出
    public LoginEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
