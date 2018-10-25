package com.study.newbies.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.study.newbies.common.app.ThisApp;

/**
 *
 * @author NewBies
 * @date 2018/8/25
 */
public class UiUtil {

    /**
     * dp-->px
     */
    public static int dp2Px(Context context, int dp) {
        // px/dip = density;
        // density = dpi/160
        // 320*480 density = 1 1px = 1dp
        // 1280*720 density = 2 2px = 1dp

        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);
        return px;
    }

    public static double px2dp(Context context, int px){
        float density = context.getResources().getDisplayMetrics().density;
        double dp = (px - 0.5)/density;
        return dp;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 得到屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        final Resources resources = ThisApp.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 得到屏幕的高
     * @return
     */
    public static int getScreenHeight(){
        final Resources resources = ThisApp.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     * @auther 李自坤
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
}
