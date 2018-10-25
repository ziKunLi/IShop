package com.study.newbies.main.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.ui.recycle.MultipleFields;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.common.ui.recycle.MultipleRecyclerAdapter;
import com.study.newbies.common.ui.recycle.MultipleViewHolder;
import com.study.newbies.main.R;

import java.util.List;

/**
 * @author NewBies
 */
public class AddressAdapter extends MultipleRecyclerAdapter {

    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = entity.getField(MultipleFields.NAME);
                final String phone = entity.getField(AddressItemFields.PHONE);
                final String address = entity.getField(AddressItemFields.ADDRESS);
                final boolean isDefault = entity.getField(MultipleFields.TAG);
                final int id = entity.getField(MultipleFields.ID);

                final AppCompatTextView nameText = holder.getView(R.id.tvAddressName);
                final AppCompatTextView phoneText = holder.getView(R.id.tvAddressPhone);
                final AppCompatTextView addressText = holder.getView(R.id.tvAddressAddress);
                final AppCompatTextView deleteTextView = holder.getView(R.id.tvAddressDelete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(holder.getLayoutPosition());
                    }
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                break;
            default:
                break;
        }
    }
}
