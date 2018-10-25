package com.study.newbies.common.net;

import android.content.Context;

import com.study.newbies.common.net.callback.IError;
import com.study.newbies.common.net.callback.IFailure;
import com.study.newbies.common.net.callback.IRequest;
import com.study.newbies.common.net.callback.ISuccess;
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

public class RestClientBuilder {
    private String url;
    private Map<String, Object> PARAMS = RestCreator.getParams();
    private String downloadDir;
    private String extension;
    private String name;
    private IRequest request;
    private ISuccess success;
    private IError error;
    private IFailure failure;
    private RequestBody body;
    private LoaderStyle LOADER_STYLE;
    private Context context;
    private File file;

    RestClientBuilder(){}

    public final RestClientBuilder url(String url){
        this.url = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params){
        PARAMS.putAll(params);
        return this;
    }


    public final RestClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }

    /**
     * 文件下载后存放在哪个目录
     * @param downloadDir
     * @return
     */
    public final RestClientBuilder dir(String downloadDir){
        this.downloadDir = downloadDir;
        return this;
    }

    /**
     * 文件的后缀名
     * @param extension
     * @return
     */
    public final RestClientBuilder extension(String extension){
        this.extension = extension;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.name = name;
        return this;
    }

    public final RestClientBuilder raw(String raw){
        //默认传的参数是json格式
        this.body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess success){
        this.success = success;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.failure = failure;
        return this;
    }

    public final RestClientBuilder error(IError error){
        this.error = error;
        return this;
    }

    public final RestClientBuilder request(IRequest request){
        this.request = request;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style){
        this.LOADER_STYLE = style;
        this.context = context;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.LOADER_STYLE = LoaderStyle.BallSpinFadeLoaderIndicator;
        this.context = context;
        return this;
    }

    public final RestClientBuilder file(File file){
        this.file = file;
        return this;
    }

    public final RestClientBuilder file(String filePath){
        this.file = new File(filePath);
        return this;
    }

    public Map<String, Object> checkParams(){
        return PARAMS;
    }

    public final RestClient build(){
        return new RestClient(url, PARAMS, downloadDir, extension, name, request, success, error, failure, body, LOADER_STYLE, file, context);
    }
}
