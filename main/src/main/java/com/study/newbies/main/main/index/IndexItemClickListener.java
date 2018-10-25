package com.study.newbies.main.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.main.detail.GoodsDetailDelegate;

/**
 *
 * @author NewBies
 * @date 2018/9/18
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final AppDelegate DELEGATE;

    private IndexItemClickListener(AppDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(AppDelegate delegate){
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GoodsDetailDelegate goodsDetailDelegate = GoodsDetailDelegate.create();
        DELEGATE.start(goodsDetailDelegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
