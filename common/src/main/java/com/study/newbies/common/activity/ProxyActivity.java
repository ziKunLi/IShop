package com.study.newbies.common.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.widget.Toast;

import com.study.newbies.common.R;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import qiu.niorgai.StatusBarCompat;

/**
 *
 * @author NewBies
 * @date 2018/9/9
 */

public abstract class ProxyActivity extends SupportActivity implements ISupportActivity{

    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    protected void showToast(String info){
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    public abstract AppDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);
        //初始化视图
        initContainer(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        @SuppressLint("RestrictedApi") final ContentFrameLayout contentFrameLayout = new ContentFrameLayout(this);
        contentFrameLayout.setId(R.id.delegateContainer);
        setContentView(contentFrameLayout);
        if(savedInstanceState == null){
            //装载根Fragment
            DELEGATE.loadRootFragment(R.id.delegateContainer, setRootDelegate());
        }
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //垃圾回收，虽然不一定会执行，但还是写一下
        System.gc();
        System.runFinalization();
    }

    @Override
    public void onBackPressedSupport() {
        DELEGATE.onBackPressedSupport();
    }

}
