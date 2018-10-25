package com.study.newbies.common.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.study.newbies.common.R;
import com.study.newbies.common.util.UiUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2018/9/10
 */

public class AppLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, String type){

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);

        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = UiUtil.getScreenWidth();
        int deviceHeight = UiUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if(dialogWindow != null){
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = deviceWidth/LOADER_SIZE_SCALE;
            layoutParams.height = deviceHeight/LOADER_SIZE_SCALE;
            //这句话可以不要，这是使用偏移量来让其居中的
//            layoutParams.height = layoutParams.height + deviceHeight/LOADER_SIZE_SCALE;
            layoutParams.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context, DEFAULT_LOADER);
    }

    public static void showLoading(Context context, LoaderStyle loaderStyle){
        showLoading(context, loaderStyle.name());
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS){
            if(dialog != null){
                if(dialog.isShowing()){
                    //这两个的区别就是cancel回触发一些回调，而dismiss不会
                    dialog.cancel();
//                    dialog.dismiss();
                }
            }
        }
    }
}
