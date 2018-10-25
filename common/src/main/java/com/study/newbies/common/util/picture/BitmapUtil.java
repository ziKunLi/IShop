package com.study.newbies.common.util.picture;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author NewBies
 */
public class BitmapUtil {

    private static BitmapFactory.Options options = new BitmapFactory.Options();

    private static int inSampleSize;

    public static void saveBitmap(Bitmap bitmap, String path){
        File filePhoto = new File(path);
        if (!filePhoto.exists()) {
            try {
                filePhoto.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            filePhoto.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePhoto);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 取样优化法
     * 方法描述：压缩Resources类型的Bitmap
     * @param resources Class for accessing an application's resources.
     * @param resId     图片资源的ID
     * @param reqWidth  Bitmap目标压缩宽度像素值
     * @param reqHeight Bitmap目标压缩高度像素值
     * @return 压缩后的Bitmap类型的图片
     */
    public static Bitmap decodeBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        //1.将BitmapFactory.Options的inJustDecodeBounds参数设置为true并加载图片。设置为true表示只去读取图片的信息部分，而不会加载图片
        options.inJustDecodeBounds = true;
        //这一句话用来确定现在是读取的那张图片
        BitmapFactory.decodeResource(resources, resId, options);
        //计算采样率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //4.将BitmapFactory.OPtions的参数设置为false并重新加载图片。
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 取样优化法
     * 从文件中获取图片
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeBitmapFromFile(String filePath, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //1.将BitmapFactory.Options的inJustDecodeBounds参数设置为true并加载图片。设置为true表示只去读取图片的信息部分，而不会加载图片
        options.inJustDecodeBounds = true;
        //这一句话用来确定现在是读取的那张图片
        BitmapFactory.decodeFile(filePath, options);
        //计算采样率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //4.将BitmapFactory.OPtions的参数设置为false并重新加载图片。
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 方法描述：计算采样率inSampleSize的值
     * @param options   BitmapFactory.Options实例
     * @param reqWidth  Bitmap目标压缩宽度像素值
     * @param reqHeight Bitmap目标压缩高度像素值
     * @return 采样率inSampleSize的值
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        //2.从BitmapFactory.Options中取出原始图片的宽高信息。
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;

        //3.根据采样率的规则并结合目标View所需大小计算出采样率inSampleSize。
        int inSampleSize = 1;
        //注意注意，整数除的话会不准确，应使用小数来
        int widthInSampleSize = Math.round((float) outWidth / (float) reqWidth);
        int heightInSampleSize = Math.round((float) outHeight / (float) reqHeight);

        if (widthInSampleSize > inSampleSize || heightInSampleSize > inSampleSize) {
            inSampleSize = widthInSampleSize > heightInSampleSize ? widthInSampleSize : heightInSampleSize;
        }

        BitmapUtil.inSampleSize = inSampleSize;
        System.err.println("BitmapUtil.inSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    public static void deleteImg(String path){
        File fileDir = new File(path);
        fileDir.delete();
    }

    public static int getInSampleSize() {
        return inSampleSize;
    }

    /**
     * 获取Bitmap在ImageView中的偏移量数组,其中第0个值表示在水平方向上的偏移值,第1个值表示在垂直方向上的偏移值
     *
     * @param imageView
     * @param includeLayout 在计算偏移的时候是否要考虑到布局的因素,如果要考虑该因素则为true,否则为false
     * @return the offsets of the bitmap inside the imageview, offset[0] means horizontal offset, offset[1] means vertical offset
     */
    public static int[] getBitmapOffset(ImageView imageView, @SuppressWarnings("SameParameterValue") boolean includeLayout) {
        int[] offset = new int[2];
        float[] values = new float[9];

        Matrix matrix = imageView.getImageMatrix();
        matrix.getValues(values);

        // x方向上的偏移量(单位px)
        offset[0] = (int) values[2];
        // y方向上的偏移量(单位px)
        offset[1] = (int) values[5];

        if (includeLayout) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            int paddingTop = imageView.getPaddingTop();
            int paddingLeft = imageView.getPaddingLeft();

            offset[0] += paddingLeft + params.leftMargin;
            offset[1] += paddingTop + params.topMargin;
        }
        return offset;
    }
}
