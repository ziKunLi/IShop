package com.study.newbies.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.study.newbies.common.R;
import com.study.newbies.common.util.LogUtil;

/**
 * 卫星菜单
 * @author NewBies
 * @date 2018/4/14
 */

public class SatelliteMenu extends ViewGroup{

    /**
     * 子视图数量
     */
    private int childCount;
    /**
     * 布局所在位置
     */
    private LOCATION location = LOCATION.LEFT_BOTTOM;
    /**
     * 半径
     */
    private int radius;
    private int offset = 0;
    /**
     * 中心点
     */
    private View centerView;
    /**
     * 菜单是否关闭
     */
    private boolean isClose = false;
    /**
     * 组件点击事件
     */
    private OnItemClickListener onItemClickListener;
    private int duration = 300;
    private boolean isAddView;
    private int width;
    private int height;
    /**
     * 布局所在位置的枚举
     */
    private enum LOCATION{
        LEFT_BOTTOM, LEFT_TOP, RIGHT_BOTTOM, RIGHT_TOP
    }

    public SatelliteMenu(Context context) {
        super(context);
    }

    public SatelliteMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SatelliteMenu);
        int position = typedArray.getInt(R.styleable.SatelliteMenu_location,3);
        radius = (int) typedArray.getDimension(R.styleable.SatelliteMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()));
        typedArray.recycle();

        switch (position){
            case 0:
                location = LOCATION.LEFT_BOTTOM;
                break;
            case 1:
                location = LOCATION.LEFT_TOP;
                break;
            case 2:
                location = LOCATION.RIGHT_BOTTOM;
                break;
            case 3:
                location = LOCATION.RIGHT_TOP;
                break;
            default:break;
        }
    }

    public SatelliteMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        childCount = getChildCount();
        for(int i = 0; i < childCount; i++){
            //测量子视图大小
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            initLayout();
        }
        else if(isAddView){
            initLayout();
            //如果是打开的，那么将其关闭
            toggleMenuAnim();
            isAddView = false;
        }
    }

    @Override
    public void  addView(View view){
        super.addView(view);
        isAddView = true;
    }


    public void initLayout(){
        layoutCenterButton();
        if(childCount > 1){
            //使用偏移量，保证卫星按钮隐藏时是在主星按钮中心的
            offset = (centerView.getMeasuredWidth() - getChildAt(1).getMeasuredWidth())/2;
        }
        if (offset < 0){
            offset = 0;
        }
        for(int i = 1; i < childCount;i++){
            getChildAt(i).setVisibility(GONE);
            //布局
            initLayout(getChildAt(i),offset);
        }

    }
    /**
     * 初始化中心按钮
     */
    private void layoutCenterButton(){
        //将第一个按钮设置为中心按钮
        centerView = getChildAt(0);
        centerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenuAnim();
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(0);
                }
            }
        });
       initLayout(centerView,0);
    }

    /**
     * 初始化组件所在位置
     * @param offset
     */
    private void initLayout(View view,int offset){
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        int left = 0;
        int top = 0;
        switch (location){
            case LEFT_BOTTOM:
                top = getMeasuredHeight() - height;
                break;
            case LEFT_TOP:
                break;
            case RIGHT_BOTTOM:
                left = getMeasuredWidth() - width;
                top = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                left = getMeasuredWidth() - width;
                break;
            default:
                break;
        }
        //参数代表了相对于布局的左上角位置和右下角位置
        view.layout(left + offset,top - offset,left + width + offset,top + height - offset);
        LogUtil.v("itemSize: ",(left + offset) + "  " + (top - offset) + "  " + (left + width + offset) + "   " + (top + height - offset));
    }

    /**
     * 切换菜单动画
     */
    private void toggleMenuAnim(){
        int flagX = 1;
        int flagY = 1;
        int flagToggle = 1;
        //更改状态
        if(isClose){
            isClose = false;
            flagToggle = -1;
        }
        else {
            isClose = true;
        }
        //判断布局所在位置，设置弹出方向为正还是为负
        if(location == LOCATION.RIGHT_BOTTOM||location == LOCATION.RIGHT_TOP){
            flagX = -1;
        }
        if(location == LOCATION.RIGHT_BOTTOM||location == LOCATION.LEFT_BOTTOM){
            flagY = -1;
        }


        //组件对应角度
        double arc = Math.PI/2/(childCount - 2);
        if(childCount <= 2){
            arc = Math.PI/2;
        }
        for (int i = 1; i < childCount; i++){
            //如果是关闭的，那么就打开
            if(isClose){
                getChildAt(i).setVisibility(VISIBLE);
                getChildAt(i).setScaleX(1);
                getChildAt(i).setScaleY(1);
                getChildAt(i).setClickable(true);
                getChildAt(i).setFocusable(true);
            }
            getChildAt(i).setTranslationX(offset);
            getChildAt(i).setTranslationY(offset);
            ViewCompat.animate(getChildAt(i))
                    .translationX((float) (flagToggle * flagX * radius * Math.cos(arc * (i - 1))))
                    .translationY((float) (flagToggle * flagY * radius * Math.sin(arc * (i - 1))))
                    .setDuration(duration)
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            view.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            if(!isClose){
                                view.setVisibility(GONE);
                                view.setClickable(false);
                                view.setFocusable(false);
                            }
                            view.setEnabled(true);
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    })
                    .start();

            final int position = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(position);
                    }
                    isClose = false;
                    onItemClickAnim(position);
                }
            });
        }
        if(isClose){
            ViewCompat.animate(centerView)
                    .rotation(360)
                    .setDuration(duration)
                    .start();
        }
        else {
            ViewCompat.animate(centerView)
                    .rotation(0)
                    .setDuration(duration)
                    .start();
        }
    }

    private void onItemClickAnim(int position){
        ViewCompat.animate(getChildAt(position))
                .scaleX(4)
                .scaleY(4)
                .setDuration(duration + 100)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                })
                .start();
        for(int i = 1; i < childCount; i++){
            if(i != position){
                ViewCompat.animate(getChildAt(i))
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(duration + 100)
                        .setListener(new ViewPropertyAnimatorListener() {
                            @Override
                            public void onAnimationStart(View view) {
                                centerView.setEnabled(false);
                            }

                            @Override
                            public void onAnimationEnd(View view) {
                                view.setVisibility(GONE);
                                view.setTranslationX(offset);
                                view.setTranslationY(offset);
                                centerView.setEnabled(true);
                            }

                            @Override
                            public void onAnimationCancel(View view) {

                            }
                        })
                        .start();
            }

        }
        ViewCompat.animate(centerView)
                .rotation(0)
                .setDuration(duration)
                .start();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
