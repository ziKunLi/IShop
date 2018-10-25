package com.study.newbies.ishop;

import android.app.Application;

import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.study.newbies.common.app.ThisApp;
import com.study.newbies.common.base.BaseApplication;
import com.study.newbies.common.net.interceptors.DebugInterceptor;

/**
 *
 * @author NewBies
 * @date 2018/9/9
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ThisApp.init(this)
                //配置字符图片（可以不要）
                .withIcon(new FontAwesomeModule())
                .withIcon(new IoniconsModule())
                .withIcon(new EntypoModule())
                .withIcon(new TypiconsModule())
                .withIcon(new MaterialModule())
                .withIcon(new MaterialCommunityModule())
                .withIcon(new MeteoconsModule())
                .withIcon(new WeathericonsModule())
                .withIcon(new SimpleLineIconsModule())
                //配置baseUrl
                .withApiHost("http://127.0.0.1/")
                //配置debug拦截器
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/index", R.raw.index))
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/sort", R.raw.sort))
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/sortContentList", R.raw.sort_content_list))
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/shopCart", R.raw.shop_cart))
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/personalOrderList", R.raw.personal_order_list))
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/about", R.raw.about))
                .withInterceptor(new DebugInterceptor("http://127.0.0.1/address", R.raw.address))
                .configure();
    }
}
