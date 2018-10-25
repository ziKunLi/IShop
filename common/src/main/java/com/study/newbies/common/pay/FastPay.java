package com.study.newbies.common.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.newbies.common.R;
import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;

import butterknife.OnClick;

/**
 *
 * @author NewBies
 * @date 2018/10/6
 */

public class FastPay implements View.OnClickListener {

    /**
     * 设置支付回调监听
     */
    private IAlPayResultListener iAlPayResultListener = null;
    private Activity activity = null;
    private AlertDialog dialog = null;
    private int orderId = -1;

    private FastPay(AppDelegate delegate){
        this.activity = delegate.getActivity();
        this.dialog = new AlertDialog.Builder(delegate.getContext()).create();
    }
    public static FastPay create(AppDelegate delegate){
        return new FastPay(delegate);
    }

    public void beginPayDialog(){
        dialog.show();
        final Window window = dialog.getWindow();
        if(window != null){
            window.setContentView(R.layout.delegate_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_pain_up_from_bottom);
            //设置透明
            window.setBackgroundDrawable(new ColorDrawable());
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);
            window.findViewById(R.id.aliPay).setOnClickListener(this);
            window.findViewById(R.id.weChatPay).setOnClickListener(this);
            window.findViewById(R.id.btnDialogPayCancel).setOnClickListener(this);
        }
    }

    public FastPay setPayResultListener(IAlPayResultListener listener){
        this.iAlPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId){
        this.orderId = orderId;
        return this;
    }

    public final void aliPay(int orderId){
        final String singUrl = "签名串";
        //获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySing = JSON.parseObject(response).getString("result");
                        //必须是异步的调用客户端支付接口
                        final PayAsyncTask task = new PayAsyncTask(activity, iAlPayResultListener);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySing);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.aliPay) {
            aliPay(orderId);
            dialog.cancel();
        }
        else if (id == R.id.weChatPay) {
            dialog.cancel();
        }
        else if (id == R.id.btnDialogPayCancel) {
            dialog.cancel();
        }
        else {
            dialog.cancel();
        }
    }
}
