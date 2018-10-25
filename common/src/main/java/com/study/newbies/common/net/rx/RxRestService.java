package com.study.newbies.common.net.rx;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 *
 * @author NewBies
 * @date 2018/9/13
 */

public interface RxRestService {

    /**
     * Retrofit特意的将网络请求的URL分成了两部分设置
     * 这里的注解是第一步，第二部分是在创建Retrofit实例中的baseUrl中设置的
     * @param url
     * @param params
     * @return
     */
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * FormUrlEncoded表示请求体是一个Form表单
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * Streaming注解可以使边下载边写入文件，如果不加的话，效果就是一次下载完毕后再写入文件，这回导致下载大文件时
     * 内存溢出
     * 该方法应在子线程执行，这是一个异步方法
     * @param url
     * @param params
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * Multipart表示请求体是一个支持文件上传的Form表单
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);

}
