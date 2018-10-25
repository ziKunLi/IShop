package com.study.newbies.main.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.ui.recycle.ItemType;
import com.study.newbies.common.ui.recycle.MultipleFields;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.common.ui.recycle.MultipleRecyclerAdapter;
import com.study.newbies.common.ui.recycle.MultipleViewHolder;
import com.study.newbies.main.R;
import com.study.newbies.main.main.sort.SortDelegate;
import com.study.newbies.main.main.sort.content.ContentDelegate;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 *
 * @author NewBies
 * @date 2018/9/19
 */

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    private final SortDelegate DELEGATE;
    /**
     * 记录上一个点击的分类位置
     */
    private int prePosition = 0;

    public SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        //添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tvVerticalItemName);
                final View line = holder.getView(R.id.viewLine);
                //获取到整个布局
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition = holder.getAdapterPosition();
                        if(prePosition != currentPosition){
                            //还原上一个
                            getData().get(prePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(prePosition);

                            //更新选中的Item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            prePosition = currentPosition;

                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if(!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_white));
                }
                else{
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tvVerticalItemName, text);
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        final AppDelegate contentDelegate = SupportHelper.findFragment(DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        if(contentDelegate != null){
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
        }
    }
}
