package com.study.newbies.main.main.personal.order;

import android.os.Bundle;
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
import com.study.newbies.main.main.personal.PersonalDelegate;

import java.util.List;

import butterknife.BindView;

/**
 * @author NewBies
 * @date 2018/10/9
 */

public class OrderListDelegate extends AppDelegate {

    private String type = null;

    @BindView(R2.id.rvOrderList)
    RecyclerView rvOrderList = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        type = args.getString(PersonalDelegate.ORDER_TYPE, "");
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("personalOrderList")
//                .params("type", type)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        rvOrderList.setLayoutManager(manager);
                        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        rvOrderList.setAdapter(adapter);
                        rvOrderList.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
                    }
                })
                .build()
                .get();
    }
}
