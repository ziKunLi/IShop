package com.study.newbies.common.ui.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.ui.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * webView简单封装
 * @author NewBies
 * @date 2018/9/19
 */

public abstract class WebDelegate extends AppDelegate implements IWebViewInitializer{

    private WebView webView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String url = null;
    private boolean isWebViewAvailable = false;
    private AppDelegate topDelegate;

    public WebDelegate() {
    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        url = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        //防止内存泄漏
        if(webView != null){
            webView.removeAllViews();
            webView.destroy();
        }
        else{
            final IWebViewInitializer initializer = setInitializer();
            if(initializer != null){
                //这里采用在代码中new出webView的形式而不采用在xml文件中直接写的形式是因为xml中的webView容易造成内存泄漏，而new出来的
                //可以在很大程度上避免他
                final WeakReference<WebView> webViewWeakReference = new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
                webView = webViewWeakReference.get();
                webView = initializer.initWebView(webView);
                webView.setWebViewClient(initializer.initWebViewClient());
                webView.setWebChromeClient(initializer.initWebChromeClient());
                webView.addJavascriptInterface(AppWebInterface.create(this), "App");
                isWebViewAvailable = true;
            }
            else {
                throw new NullPointerException("WebViewInitializer is null!");
            }
        }
    }

    public void setTopDelegate(AppDelegate delegate){
        topDelegate = delegate;
    }

    public AppDelegate getTopDelegate(){
        if(topDelegate == null){
            topDelegate = this;
        }
        return topDelegate;
    }

    public final WebView getWebView(){
        if(webView == null){
            throw new NullPointerException("webView is null");
        }
        return isWebViewAvailable ? webView : null;
    }

    public String getUrl(){
        if(url == null){
            throw new NullPointerException("url is null");
        }
        return url;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(webView != null){
            webView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(webView != null){
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(webView != null){
            webView.removeAllViews();
            webView.destroy();
        }
    }
}
