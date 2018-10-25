package com.study.newbies.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 *
 * @author NewBies
 * @date 2018/8/26
 */
public class DragButton extends View {

    private View dragView;

    public DragButton(Context context) {
        super(context);
    }

    public DragButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
//                dragView.layout();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    public View setDragView(final FrameLayout parent, View view){

        view.setOnTouchListener(new OnTouchListener() {

            private int startX;
            private int startY;
            private int endX;
            private int endY;
            private FrameLayout.LayoutParams layoutParams;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                endX = (int)event.getX();
                endY = (int)event.getY();
                if(layoutParams == null){
                    layoutParams = (FrameLayout.LayoutParams) parent.getLayoutParams();
                }
                switch (event.getAction()){
                    //监听按下去的事件，这个事件在每次拖动时，必定会执行，也只执行一次
                    case MotionEvent.ACTION_DOWN:
                        //将按下去的点记录为起始点
                        startX = endX;
                        startY = endY;
                        break;
                    //步骤五：监听移动事件，该事件会在拖动时执行N次
                    case MotionEvent.ACTION_MOVE:
                        //计算移动的距离
                        int offsetX = endX - startX;
                        int offsetY = endY - startY;

                        //调用layout方法来重新放置它的位置
                        layoutParams.setMargins(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                        //刷新
                        requestLayout();
                        break;
                    //监听抬起事件，该事件同按下去的时间一样，只执行一次
                    case MotionEvent.ACTION_UP:
                        break;
                    default:break;
                }
                return true;
            }
        });

        return view;
    }
}
