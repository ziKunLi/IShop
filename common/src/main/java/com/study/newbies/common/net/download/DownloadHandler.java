package com.study.newbies.common.net.download;

import android.os.AsyncTask;

import com.study.newbies.common.net.RestCreator;
import com.study.newbies.common.net.callback.IError;
import com.study.newbies.common.net.callback.IFailure;
import com.study.newbies.common.net.callback.IRequest;
import com.study.newbies.common.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author NewBies
 * @date 2018/9/12
 */

public class DownloadHandler {

    private final String URL;
    private final Map<String, Object> PARAMS = RestCreator.getParams();
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;

    public DownloadHandler(String url,
                           String downloadDir,
                           String extension,
                           String name,
                           IRequest request,
                           ISuccess success,
                           IError error,
                           IFailure failure) {
        this.URL = url;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
    }

    public final void handleDownload(){
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody,NAME);

                            //这里一定要判断，否者文件下载不全
                            //下载完了，一定要判断这个task结束没有，如果他还没结束，我们就回调他的结束方法，那我们的文件可能会下载一部分就完了
                            //这是一件非常奇怪的事，所以我们这里做一个判断
                            if(task.isCancelled()){
                                if(REQUEST != null){
                                    //这个时候我们才能回调他
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if(ERROR != null){
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE != null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
