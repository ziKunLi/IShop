package com.study.newbies.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.IError;
import com.study.newbies.common.net.callback.IFailure;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.main.R;

/**
 *
 * @author NewBies
 * @date 2018/9/9
 */
public class ExampleDelegate extends AppDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient(){
        RestClient.builder()
                .url("index")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String message) {

                    }
                })
                .loader(getContext())
                .build()
                .get();
    }
}
