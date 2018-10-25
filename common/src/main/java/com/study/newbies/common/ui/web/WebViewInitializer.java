package com.study.newbies.common.ui.web;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 *
 * @author NewBies
 * @date 2018/9/30
 */

public class WebViewInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView createWebView(WebView webView){
        //设置可以调试
        WebView.setWebContentsDebuggingEnabled(true);
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(true);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(true);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化webSettings
        final WebSettings settings = webView.getSettings();
        final String ua = settings.getUserAgentString();
        settings.setJavaScriptEnabled(true);
        //这样就可以判断是不是我们的APP在打开这个网页
        settings.setUserAgentString(ua + "IShop");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webView;
    }
}
