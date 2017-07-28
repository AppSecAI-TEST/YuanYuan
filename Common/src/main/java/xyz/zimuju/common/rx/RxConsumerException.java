package xyz.zimuju.common.rx;

import io.reactivex.functions.Consumer;
import xyz.zimuju.common.entity.BasalError;
import xyz.zimuju.common.util.EmptyUtil;


public abstract class RxConsumerException implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {

        if (throwable instanceof RxResponseException) {
            onError(((RxResponseException) throwable).getRxError());
        } else {
            BasalError basicError = new BasalError();
            basicError.setMessage((throwable).getMessage());
            if (EmptyUtil.isNotEmpty(basicError.getMessage())) {
                onError(basicError);
            }
        }
    }

    public abstract void onError(BasalError rxError);
}
