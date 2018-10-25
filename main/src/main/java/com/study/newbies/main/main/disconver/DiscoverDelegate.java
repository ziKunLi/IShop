package com.study.newbies.main.main.disconver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.ui.bottom.BottomItemDelegate;
import com.study.newbies.common.ui.web.WebDelegate;
import com.study.newbies.common.ui.web.WebDelegateImpl;
import com.study.newbies.main.R;

/**
 *
 * @author NewBies
 * @date 2018/9/19
 */

public class DiscoverDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("index.html");
        delegate.setTopDelegate(this.getParentDelegate());
        loadRootFragment(R.id.webDiscoveryContainer, delegate);
    }
}
