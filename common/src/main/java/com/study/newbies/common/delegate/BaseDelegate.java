package com.study.newbies.common.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newbies.common.activity.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 *
 * @author NewBies
 * @date 2018/9/9
 */

public abstract class BaseDelegate extends SwipeBackFragment{

    private Unbinder unbinder = null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if(setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer) setLayout(), container,false);
        }
        else if(setLayout() instanceof View){
            rootView = (View) setLayout();
        }
        else{
            throw new ClassCastException("setLayout方法返回类型错误，应为Integer或View，setLayout的返回值为：" + setLayout());
        }
        //如果rootView不为null，那么就绑定他
        unbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }

    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
