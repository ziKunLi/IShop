package com.study.newbies.main.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import java.util.List;

import butterknife.BindView;

/**
 * @author NewBies
 */
public class AddressDelegate extends AppDelegate implements ISuccess {

    @BindView(R2.id.rvAddress)
    RecyclerView recyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("address")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter addressAdapter = new AddressAdapter(data);
        recyclerView.setAdapter(addressAdapter);
    }
}
