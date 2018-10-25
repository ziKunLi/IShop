package com.study.newbies.common.ui.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.study.newbies.common.ui.web.WebDelegate;
import com.study.newbies.common.ui.web.route.Router;
import com.study.newbies.common.util.LogUtil;

/**
 *
 * @author NewBies
 * @date 2018/9/30
 */

public class WebViewClientImpl extends WebViewClient{

    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate delegate){
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtil.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }
}
