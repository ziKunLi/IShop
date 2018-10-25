package com.study.newbies.common.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.study.newbies.common.util.LogUtil;

import java.util.List;

/**
 *
 * @author NewBies
 * @date 2018/8/26
 */

public class BaseApplication extends Application{

    private final String lifecycle = "lifecycle";

    private List<Activity> activityList;

    @Override
    public void onCreate(){
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is created");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is started");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is resumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is paused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is stopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is destroyed");
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
