package com.study.newbies.common.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 *
 * @author NewBies
 * @date 2018/9/17
 */

public class ImageHolder implements Holder<String>{

    private AppCompatImageView imageView = null;

    private static final RequestOptions BANNER_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    @Override
    public View createView(Context context) {
        imageView = new AppCompatImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        //从网络加载图片
        Glide.with(context)
                //url
                .load(data)
                .apply(BANNER_OPTIONS)
                .into(imageView);
    }
}
