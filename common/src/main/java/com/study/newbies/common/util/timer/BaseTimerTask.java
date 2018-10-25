package com.study.newbies.common.util.timer;

import java.util.TimerTask;

/**
 *
 * @author NewBies
 * @date 2018/9/13
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener iTimerListener;

    public BaseTimerTask(ITimerListener iTimerListener) {
        this.iTimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if(iTimerListener != null){
            iTimerListener.onTimer();
        }
    }
}
