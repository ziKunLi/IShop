package com.study.newbies.common.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 *
 * @author NewBies
 * @date 2018/9/13
 */

public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new AppCompatImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }
}
