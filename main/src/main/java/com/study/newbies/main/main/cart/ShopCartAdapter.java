package com.study.newbies.main.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.ui.recycle.MultipleFields;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.common.ui.recycle.MultipleRecyclerAdapter;
import com.study.newbies.common.ui.recycle.MultipleViewHolder;
import com.study.newbies.main.R;

import java.util.List;

/**
 *
 * @author NewBies
 * @date 2018/10/6
 */
public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean isSelectedAll = false;
    private ICartItemListener iCartItemListener = null;
    private double totalPrice = 0.00;
    private List<MultipleItemEntity> data = null;

    public void setICartItemListener(ICartItemListener listener){
        this.iCartItemListener = listener;
    }

    private static final RequestOptions option = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        this.data = data;
        //初始化总价
        initTotalPrice();
        //添加购物车Item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void initTotalPrice(){
        totalPrice = 0;
        for(MultipleItemEntity entity : data){
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if(isSelected){
                final double price = entity.getField(ShopCartItemFields.PRICE);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double total = price * count;
                totalPrice += total;
            }
        }
    }

    public void setAllPrice(){
        totalPrice = 0;
        for(MultipleItemEntity entity : data){
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            totalPrice += total;
        }
    }

    public void setIsSelectedAll(boolean isSelectedAll){
        this.isSelectedAll = isSelectedAll;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                //再取出所有控件
                final AppCompatImageView imgThumb = holder.getView(R.id.imageItemShopCart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tvItemShopCartTitle);
                final AppCompatTextView tvDesc = holder.getView(R.id.tvItemShopCartDesc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tvItemShopCartPrice);
                final IconTextView iconMinus = holder.getView(R.id.iconItemMinus);
                final IconTextView iconPlus = holder.getView(R.id.iconItemPlus);
                final AppCompatTextView tvCount = holder.getView(R.id.tvItemShopCartCount);
                final IconTextView iconIsSelected = holder.getView(R.id.iconItemShopCart);
                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(option)
                        .into(imgThumb);

                //在左侧勾勾渲染之前改变状态
                entity.setField(ShopCartItemFields.IS_SELECTED, isSelectedAll);
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                //根据数据状态，显示左侧的勾
                if(isSelected){
                    iconIsSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                }
                else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        final double price = entity.getField(ShopCartItemFields.PRICE);
                        final int count = entity.getField(ShopCartItemFields.COUNT);
                        if(currentSelected){
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                            totalPrice -= price * count;
                            if(iCartItemListener != null){
                                iCartItemListener.onItemSelectChanged(false);
                            }
                        }
                        else {
                            iconIsSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);
                            totalPrice += price * count;
                            if(iCartItemListener != null){
                                iCartItemListener.onItemSelectChanged(true);
                            }
                        }
                    }
                });
                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if(Integer.parseInt(tvCount.getText().toString()) > 1){
                            RestClient.builder()
                                    .url("https://www.zhihu.com/")
                                    .loader(mContext)
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            entity.setField(ShopCartItemFields.COUNT, countNum);
                                            tvCount.setText(String.valueOf(countNum));
                                            if(iCartItemListener != null&&isSelected){
                                                totalPrice -= price;
                                                final double itemTotal = countNum * price;
                                                iCartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        RestClient.builder()
                                .url("https://www.zhihu.com/")
                                .loader(mContext)
                                .params("count", currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tvCount.getText().toString());
                                        countNum++;
                                        entity.setField(ShopCartItemFields.COUNT, countNum);
                                        tvCount.setText(String.valueOf(countNum));
                                        if(iCartItemListener != null&&isSelected){
                                            totalPrice += price;
                                            final double itemTotal = countNum * price;
                                            iCartItemListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .post();
                    }
                });
                break;
            default:
                break;
        }
    }
}
