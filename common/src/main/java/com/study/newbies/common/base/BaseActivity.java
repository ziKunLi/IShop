package com.study.newbies.common.base;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.study.newbies.common.R;

/**
 *
 * @author NewBies
 * @date 2018/8/25
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 加载窗口
     */
    private PopupWindow loadingPop;
    /**
     * 屏幕是竖屏还是横屏
     **/
    private ScreenRoateTyte screenRoateTyte = ScreenRoateTyte.PORTRAIT;

    /**
     * 初始化参数
     */
    public abstract void initData();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 设置监听
     */
    public abstract void initListener();

    /**
     * 处理点击事件
     * @param v
     */
    @Override
    public abstract void onClick(View v);

    /**
     * 页面跳转
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this,clz));
    }

    /**
     * 携带数据的页面跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 是否设置沉浸状态栏
     */
    public void setStatusBar(boolean isSetStatusBar) {
        if(isSetStatusBar){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
    }

    /**
     * 是否允许屏幕旋转方向
     * @param screenRoateTyte
     */
    public void setScreenRoate(ScreenRoateTyte screenRoateTyte) {
        this.screenRoateTyte = screenRoateTyte;
    }

    /**
     * 该方法执行了，就说明现在这个活动已经在栈顶了
     * 与onPause配对，表示Activity已经创建完成，并且可以开始活动了，
     * 这个时候用户已经可以看到界面了，并且即将与用户交互（完成该周期之后便可以响应用户的交互事件了）。
     */
    @Override
    protected void onResume() {
        super.onResume();
        //将屏幕设置为竖屏
        if (screenRoateTyte.equals(ScreenRoateTyte.PORTRAIT)&&this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //将屏幕设置为横屏
        else if(screenRoateTyte.equals(ScreenRoateTyte.LANDSCAPE)&&this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    /**
     * 简化Toast
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     * @auther 李自坤
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 当在进行耗时操作，UI必须等待时，展示加载界面
     * @param contextView
     */
    public void showLoadingPop(View contextView){
        if(loadingPop == null){
            View popView = getLayoutInflater().inflate(R.layout.loading_pop,null);
            loadingPop = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,false);
        }
        loadingPop.showAtLocation(contextView, Gravity.CENTER,0,0);
        setBackgroundAlpha(0.6f);
        loadingPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 耗时操作结束，UI不需等待
     */
    public void dismissLoadingPop(){
        loadingPop.dismiss();
    }

    /**
     * 横屏或竖屏枚举
     */
    public enum ScreenRoateTyte{
        /**
         * 横屏
         */
        LANDSCAPE,
        /**
         * 竖屏
         */
        PORTRAIT
    }

}
