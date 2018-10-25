package com.study.newbies.common.net.interceptors;

import android.support.annotation.RawRes;

import com.study.newbies.common.util.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * @author NewBies
 * @date 2018/9/12
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int debugRawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = debugRawId;
    }

    private Response getResponse(Chain chain, String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    /**
     * RawRes 该注解告诉我们必须使用raw文件夹下的数据
     * @param chain
     * @param rawId
     * @return
     */
    private Response debugResponse(Chain chain, @RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if(url.equals(DEBUG_URL)){
            return debugResponse(chain, DEBUG_RAW_ID);
        }

        return chain.proceed(chain.request());
    }
}
