package com.study.newbies.main.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.study.newbies.common.ui.bottom.BottomItemDelegate;
import com.study.newbies.main.R;
import com.study.newbies.main.main.sort.content.ContentDelegate;
import com.study.newbies.main.main.sort.list.VerticalListDelegate;

/**
 *
 * @author NewBies
 * @date 2018/9/15
 */

public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        loadRootFragment(R.id.verticalListContainer, listDelegate);
        //设置右侧第一个分类显示，默认显示分类一
        loadRootFragment(R.id.sortContentContainer, ContentDelegate.newInstance(1));
    }
}
