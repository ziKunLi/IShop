package com.study.newbies.main.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.pay.FastPay;
import com.study.newbies.common.pay.IAlPayResultListener;
import com.study.newbies.common.ui.bottom.BottomItemDelegate;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author NewBies
 * @date 2018/10/5
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess, ICartItemListener, IAlPayResultListener{

    @BindView(R2.id.rvShopCart)
    RecyclerView recyclerView = null;
    @BindView(R2.id.iconShopCartSelectAll)
    IconTextView iconShopCartSelectAll;
    @BindView(R2.id.stubNoItem)
    ViewStubCompat stubNoItem;
    @BindView(R2.id.tvShopCartTotalPrice)
    AppCompatTextView tvShopCartTotalPrice;
    private ShopCartAdapter adapter = null;
    private int iconSelectedCount = 0;
    //购物车数量标记
    private int currentCount = 0;
    private int totalCount = 0;
    private double totalPrice = 0.00;

    @OnClick(R2.id.iconShopCartSelectAll)
    public void onClickSelectedAll(){
        final int tag = (int) iconShopCartSelectAll.getTag();
        if(tag == 0){
            iconShopCartSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            iconShopCartSelectAll.setTag(1);
            adapter.setIsSelectedAll(true);
            adapter.setAllPrice();
        }
        else {
            iconShopCartSelectAll.setTextColor(Color.GRAY);
            iconShopCartSelectAll.setTag(0);
            adapter.setIsSelectedAll(false);
            adapter.setTotalPrice(0.00);
        }
        //更新RecycleView的显示状态
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        tvShopCartTotalPrice.setText(String.valueOf(adapter.getTotalPrice()));
    }

    @OnClick(R2.id.tvTopShopCartRemoveSelect)
    public void onClickRemoveSelectedItem(){
        final List<MultipleItemEntity> data = adapter.getData();
        //找到要删除的数据
        List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for(MultipleItemEntity  entity : data){
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if(isSelected){
                deleteEntities.add(entity);
            }
        }
        for(MultipleItemEntity entity : deleteEntities){
            int removePosition;
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            if(entityPosition > currentCount - 1){
                removePosition = entityPosition - (totalCount - currentCount);
            }
            else {
                removePosition = entityPosition;
            }
            adapter.remove(removePosition);
            currentCount = adapter.getItemCount();
            //更新数据
            adapter.notifyItemRangeChanged(removePosition,  adapter.getItemCount());
        }
        adapter.initTotalPrice();
        tvShopCartTotalPrice.setText(String.valueOf(adapter.getTotalPrice()));
        checkItemCount();
    }

    @OnClick(R2.id.tvTopShopCartClear)
    void onClickClear(){
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        checkItemCount();
        tvShopCartTotalPrice.setText("0.00");
    }

    @OnClick(R2.id.tvShopCartPay)
    void onClickPay(){
        FastPay.create(this).beginPayDialog();

    }

    /**
     * 注意：这里和支付是没有关系的，这里仅仅是创建订单
     */
    private void createOrder(){
        final String orderUrl = "你的生成订单的API";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userId", "fa");
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "描述");
        orderParams.put("type", 1);
        orderParams.put("orderType",0);
        orderParams.put("isanonymous", true);
        orderParams.put("followeduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        //进行具体的支付过程
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();
    }

    private void checkItemCount(){
        final int count = adapter.getItemCount();
        if(count == 0){
            @SuppressLint("RestrictedApi") final View stubView = stubNoItem.inflate();
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.stubToBuy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("您该购物啦！");
                }
            });
            recyclerView.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        iconShopCartSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shopCart")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter().setJsonData(response).convert();
        adapter = new ShopCartAdapter(data);
        currentCount = adapter.getItemCount();
        totalCount = adapter.getItemCount();
        adapter.setICartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        totalPrice = adapter.getTotalPrice();
        tvShopCartTotalPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = adapter.getTotalPrice();
        tvShopCartTotalPrice.setText(String.valueOf(price));
    }

    @Override
    public void onItemSelectChanged(boolean isSelect) {
        final double price = adapter.getTotalPrice();
        tvShopCartTotalPrice.setText(String.valueOf(price));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
