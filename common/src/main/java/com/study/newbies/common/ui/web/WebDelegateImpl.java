package com.study.newbies.common.ui.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.study.newbies.common.ui.web.chromeclient.WebChromeClientImpl;
import com.study.newbies.common.ui.web.client.WebViewClientImpl;
import com.study.newbies.common.ui.web.route.RouteKeys;
import com.study.newbies.common.ui.web.route.Router;

/**
 *
 * @author NewBies
 * @date 2018/9/24
 */

public class WebDelegateImpl extends WebDelegate{

    public static WebDelegateImpl create(String url){
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if(getUrl() != null){
            //用原生的方式模拟web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
