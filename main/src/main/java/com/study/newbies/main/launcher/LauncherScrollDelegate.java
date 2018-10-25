package com.study.newbies.main.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.ui.launcher.LauncherHolderCreator;
import com.study.newbies.common.util.storage.AppPreference;
import com.study.newbies.main.R;
import com.study.newbies.main.sign.SignInDelegate;

import java.util.ArrayList;

/**
 * @author NewBies
 */
public class LauncherScrollDelegate extends AppDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> convenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        convenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        convenientBanner = new ConvenientBanner<>(getContext());
        return convenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            AppPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //让用户登录
            getSupportDelegate().startWithPop(new SignInDelegate());
        }
    }
}
