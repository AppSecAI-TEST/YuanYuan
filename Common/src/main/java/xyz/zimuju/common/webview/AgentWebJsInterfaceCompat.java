package xyz.zimuju.common.webview;

import android.app.Activity;
import android.webkit.JavascriptInterface;


public class AgentWebJsInterfaceCompat implements AgentWebCompat, FileUploadPop<IFileUploadChooser> {

    private AgentWeb mAgentWeb;
    private Activity mActivity;
    private IFileUploadChooser mIFileUploadChooser;

    AgentWebJsInterfaceCompat(AgentWeb agentWeb, Activity activity) {
        this.mAgentWeb = agentWeb;
        this.mActivity = activity;
    }

    @JavascriptInterface
    public void uploadFile() {


        mIFileUploadChooser = new FileUpLoadChooserImpl(mActivity, new FileUpLoadChooserImpl.JsChannelCallback() {
            @Override
            public void call(String value) {

//                Log.i("Info","call:"+value);
//                StringBuilder sb=new StringBuilder().append("javascript:uploadFileResult ( \"").append(value).append("\" ) ");
                if (mAgentWeb != null)
//                    mAgentWeb.getJsEntraceAccess().callJs("javascript:uploadFileResult(" + value + ")");
                    mAgentWeb.getJsEntraceAccess().quickCallJs("uploadFileResult", value);
            }
        });
        mIFileUploadChooser.openFileChooser();

    }

    @Override
    public IFileUploadChooser pop() {
        IFileUploadChooser mIFileUploadChooser = this.mIFileUploadChooser;
        this.mIFileUploadChooser = null;
        return mIFileUploadChooser;
    }
}
