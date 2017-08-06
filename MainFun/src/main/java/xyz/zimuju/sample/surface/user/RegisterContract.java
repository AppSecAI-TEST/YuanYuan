package xyz.zimuju.sample.surface.user;

import android.util.Log;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobSmsState;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import xyz.zimuju.common.rx.RxContract;
import xyz.zimuju.common.util.EmptyUtil;

/*
 * @description RegisterContract
 * @author Nathaniel
 * @email nathanwriting@126.com
 * @time 2017/8/6 - 12:05
 * @version 1.0.0
 */
public class RegisterContract extends RxContract<RegisterView> implements RegisterPresenter {

    private Integer smsId;

    @Override
    public void obtain(String phone) {
        BmobSMS.requestSMSCode(phone, "猿媛", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    basalView.obtainResult(smsId);
                } else {
                    basalView.showToast(e.toString());
                }
            }
        });
    }

    @Override
    public void register(String... parameters) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(parameters[0]);
        bmobUser.setPassword(parameters[1]);
        bmobUser.setMobilePhoneNumber(parameters[2]);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    basalView.showToast("注册成功");
                    basalView.registerResult();
                } else {
                    basalView.showToast("注册失败：原因：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void querySmsState(Integer smsId) {
        /*
         * 注：SmsState包含两种状态：
         * 1、smsState（短信状态） :SUCCESS（发送成功）、FAIL（发送失败）、SENDING(发送中)。
         * 2、verifyState（验证状态）:true(已验证)、false(未验证)。
         */
        BmobSMS.querySmsState(smsId, new QueryListener<BmobSmsState>() {
            @Override
            public void done(BmobSmsState bmobSmsState, BmobException e) {
                if (EmptyUtil.isEmpty(e)) {
                    switch (bmobSmsState.getSmsState()) {
                        case "SUCCESS":
                            basalView.showToast("验证码发送成功，请注意查收");
                            break;

                        case "FAIL":
                            basalView.showToast("由于未知原因验证码发送失败");
                            break;

                        case "SENDING":
                            basalView.showToast("验证码正在火速向您奔来");
                            break;
                    }

                    switch (bmobSmsState.getVerifyState()) {
                        case "true":
                            basalView.showToast("已通过验证");
                            break;

                        case "false":
                            basalView.showToast("未通过验证");
                            break;
                    }
                } else {
                    Log.d("Nathaniel", "发送失败，原因：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void verifyCode(String phone, String code) {
        BmobSMS.verifySmsCode("11位手机号码", "验证码", new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if (ex == null) {
                    basalView.verifyCodeResult(true);
                } else {
                    basalView.showToast("验证码失效");
                    Log.d("Nathaniel", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                }
            }
        });
    }
}
