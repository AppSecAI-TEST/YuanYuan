package xyz.zimuju.common.webview;

import android.webkit.WebSettings;
import android.webkit.WebView;


public interface MWebSettings<T extends WebSettings> {

    MWebSettings toSetting(WebView webView);

    T getWebSettings();
}
