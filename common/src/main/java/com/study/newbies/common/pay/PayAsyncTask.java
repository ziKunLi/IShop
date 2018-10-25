package com.study.newbies.common.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.study.newbies.common.ui.AppLoader;

/**
 *
 * @author NewBies
 * @date 2018/10/8
 */

public class PayAsyncTask extends AsyncTask<String, Void, String>{

    @SuppressLint("StaticFieldLeak")
    private final Activity ACTIVITY;
    private final IAlPayResultListener LISTENER;

    /**
     * 订单支付成功
     */
    private static final String ALI_PAY_STATUS_SUCCESS = "9000";
    /**
     * 订单处理中
     */
    private static final String ALI_PAY_STATUS_PAYING = "8000";
    /**
     * 订单支付失败
     */
    private static final String ALI_PAY_STATUS_FAIL = "4000";
    /**
     * 用户中途取消
     */
    private static final String ALI_PAY_STATUS_CANCEL = "6001";
    /**
     * 网络错误
     */
    private static final String ALI_PAY_STATUS_CONNECT_ERROR = "6002";


    public PayAsyncTask(Activity activity, IAlPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        final String aliPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(aliPaySign, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        AppLoader.stopLoading();
        final PayResult payResult = new PayResult(result);
        //支付宝返回此次支付结果及加签，建议对支付宝签名拿签约时支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        switch (resultStatus){
            case ALI_PAY_STATUS_SUCCESS:
                if(LISTENER != null){
                    LISTENER.onPaySuccess();
                }
                break;
            case ALI_PAY_STATUS_PAYING:
                if(LISTENER != null){
                    LISTENER.onPaying();
                }
                break;
            case ALI_PAY_STATUS_FAIL:
                if(LISTENER != null){
                    LISTENER.onPayFail();
                }
                break;
            case ALI_PAY_STATUS_CONNECT_ERROR:
                if(LISTENER != null){
                    LISTENER.onPayConnectError();
                }
                break;
            case ALI_PAY_STATUS_CANCEL:
                if(LISTENER != null){
                    LISTENER.onPayCancel();
                }
                break;
            default:
                break;
        }
    }
}
