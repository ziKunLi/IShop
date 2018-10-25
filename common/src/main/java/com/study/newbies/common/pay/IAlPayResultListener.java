package com.study.newbies.common.pay;

/**
 *
 * @author NewBies
 * @date 2018/10/6
 */

public interface IAlPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
