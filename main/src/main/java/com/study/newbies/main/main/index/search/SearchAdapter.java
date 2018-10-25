package com.study.newbies.main.main.index.search;

import android.support.v7.widget.AppCompatTextView;


import com.study.newbies.common.ui.recycle.MultipleFields;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.common.ui.recycle.MultipleRecyclerAdapter;
import com.study.newbies.common.ui.recycle.MultipleViewHolder;
import com.study.newbies.main.R;

import java.util.List;

/**
 * Created by 傅令杰
 */

public class SearchAdapter extends MultipleRecyclerAdapter {

    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
                final String history = entity.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
