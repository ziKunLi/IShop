package com.study.newbies.common.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 *
 * @author NewBies
 * @date 2018/9/17
 */

public class HolderCreator implements CBViewHolderCreator<ImageHolder>{
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
