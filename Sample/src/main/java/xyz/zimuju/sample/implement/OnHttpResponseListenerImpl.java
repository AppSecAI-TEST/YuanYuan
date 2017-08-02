package xyz.zimuju.sample.implement;

import org.json.JSONObject;

import xyz.zimuju.common.manager.OnHttpResponseListener;
import xyz.zimuju.common.util.StringUtils;


public class OnHttpResponseListenerImpl implements OnHttpResponseListener {
    private static final String TAG = "OnHttpResponseListenerImpl";
    OnHttpResponseListener listener;

    public OnHttpResponseListenerImpl(OnHttpResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        int resultCode = 0;
        String resultData = null;
        Exception exception = null;
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            resultCode = jsonObject.getInt("code");//TODO code改为接口文档给的key
            resultData = jsonObject.getString("data");//TODO data改为接口文档给的key
        } catch (Exception e1) {
            exception = e1;
        }

        if (listener == null) {
            listener = this;
        }
        if ((e == null && exception == null) || resultCode > 0 || StringUtils.isNotEmpty(resultData, true)) {
            listener.onHttpSuccess(requestCode, resultCode, resultData);
        } else {
            listener.onHttpError(requestCode, new Exception(TAG + ": e = " + e + "; \n exception = " + exception));
        }
    }

    @Override
    public void onHttpSuccess(int requestCode, int resultCode, String resultData) {

    }

    @Override
    public void onHttpError(int requestCode, Exception e) {

    }

}
