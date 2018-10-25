package com.study.newbies.common.ui.web.event;

import com.study.newbies.common.util.LogUtil;

/**
 *
 * @author NewBies
 * @date 2018/10/5
 */

public class UndefineEvent extends Event{
    @Override
    public String execute(String params) {
        LogUtil.v("UndefineEvent", params);
        return null;
    }
}
