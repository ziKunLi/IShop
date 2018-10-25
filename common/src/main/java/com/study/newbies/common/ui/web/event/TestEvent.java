package com.study.newbies.common.ui.web.event;

import android.webkit.WebView;
import android.widget.Toast;

/**
 *
 * @author NewBies
 * @date 2018/10/5
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), params, Toast.LENGTH_SHORT).show();
        if(getAction().equals("test")){
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();", null);
                }
            });
        }
        return null;
    }
}
