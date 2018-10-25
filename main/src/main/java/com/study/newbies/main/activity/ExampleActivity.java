package com.study.newbies.main.activity;


import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.activity.ProxyActivity;
import com.study.newbies.common.ui.launcher.ScrollLauncherTag;
import com.study.newbies.common.util.storage.AppPreference;
import com.study.newbies.main.launcher.ILauncherListener;
import com.study.newbies.main.launcher.LauncherDelegate;
import com.study.newbies.main.launcher.LauncherScrollDelegate;
import com.study.newbies.main.launcher.OnLauncherFinishTag;
import com.study.newbies.main.main.BottomDelegate;
import com.study.newbies.main.sign.ISignListener;
import com.study.newbies.main.sign.SignInDelegate;

/**
 *
 * @author NewBies
 * @date 2018/9/9
 */

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    public AppDelegate setRootDelegate() {
        //如果用户是第一次使用
        if(!AppPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            return new LauncherScrollDelegate();
        }
        else {
            //在里面检查用户是否登录
            return new LauncherDelegate();
        }
    }

    @Override
    public void onSignInSuccess() {
        getSupportDelegate().startWithPop(new BottomDelegate());
        showToast("登录成功");
    }

    @Override
    public void onSignUpSuccess() {
        getSupportDelegate().startWithPop(new BottomDelegate());
        showToast("注册成功");
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                getSupportDelegate().startWithPop(new BottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
