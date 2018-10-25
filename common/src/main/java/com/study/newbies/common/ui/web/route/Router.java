package com.study.newbies.common.ui.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.ui.web.WebDelegate;
import com.study.newbies.common.ui.web.WebDelegateImpl;

import java.net.URL;

/**
 * 线程安全的惰性单利
 * @author NewBies
 * @date 2018/9/30
 */

public class Router {

    private Router(){}

    private static class Holder{
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance(){
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebDelegate delegate, String url){
        //如果是一个电话
        if(url.contains("tel:")){
            callPhone(delegate.getContext(), url);
            return true;
        }

        final AppDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.getProxyActivity().start(webDelegate);
        return true;
    }

    private void loadWebPage(WebView webView, String url){
        if(webView != null){
            webView.loadUrl(url);
        }
        else {
            throw new NullPointerException("webView is null!");
        }
    }

    private void loadLocalPage(WebView webView, String url){
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url){
        if(URLUtil.isNetworkUrl(url)||URLUtil.isAssetUrl(url)){
            loadWebPage(webView, url);
        }
        else{
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(WebDelegate delegate, String url){
        loadPage(delegate.getWebView(), url);
    }

    private void callPhone(Context context, String uri){
        //跳转到拨号界面
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        context.startActivity(intent);
        ContextCompat.startActivity(context, intent, null);
    }
}
