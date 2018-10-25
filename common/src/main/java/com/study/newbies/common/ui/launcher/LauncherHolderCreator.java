package com.study.newbies.common.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 *
 * @author NewBies
 * @date 2018/9/13
 */

public class LauncherHolderCreator implements CBViewHolderCreator {

    @Override
    public Object createHolder() {
        return new LauncherHolder();
    }
}
