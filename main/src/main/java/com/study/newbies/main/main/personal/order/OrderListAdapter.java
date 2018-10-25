package com.study.newbies.main.main.personal.order;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.study.newbies.common.ui.recycle.MultipleFields;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.common.ui.recycle.MultipleRecyclerAdapter;
import com.study.newbies.common.ui.recycle.MultipleViewHolder;
import com.study.newbies.main.R;

import java.util.List;

/**
 *
 * @author NewBies
 * @date 2018/10/9
 */

public class OrderListAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST,R.layout.item_order_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.imageOrderList);
                final AppCompatTextView title = holder.getView(R.id.tvOrderListTitle);
                final AppCompatTextView price = holder.getView(R.id.tvOrderListPrice);
                final AppCompatTextView time = holder.getView(R.id.tvOrderListTime);
                final String titleValue = entity.getField(MultipleFields.TITLE);
                final String timeValue = entity.getField(OrderItemFields.TIME);
                final Double priceValue = entity.getField(OrderItemFields.PRICE);
                final String imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                title.setText(titleValue);
                time.setText("时间：" + timeValue);
                price.setText("价格：" + String.valueOf(priceValue));

                break;
            default:
                break;
        }
    }
}
