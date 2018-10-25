package com.study.newbies.common.net.rx;


import android.content.Context;

import com.study.newbies.common.net.HttpMethod;
import com.study.newbies.common.net.RestCreator;
import com.study.newbies.common.ui.AppLoader;
import com.study.newbies.common.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 *
 * @author NewBies
 * @date 2018/9/10
 */
public class RxRestClient {

    private final String URL;
    private final Map<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        LoaderStyle loaderStyle,
                        File file,
                        Context context) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
    }

    public static RxRestClientBuilder builder(){
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method){
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if(LOADER_STYLE != null){
            AppLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method){
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        if(BODY == null){
            return request(HttpMethod.POST);
        }
        else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("Params 必须为 null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put(){
        if(BODY == null){
            return request(HttpMethod.PUT);
        }
        else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("Params 必须为 null");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload(){
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download(){
        final Observable<ResponseBody> responseBodyObservable = RestCreator.getRxRestService().download(URL, PARAMS);
        return responseBodyObservable;
    }

}
