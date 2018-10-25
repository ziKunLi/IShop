package com.study.newbies.main.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import butterknife.BindView;

/**
 * Created by 傅令杰
 */

public class AboutDelegate extends AppDelegate {

    @BindView(R2.id.tvInfo)
    AppCompatTextView info = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("about")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String infoString = JSON.parseObject(response).getString("data");
                        info.setText(infoString);
                    }
                })
                .build()
                .get();
    }
}
