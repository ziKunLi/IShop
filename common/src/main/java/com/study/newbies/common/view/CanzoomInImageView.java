package com.study.newbies.common.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.study.newbies.common.util.LogUtil;

/**
 *
 * @author NewBies
 * @date 2018/2/16
 */
public class CanzoomInImageView extends android.support.v7.widget.AppCompatImageView{

    /**
     * 记录是拖拉照片模式还是放大缩小照片模式
     */
    private int mode = 0;
    /**
     * 拖拉照片模式
     */
    private static final int MODE_DRAG = 1;
    /**
     * 放大缩小照片模式
     */
    private static final int MODE_ZOOM = 2;

    /**
     * 用于记录开始时候的坐标位置
     */
    private PointF startPoint = new PointF();
    /**
     * 用于记录拖拉图片移动的坐标位置
     */
    private Matrix matrix = new Matrix();
    /**
     * 用于记录图片要进行拖拉时候的坐标位置
     */
    private Matrix currentMatrix = new Matrix();
    /**
     * 两个手指的开始距离
     */
    private float startDis;
    /**
     *  两个手指的中间点
     */
    private PointF midPoint;
    /**
     * 缩放倍数，这个必须默认为1呀，你默认为其他的会出错哦
     */
    private float scale = 1;
    /**
     * x轴方向偏移量
     */
    private float dx;
    /**
     * y轴方向偏移量
     */
    private float dy;
    /**
     * 相比于最原始图片的放大缩小比
     */
    private float originalScale;
    /**
     * 画笔
     */
    private Paint paint;

    private OnPictureChangeListener onPictureChangeListener;

    /**
     * 图片变化时（拖动，放大缩小）的监听器
     */
    public interface OnPictureChangeListener{
        void onPictureDrag();
        void onPictureZoom();
    }

    public CanzoomInImageView(Context context) {
        super(context);
    }

    public CanzoomInImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanzoomInImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            // 手指压下屏幕
            case MotionEvent.ACTION_DOWN:
                mode = MODE_DRAG;
                // 记录ImageView当前的移动位置
                currentMatrix.set(getImageMatrix());
                startPoint.set(event.getX(), event.getY());
                LogUtil.v("手指按下");
                break;
            // 手指在屏幕上移动，该事件会被不断触发
            case MotionEvent.ACTION_MOVE:
                // 拖拉图片
                if (mode == MODE_DRAG) {
                    // 得到x轴的移动距离
                    dx = event.getX() - startPoint.x;
                    // 得到x轴的移动距离
                    dy = event.getY() - startPoint.y;
                    // 在没有移动之前的位置上进行移动
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                    onPictureChangeListener.onPictureDrag();
                }
                //放大缩小图片
                else if (mode == MODE_ZOOM) {
                    //结束距离
                    float endDis = distance(event);
                    //两个手指并拢在一起的时候像素大于10
                    if (endDis > 10f) {
                        //得到缩放倍数
                        scale = endDis / startDis;
                        matrix.set(currentMatrix);
                        matrix.postScale(scale, scale,midPoint.x,midPoint.y);
                        onPictureChangeListener.onPictureZoom();
                    }
                }
                break;
            // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                //计算两个手指间的距离
                startDis = distance(event);
                //计算两个手指间的中间点
                // 两个手指并拢在一起的时候像素大于10
                if (startDis > 10f) {
                    midPoint = mid(event);
                    //记录当前ImageView的缩放倍数
                    currentMatrix.set(getImageMatrix());
                }
                break;
            // 手指离开屏幕
            case MotionEvent.ACTION_UP:
                // 当触点离开屏幕，但是屏幕上还有触点(手指)
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                this.originalScale = originalScale * scale;
                LogUtil.v("originalScale: " + originalScale + "  scale: " + scale);
                //防止拖动后累乘导致值不正确
                scale = 1;
                break;
            default:
                break;
        }

        float[] temp = new float[9];
        matrix.getValues(temp);
        LogUtil.v("嗯" + temp[0] + "  " + temp[4]);

        setImageMatrix(matrix);

        return true;
    }

    /**
     * 计算两个手指间的距离
     */
    private float distance(MotionEvent event) {
        //第二个点的坐标与第一个点的坐标差
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        //使用勾股定理返回两点之间的距离
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算两个手指间的中间点
     */
    private PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

    public float getOriginalScale() {
        return this.originalScale;
    }

    public void setOriginalScale(float originalScale) {
        //设置此时的放大比为原始放大比，如果不设置则默认为1，但显示出来的图片不一定为1，
        // 这就会导致第一次调用了这里重写的onTouchEvent方法时，将图片变为了1的大小
        matrix.setScale(originalScale, originalScale);
        setImageMatrix(matrix);
        this.originalScale = originalScale;
    }

//    public void setOriginalScale(float originalScale,int offsetX,int offsetY){
//        //设置此时的放大比为原始放大比，如果不设置则默认为1，但显示出来的图片不一定为1，
//        // 这就会导致第一次调用了这里重写的onTouchEvent方法时，将图片变为了1的大小
//        matrix.setScale(originalScale, originalScale);
//        setImageMatrix(matrix);
//        this.originalScale = originalScale;
//    }

    public float getScale() {
        return scale;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setOnPictureChangeListener(OnPictureChangeListener onPictureChangeListener) {
        this.onPictureChangeListener = onPictureChangeListener;
    }
}
