package com.study.newbies.common.ui.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.study.newbies.common.ui.web.event.Event;
import com.study.newbies.common.ui.web.event.EventManager;
import com.study.newbies.common.ui.web.event.TestEvent;

/**
 * 这个接口是用来让webView与原生javaScrip进行交互的
 * @author NewBies
 * @date 2018/9/19
 */

public class AppWebInterface {

    private final WebDelegate DELEGATE;

    public AppWebInterface(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public static AppWebInterface create(WebDelegate delegate){
        return new AppWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance()
                .addEvent("test", new TestEvent())
                .createEvent(action);
        if(event != null){
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
