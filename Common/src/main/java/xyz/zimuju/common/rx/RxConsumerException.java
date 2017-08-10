package xyz.zimuju.common.rx;


import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.functions.Consumer;
import xyz.zimuju.common.util.EmptyUtil;

/**
 * Created by 8000m on 2017/5/2.
 */

public abstract class RxConsumerException implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {


        if (throwable instanceof RxResponseException) {
            RxError rxError = ((RxResponseException) throwable).getRxError();
            onError(rxError);
//            if (rxError.getCode() == 1020) {
//                Intent intent = new Intent(CommonConstants.KEY_LOGIN_LOUT);
//                CommonApplication.getInstance().getContext().sendBroadcast(intent);
//            }
        } else if (throwable instanceof HttpException) {
            //服务器那边需要客户端显示网络错误，用户出了问题，可以帮助定位问题
            RxError rxError = new RxError();
            String httpStr = throwable.getMessage();
            rxError.setMsg(httpStr);
            onError(rxError);
        } else {
//            if (!BuildConfig.isDebug) {
//                return;
//            }

            RxError rxError = new RxError();
            rxError.setMsg((throwable).getMessage());
            if (EmptyUtil.isNotEmpty(rxError.getMsg())) {
                onError(rxError);
            }
        }
    }

    public abstract void onError(RxError rxError);
}
