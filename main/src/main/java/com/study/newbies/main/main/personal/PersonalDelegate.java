package com.study.newbies.main.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.newbies.common.ui.bottom.BottomItemDelegate;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;
import com.study.newbies.main.main.personal.address.AddressDelegate;
import com.study.newbies.main.main.personal.list.ListAdapter;
import com.study.newbies.main.main.personal.list.ListBean;
import com.study.newbies.main.main.personal.list.ListItemType;
import com.study.newbies.main.main.personal.order.OrderListDelegate;
import com.study.newbies.main.main.personal.profile.UserProfileDelegate;
import com.study.newbies.main.main.personal.setting.SettingsDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author NewBies
 * @date 2018/10/8
 */

public class PersonalDelegate extends BottomItemDelegate{

    @BindView(R2.id.rvPersonalSetting)
    RecyclerView rvSettings;
    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle args = null;

    @OnClick(R2.id.tvAllOrder)
    public void onClickAllOrder(){
        args.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }

    @OnClick(R2.id.imgUserAvatar)
    void onClickAvatar() {
        getParentDelegate().start(new UserProfileDelegate());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = new Bundle();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    private void startOrderListByType(){
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(args);
        getParentDelegate().start(delegate);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();

        final ListBean user = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new SettingsDelegate())
                .setText("账号与安全")
                .build();

        final ListBean notify = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setDelegate(new SettingsDelegate())
                .setText("音效与通知")
                .build();

        final ListBean feedback = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setDelegate(new SettingsDelegate())
                .setText("问题反馈")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(5)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(user);
        data.add(notify);
        data.add(system);
        data.add(feedback);
        //设置RecycleView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        rvSettings.setAdapter(adapter);
        rvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
