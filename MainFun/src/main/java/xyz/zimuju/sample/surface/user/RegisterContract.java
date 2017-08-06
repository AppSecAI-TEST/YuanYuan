package xyz.zimuju.sample.surface.user;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobSmsState;
import cn.bmob.v3.exception.BmobException;
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
        BmobSMS.requestSMSCode(context, phone, "猿媛", new RequestSMSCodeListener() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {
                    basalView.obtainResult(smsId);
                } else {
                    basalView.showToast(ex.toString());
                }
            }

        });
    }

    @Override
    public void register(String parameters) {

    }

    @Override
    public void querySmsState(Integer smsId) {
        BmobSMS.querySmsState(context, smsId, new QuerySMSStateListener() {
            @Override
            public void done(BmobSmsState bmobSmsState, BmobException e) {
                if (EmptyUtil.isEmpty(e)) {
                    basalView.showToast("验证码发送成功，请注意查收");
                } else {
                    basalView.showToast("发送失败，原因：" + e.getMessage());
                }
            }
        });
    }
}
