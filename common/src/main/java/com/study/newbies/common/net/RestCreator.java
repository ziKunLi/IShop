package com.study.newbies.common.net;

import com.study.newbies.common.app.ConfigType;
import com.study.newbies.common.app.ThisApp;
import com.study.newbies.common.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 负责基础的创建
 * 例如包含接口的Service，Retrofit实例
 * @author NewBies
 * @date 2018/9/10
 */
public class RestCreator {

    private static final class ParamsHolder{
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) ThisApp.getConfigurations(ConfigType.INTERCEPTOR.name());

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        /**
         * 添加拦截器
         * @return
         */
        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for(Interceptor interceptor : INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) ThisApp.getConfigurations().get(ConfigType.API_HOST.name());
        /**
         * 步骤四，创建Retrofit实例
         */
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                //加入转换器（设置数据解析器）
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                //步骤五，创建网络请求接口实例
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }
}
