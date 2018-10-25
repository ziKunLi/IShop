package com.study.newbies.main.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.study.newbies.common.ui.RgbValue;
import com.study.newbies.main.R;

/**
 *
 * @author NewBies
 * @date 2018/9/18
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    /**
     * 顶部距离
     */
    private int distanceY = 0;
    /**
     * 颜色变化速度
     */
    private static final int SHOW_SPEED = 3;
    /**
     * 定义变化的颜色
     */
    private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);

    public TranslucentBehavior(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    /**
     * toolbat变化所依赖的view
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rvIndex;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //我们接管他的事件
        return true;
    }

    /**
     * 这个方法处理具体的使用逻辑的（核心）
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        //增加滑动距离
        distanceY += dyConsumed;
//        LogUtil.v("Y", distanceY + " | " + dyConsumed);
        //toolbar的高度
        final int targetHeight = child.getBottom();
        //当滑动时，并且距离小于toolbar高度的时候，调整渐变色
        if(distanceY >= 0&&distanceY <= targetHeight){
            final float scale = (float) distanceY/targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }
        else if (distanceY > targetHeight){
            child.setBackgroundColor(Color.rgb( RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }
    }
}
