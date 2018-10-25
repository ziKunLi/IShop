package com.study.newbies.common.ui.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.ui.web.WebDelegate;

/**
 * @author NewBies
 * @date 2018/10/5
 */

public abstract class Event implements IEvent {

    private Context context = null;
    private String action = null;
    private WebDelegate delegate = null;
    private String url = null;
    private WebView webView = null;

    public WebView getWebView() {
        return delegate.getWebView();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public AppDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(WebDelegate delegate) {
        this.delegate = delegate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

