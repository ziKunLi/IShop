package com.study.newbies.common.callback;

import android.view.View;

/**
 *
 * @author NewBies
 * @date 2018/8/27
 */

public interface ItemClickListener {
    /**
     * 点击事件
     * @param view
     * @param positionX 第几个item
     * @param positionY item中第几个组件
     */
    void onItemClick(View view, int positionX, int positionY);

    /**
     * 长按事件
     * @param view
     * @param positionX 第几个item
     * @param positionY item中第几个组件
     */
    void onItemLongClick(View view, int positionX, int positionY);
}
