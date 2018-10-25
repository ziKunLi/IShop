package com.study.newbies.main.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.main.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 商品详情页
 * @author NewBies
 * @date 2018/9/18
 */

public class GoodsDetailDelegate extends AppDelegate{

    public static GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
