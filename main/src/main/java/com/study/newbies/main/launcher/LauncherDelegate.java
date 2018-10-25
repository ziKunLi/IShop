package com.study.newbies.main.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.app.AccountManager;
import com.study.newbies.common.app.IUserChecker;
import com.study.newbies.common.util.timer.BaseTimerTask;
import com.study.newbies.common.util.timer.ITimerListener;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author NewBies
 */
public class LauncherDelegate extends AppDelegate implements ITimerListener {

    @BindView(R2.id.launcherTimer)
    AppCompatTextView tvTimer = null;

    private Timer timer = null;
    private int count = 5;
    private ILauncherListener launcherListener = null;

    @OnClick(R2.id.launcherTimer)
    void onClickTimerView() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        checkIsSignIn();
    }

    /**
     * 当碎片与活动绑定时，获取到回调的launcherListener
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            launcherListener = (ILauncherListener) activity;
        }
    }

    private void initTimer() {
        timer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        timer.schedule(task, 0, 1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initTimer();
    }

    /**
     * 检查用户是否登录了APP
     */
    private void checkIsSignIn() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                if (launcherListener != null) {
                    launcherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSignIn() {
                if (launcherListener != null) {
                    launcherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvTimer != null) {
                    tvTimer.setText(MessageFormat.format("跳过\n{0}s", count));
                    count--;
                    if (count < 0) {
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                            checkIsSignIn();
                        }
                    }
                }
            }
        });
    }
}
