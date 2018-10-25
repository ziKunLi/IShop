package com.study.newbies.common.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
 * 步骤三，创建用于描述网络请求的接口
 * 1.用动态代理动态的将该接口的注解“翻译”成一个HTTP请求，最后在执行Http请求
 * 2.注：接口中的每个方法的所有参数都必须使用注解标注，否者会报错
 * 基于retrofit2的网络请求框架
 * @author NewBies
 * @date 2018/9/10
 */

public interface RestService {

    /**
     * Retrofit特意的将网络请求的URL分成了两部分设置
     * 这里的注解是第一步，第二部分是在创建Retrofit实例中的baseUrl中设置的
     * @param url
     * @param params
     * @return
     */
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * FormUrlEncoded表示请求体是一个Form表单
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

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
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * Multipart表示请求体是一个支持文件上传的Form表单
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}
