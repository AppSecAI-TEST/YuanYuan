package xyz.zimuju.common.webview;

import android.webkit.JsPromptResult;
import android.webkit.WebView;

/**
 * Created by cenxiaozhong on 2017/5/14.
 * source code  https://github.com/Justson/AgentWeb
 */

public class ChromeClientCallbackManager {


    public AgentWebCompatInterface mAgentWebCompatInterface;
    private ReceivedTitleCallback mReceivedTitleCallback;
    private GeoLocation mGeoLocation;

    public ReceivedTitleCallback getReceivedTitleCallback() {
        return mReceivedTitleCallback;
    }

    public ChromeClientCallbackManager setReceivedTitleCallback(ReceivedTitleCallback receivedTitleCallback) {
        mReceivedTitleCallback = receivedTitleCallback;
        return this;
    }

    public ChromeClientCallbackManager setGeoLocation(GeoLocation geoLocation) {
        this.mGeoLocation = geoLocation;
        return this;
    }

    public AgentWebCompatInterface getAgentWebCompatInterface() {
        return mAgentWebCompatInterface;
    }

    public void setAgentWebCompatInterface(AgentWebCompatInterface agentWebCompatInterface) {
        this.mAgentWebCompatInterface = agentWebCompatInterface;
        LogUtils.i("Info", "agent:" + agentWebCompatInterface);
    }

    public interface ReceivedTitleCallback {
        void onReceivedTitle(WebView view, String title);
    }

    interface AgentWebCompatInterface {
        boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);

        void onReceivedTitle(WebView view, String title);

        void onProgressChanged(WebView view, int newProgress);
    }

    public static class GeoLocation {
        /*1 表示定位开启, 0 表示关闭*/
        public int tag = 1;


    }
}
