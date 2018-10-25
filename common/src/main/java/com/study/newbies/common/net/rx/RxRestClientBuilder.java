package com.study.newbies.common.net.rx;

import android.content.Context;

import com.study.newbies.common.net.RestCreator;
import com.study.newbies.common.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * @author NewBies
 * @date 2018/9/10
 */

public class RxRestClientBuilder {
    private String url;
    private Map<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody body;
    private LoaderStyle LOADER_STYLE;
    private Context context;
    private File file;

    RxRestClientBuilder(){}

    public final RxRestClientBuilder url(String url){
        this.url = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params){
        PARAMS.putAll(params);
        return this;
    }


    public final RxRestClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }


    public final RxRestClientBuilder raw(String raw){
        //默认传的参数是json格式
        this.body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle style){
        this.LOADER_STYLE = style;
        this.context = context;
        return this;
    }

    public final RxRestClientBuilder loader(Context context){
        this.LOADER_STYLE = LoaderStyle.BallSpinFadeLoaderIndicator;
        this.context = context;
        return this;
    }

    public final RxRestClientBuilder file(File file){
        this.file = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath){
        this.file = new File(filePath);
        return this;
    }

    public Map<String, Object> checkParams(){
        return PARAMS;
    }

    public final RxRestClient build(){
        return new RxRestClient(url, PARAMS, body, LOADER_STYLE, file, context);
    }
}
