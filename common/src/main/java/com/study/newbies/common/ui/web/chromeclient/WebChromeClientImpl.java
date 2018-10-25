package com.study.newbies.common.ui.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 *
 * @author NewBies
 * @date 2018/9/30
 */

public class WebChromeClientImpl extends WebChromeClient{

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
