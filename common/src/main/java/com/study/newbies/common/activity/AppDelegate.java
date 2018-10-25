package com.study.newbies.common.activity;

import android.widget.Toast;

import com.study.newbies.common.delegate.BaseDelegate;

/**
 *
 * @author NewBies
 * @date 2018/9/9
 */

public abstract class AppDelegate extends BaseDelegate{

    protected void showToast(String info){
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    public <T extends AppDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
