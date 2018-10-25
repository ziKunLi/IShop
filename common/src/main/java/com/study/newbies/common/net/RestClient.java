package com.study.newbies.common.net;


import android.content.Context;

import com.study.newbies.common.net.callback.IError;
import com.study.newbies.common.net.callback.IFailure;
import com.study.newbies.common.net.callback.IRequest;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.net.callback.RequestCallbacks;
import com.study.newbies.common.net.download.DownloadHandler;
import com.study.newbies.common.ui.AppLoader;
import com.study.newbies.common.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 *
 * @author NewBies
 * @date 2018/9/10
 */
public class RestClient {

    private final String URL;
    private final Map<String, Object> PARAMS = RestCreator.getParams();
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RestClient(String url,
                      Map<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      LoaderStyle loaderStyle,
                      File file,
                      Context context) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        //步骤五
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }

        if(LOADER_STYLE != null){
            AppLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method){
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if(call != null){
            //步骤六
            //发送网络请求（异步，同步的网络请求是call.execute()）
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(REQUEST, SUCCESS, ERROR, FAILURE, LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if(BODY == null){
            request(HttpMethod.POST);
        }
        else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("Params 必须为 null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if(BODY == null){
            request(HttpMethod.PUT);
        }
        else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("Params 必须为 null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL, DOWNLOAD_DIR, EXTENSION, NAME, REQUEST, SUCCESS, ERROR, FAILURE).handleDownload();
    }

}
